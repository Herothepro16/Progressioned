package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.recipe.CastingBasinRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BasinBlockEntity extends BlockEntity {

    public int coolingTime = 0;
    public int maxCoolingTime = 100;
    public int renderTimer = 0;
    public long coolingStartGameTime = -1L;
    public ItemStack cachedRecipeOutput = ItemStack.EMPTY;

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    };

    public final FluidTank tank = new FluidTank(10000) {
        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            if (!inventory.getStackInSlot(0).isEmpty()) return 0;
            if (!fluid.isEmpty() && !FluidStack.isSameFluidSameComponents(fluid, resource)) return 0;
            CastingBasinRecipe matchedRecipe = findMatchingRecipe(resource);
            if (matchedRecipe == null) return 0;

            int requiredAmount = matchedRecipe.input().getAmount();
            int spaceLeft = requiredAmount - fluid.getAmount();
            if (spaceLeft <= 0) return 0;

            FluidStack limitedFill = resource.copy();
            limitedFill.setAmount(Math.min(resource.getAmount(), spaceLeft));

            return super.fill(limitedFill, action);
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    public BasinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BASIN_BE.get(), pos, state);
    }

    public CastingBasinRecipe findMatchingRecipe(FluidStack fluidStack) {
        if (level == null || fluidStack.isEmpty()) return null;
        try {
            var recipeManager = level.getRecipeManager();
            if (recipeManager == null) return null;
            for (var recipeHolder : recipeManager.getAllRecipesFor(ModRecipes.CASTING_BASIN_TYPE.get())) {
                CastingBasinRecipe recipe = recipeHolder.value();
                if (recipe != null && !recipe.output().isEmpty() && recipe.input().getFluid() == fluidStack.getFluid()) {
                    return recipe;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    public void dropContents() {
        if (level == null || level.isClientSide) return;
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(slot));
        }
    }

    public void extractItem(Player player) {
        ItemStack output = inventory.getStackInSlot(0);
        if (!output.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, output, player.getInventory().selected);
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            tank.setFluid(FluidStack.EMPTY);
            cachedRecipeOutput = ItemStack.EMPTY;
            coolingTime = 0;
            coolingStartGameTime = -1L;
            renderTimer = 0;
            setChanged();
            if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (renderTimer > 0) {
            renderTimer--;
        }

        if (!inventory.getStackInSlot(0).isEmpty()) {
            resetCooling();
            return;
        }

        FluidStack currentFluid = tank.getFluid();
        if (currentFluid.isEmpty()) {
            resetCooling();
            cachedRecipeOutput = ItemStack.EMPTY;
            return;
        }

        CastingBasinRecipe matchedRecipe = findMatchingRecipe(currentFluid);
        if (matchedRecipe != null && currentFluid.getAmount() >= matchedRecipe.input().getAmount()) {
            maxCoolingTime = matchedRecipe.coolingTime();
            cachedRecipeOutput = matchedRecipe.output();
            coolingTime++;

            if (coolingTime >= maxCoolingTime) {
                coolingTime = 0;
                coolingStartGameTime = -1L;
                tank.drain(matchedRecipe.input().getAmount(), IFluidHandler.FluidAction.EXECUTE);
                inventory.setStackInSlot(0, matchedRecipe.output().copy());
                renderTimer = 20;

                level.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.LAVA_EXTINGUISH, net.minecraft.sounds.SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            } else {
                setChanged();
                long expectedStart = level.getGameTime() - coolingTime;
                if (coolingStartGameTime != expectedStart) {
                    coolingStartGameTime = expectedStart;
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        } else {
            resetCooling();
        }
    }

    public boolean isCooling() {
        return coolingStartGameTime >= 0 && maxCoolingTime > 0;
    }

    public float getCoolingProgress(float partialTick) {
        if (!isCooling() || level == null) return 0.0f;
        float elapsed = (level.getGameTime() + partialTick) - coolingStartGameTime;
        return Mth.clamp(elapsed / (float) maxCoolingTime, 0.0f, 1.0f);
    }

    private void resetCooling() {
        if (coolingTime != 0 || coolingStartGameTime != -1L) {
            coolingTime = 0;
            coolingStartGameTime = -1L;
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("coolingTime", coolingTime);
        tag.putLong("coolingStart", coolingStartGameTime);
        tag.putInt("maxCoolingTime", maxCoolingTime);
        tag.putInt("renderTimer", renderTimer);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.put("tank", tank.writeToNBT(registries, new CompoundTag()));
        if (!cachedRecipeOutput.isEmpty()) {
            tag.put("cachedOutput", cachedRecipeOutput.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        coolingTime = tag.getInt("coolingTime");
        coolingStartGameTime = tag.contains("coolingStart") ? tag.getLong("coolingStart") : -1L;
        maxCoolingTime = tag.getInt("maxCoolingTime");
        if (maxCoolingTime <= 0) maxCoolingTime = 100;
        renderTimer = tag.getInt("renderTimer");
        if (tag.contains("inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        } else {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        tank.readFromNBT(registries, tag.getCompound("tank"));
        if (tag.contains("cachedOutput")) {
            cachedRecipeOutput = ItemStack.parse(registries, tag.getCompound("cachedOutput")).orElse(ItemStack.EMPTY);
        }
    }

    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) { CompoundTag tag = new CompoundTag(); saveAdditional(tag, registries); return tag; }
    @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
}