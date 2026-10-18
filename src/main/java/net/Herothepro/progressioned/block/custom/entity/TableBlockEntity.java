package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.recipe.CastingRecipe;
import net.Herothepro.progressioned.recipe.CastingRecipeInput;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class TableBlockEntity extends BlockEntity {

    public int coolingTime = 0;
    public int maxCoolingTime = 100;
    public int fillTarget = 144;
    public ItemStack renderResult = ItemStack.EMPTY;
    public int renderTimer = 0;
    public long coolingStartGameTime = -1L;

    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == 1) return false;
            return super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == 0) {
                return ItemStack.EMPTY;
            }
            return super.extractItem(slot, amount, simulate);
        }
    };

    public final FluidTank tank = new FluidTank(1000) {
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (level == null) return 0;
            if (!inventory.getStackInSlot(1).isEmpty() || !renderResult.isEmpty() || coolingTime > 0) return 0;
            if (!fluid.isEmpty() && !FluidStack.isSameFluidSameComponents(fluid, resource)) return 0;

            ItemStack mold = inventory.getStackInSlot(0);
            if (mold.isEmpty()) return 0;

            CastingRecipe recipe = findRecipeByType(mold, resource);
            if (recipe == null) return 0;

            fillTarget = recipe.getFluidAmount();
            maxCoolingTime = recipe.getCoolingTime();

            int spaceLeft = fillTarget - fluid.getAmount();
            if (spaceLeft <= 0) return 0;

            int toFill = Math.min(resource.getAmount(), spaceLeft);
            FluidStack limitedFill = resource.copyWithAmount(toFill);

            int filled = super.fill(limitedFill, action);
            if (filled > 0 && action.execute()) {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
            return filled;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (coolingTime > 0) return FluidStack.EMPTY;
            return super.drain(maxDrain, action);
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    public TableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TABLE_BE.get(), pos, state);
    }

    public void dropContents() {
        if (level == null || level.isClientSide) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(i));
        }
        if (!renderResult.isEmpty()) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), renderResult);
        }
    }

    public void interact(Player player) {
        if (level == null || level.isClientSide) return;

        ItemStack outputSlot = inventory.getStackInSlot(1);
        ItemStack moldSlot = inventory.getStackInSlot(0);
        ItemStack heldItem = player.getMainHandItem();

        if (!outputSlot.isEmpty() || !renderResult.isEmpty()) {
            ItemStack give = !outputSlot.isEmpty() ? outputSlot.copy() : renderResult.copy();
            ItemHandlerHelper.giveItemToPlayer(player, give);
            inventory.setStackInSlot(1, ItemStack.EMPTY);
            renderResult = ItemStack.EMPTY;
            tank.setFluid(FluidStack.EMPTY);
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            return;
        }

        if (coolingTime == 0 && tank.getFluid().isEmpty()) {
            if (moldSlot.isEmpty() && !heldItem.isEmpty()) {
                ItemStack insert = heldItem.copy();
                insert.setCount(1);
                inventory.setStackInSlot(0, insert);
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            } else if (!moldSlot.isEmpty() && heldItem.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(player, moldSlot.copy());
                inventory.setStackInSlot(0, ItemStack.EMPTY);
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (renderTimer > 0) {
            renderTimer--;
            if (renderTimer == 0 && !renderResult.isEmpty()) {
                inventory.setStackInSlot(1, renderResult.copy());
                renderResult = ItemStack.EMPTY;
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
            return;
        }

        FluidStack fluid = tank.getFluid();
        ItemStack mold = inventory.getStackInSlot(0);

        if (!fluid.isEmpty() && !mold.isEmpty() && inventory.getStackInSlot(1).isEmpty() && renderResult.isEmpty()) {
            CastingRecipe recipe = findRecipeByType(mold, fluid);
            if (recipe != null) {
                fillTarget = recipe.getFluidAmount();
                maxCoolingTime = recipe.getCoolingTime();

                if (fluid.getAmount() >= recipe.getFluidAmount()) {
                    coolingTime++;
                    if (coolingTime >= maxCoolingTime) {
                        coolingTime = 0;
                        coolingStartGameTime = -1L;
                        renderResult = recipe.getResultItem(level.registryAccess()).copy();
                        renderTimer = 15;
                        tank.drain(recipe.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);

                        if (recipe.isConsumeCast()) {
                            inventory.setStackInSlot(0, ItemStack.EMPTY);
                        }

                        level.playSound(null, worldPosition, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F);
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
            } else {
                resetCooling();
            }
        } else if (fluid.isEmpty() && coolingTime > 0) {
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

    public CastingRecipe findRecipeByType(ItemStack mold, FluidStack fluid) {
        if (level == null) return null;
        CastingRecipeInput input = new CastingRecipeInput(mold);
        for (var recipeHolder : level.getRecipeManager().getAllRecipesFor(ModRecipes.CASTING_TYPE.get())) {
            CastingRecipe recipe = recipeHolder.value();
            if (recipe.matches(input, level) && recipe.matchesFluidType(fluid)) {
                return recipe;
            }
        }
        return null;
    }

    public ItemStack getPreviewResult() {
        ItemStack mold = inventory.getStackInSlot(0);
        FluidStack fluid = tank.getFluid();
        if (mold.isEmpty() || fluid.isEmpty() || level == null) return ItemStack.EMPTY;
        CastingRecipe recipe = findRecipeByType(mold, fluid);
        return recipe != null ? recipe.getResultItem(level.registryAccess()) : ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.put("tank", tank.writeToNBT(registries, new CompoundTag()));
        tag.putInt("coolingTime", coolingTime);
        tag.putLong("coolingStart", coolingStartGameTime);
        tag.putInt("maxCoolingTime", maxCoolingTime);
        tag.putInt("fillTarget", fillTarget);
        if (!renderResult.isEmpty()) {
            tag.put("renderResult", renderResult.save(registries, new CompoundTag()));
        }
        tag.putInt("renderTimer", renderTimer);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        if (tag.contains("tank")) tank.readFromNBT(registries, tag.getCompound("tank"));
        coolingTime = tag.getInt("coolingTime");
        coolingStartGameTime = tag.contains("coolingStart") ? tag.getLong("coolingStart") : -1L;
        if (tag.contains("maxCoolingTime")) maxCoolingTime = tag.getInt("maxCoolingTime");
        if (tag.contains("fillTarget")) fillTarget = tag.getInt("fillTarget");
        if (tag.contains("renderResult")) {
            renderResult = ItemStack.parse(registries, tag.getCompound("renderResult")).orElse(ItemStack.EMPTY);
        } else {
            renderResult = ItemStack.EMPTY;
        }
        renderTimer = tag.getInt("renderTimer");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        if (pkt.getTag() != null) {
            loadAdditional(pkt.getTag(), lookupProvider);
        }
    }
}