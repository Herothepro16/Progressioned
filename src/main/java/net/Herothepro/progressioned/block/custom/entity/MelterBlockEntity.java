package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.block.custom.HeaterBlock;
import net.Herothepro.progressioned.block.custom.MelterBlock;
import net.Herothepro.progressioned.recipe.MeltingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.util.gui.MelterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MelterBlockEntity extends BlockEntity implements MenuProvider {

    public int fuel = 0;
    public int maxFuel = 0;
    public int temperature = 0;
    public int[] progress = new int[3];
    public int[] maxProgress = new int[3];
    public int[] state = new int[3];

    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
        @Override
        public int getSlotLimit(int slot) { return 1; }
    };

    public final IItemHandler externalItemHandler = new IItemHandler() {
        @Override public int getSlots() { return 3; }
        @Nonnull @Override public ItemStack getStackInSlot(int slot) { return inventory.getStackInSlot(slot); }
        @Nonnull @Override public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return inventory.insertItem(slot, stack, simulate);
        }
        @Nonnull @Override public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
        @Override public int getSlotLimit(int slot) { return 1; }
        @Override public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return inventory.isItemValid(slot, stack); }
    };

    public final FluidTank tank = new FluidTank(1296) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null) {
                if (!level.isClientSide) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                } else {
                    requestModelDataUpdate();
                }
            }
        }
    };

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> {
                    if (level != null && level.getBlockEntity(worldPosition.below()) instanceof HeaterBlockEntity heater) {
                        yield heater.burnTime;
                    }
                    yield 0;
                }
                case 1 -> {
                    if (level != null && level.getBlockEntity(worldPosition.below()) instanceof HeaterBlockEntity heater) {
                        yield heater.maxBurnTime > 0 ? heater.maxBurnTime : 100;
                    }
                    yield 100;
                }
                case 2 -> MelterBlockEntity.this.temperature;
                case 3 -> MelterBlockEntity.this.progress[0];
                case 4 -> MelterBlockEntity.this.progress[1];
                case 5 -> MelterBlockEntity.this.progress[2];
                case 6 -> MelterBlockEntity.this.maxProgress[0];
                case 7 -> MelterBlockEntity.this.maxProgress[1];
                case 8 -> MelterBlockEntity.this.maxProgress[2];
                case 9 -> MelterBlockEntity.this.state[0];
                case 10 -> MelterBlockEntity.this.state[1];
                case 11 -> MelterBlockEntity.this.state[2];
                case 12 -> MelterBlockEntity.this.tank.getFluidAmount();
                case 13 -> BuiltInRegistries.FLUID.getId(MelterBlockEntity.this.tank.getFluid().getFluid());
                case 14 -> {
                    if (level != null && level.getBlockEntity(worldPosition.below()) instanceof HeaterBlockEntity heater) {
                        yield heater.itemHandler.getStackInSlot(0).getCount();
                    }
                    yield 0;
                }
                case 15 -> {
                    if (level != null && level.getBlockEntity(worldPosition.below()) instanceof HeaterBlockEntity heater) {
                        ItemStack fuelStack = heater.itemHandler.getStackInSlot(0);
                        yield fuelStack.isEmpty() ? -1 : BuiltInRegistries.ITEM.getId(fuelStack.getItem());
                    }
                    yield -1;
                }
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> MelterBlockEntity.this.fuel = value;
                case 1 -> MelterBlockEntity.this.maxFuel = value;
                case 2 -> MelterBlockEntity.this.temperature = value;
                case 3 -> MelterBlockEntity.this.progress[0] = value;
                case 4 -> MelterBlockEntity.this.progress[1] = value;
                case 5 -> MelterBlockEntity.this.progress[2] = value;
                case 6 -> MelterBlockEntity.this.maxProgress[0] = value;
                case 7 -> MelterBlockEntity.this.maxProgress[1] = value;
                case 8 -> MelterBlockEntity.this.maxProgress[2] = value;
                case 9 -> MelterBlockEntity.this.state[0] = value;
                case 10 -> MelterBlockEntity.this.state[1] = value;
                case 11 -> MelterBlockEntity.this.state[2] = value;
            }
        }

        @Override
        public int getCount() { return 16; }
    };

    public MelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MELTER_BE.get(), pos, state);
    }

    public void dropContents() {
        if (level == null || level.isClientSide) return;
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(slot));
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("block.progressioned.porcelain_melter"); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MelterMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.put("tank", tank.writeToNBT(registries, new CompoundTag()));
        tag.putInt("fuel", fuel);
        tag.putInt("maxFuel", maxFuel);
        tag.putInt("temperature", temperature);
        tag.putIntArray("state", state);
        tag.putIntArray("progress", progress);
        tag.putIntArray("maxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        if (tag.contains("tank")) tank.readFromNBT(registries, tag.getCompound("tank"));
        fuel = tag.getInt("fuel");
        maxFuel = tag.getInt("maxFuel");
        temperature = tag.getInt("temperature");

        int[] savedState = tag.getIntArray("state");
        if (savedState.length == 3) state = savedState;

        int[] savedProgress = tag.getIntArray("progress");
        if (savedProgress.length == 3) progress = savedProgress;

        int[] savedMaxProgress = tag.getIntArray("maxProgress");
        if (savedMaxProgress.length == 3) maxProgress = savedMaxProgress;
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
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, lookupProvider);
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        if (this.level != null && this.level.isClientSide) {
            requestModelDataUpdate();
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private int getHeatFromBelow(Level level, BlockPos heatPos) {
        BlockState stateBelow = level.getBlockState(heatPos);
        if (stateBelow.getBlock() instanceof HeaterBlock) {
            BlockEntity be = level.getBlockEntity(heatPos);
            if (be instanceof HeaterBlockEntity heater && heater.isLit()) {
                return 1200;
            }
        }
        return 0;
    }

    public boolean needsHeat(Level level) {
        if (level.isClientSide) return false;

        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            var recipeHolder = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.MELTING_TYPE.get(), new SingleRecipeInput(stack), level)
                    .orElse(null);
            if (recipeHolder == null) continue;

            FluidStack output = recipeHolder.value().getOutput().copy();
            boolean canOutput = tank.fill(output, IFluidHandler.FluidAction.SIMULATE) == output.getAmount();

            if (canOutput || progress[i] < maxProgress[i]) {
                return true;
            }
        }
        return false;
    }

    public void tick(Level level, BlockPos pos, BlockState blockState) {
        if (level.isClientSide) return;
        boolean isDirty = false;
        boolean hasItemToMelt = false;

        int currentAvailableHeat = getHeatFromBelow(level, pos.below());

        for (int i = 0; i < 3; i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if (stack.isEmpty()) {
                if (progress[i] != 0 || maxProgress[i] != 0 || this.state[i] != 0) {
                    progress[i] = 0; maxProgress[i] = 0; this.state[i] = 0;
                    isDirty = true;
                }
                continue;
            }

            var recipeHolder = level.getRecipeManager()
                    .getRecipeFor(ModRecipes.MELTING_TYPE.get(), new SingleRecipeInput(stack), level)
                    .orElse(null);

            if (recipeHolder == null) {
                if (this.state[i] != 3) {
                    progress[i] = 0; maxProgress[i] = 100; this.state[i] = 3;
                    isDirty = true;
                }
                continue;
            }

            MeltingRecipe recipe = recipeHolder.value();
            if (maxProgress[i] != recipe.getCookingTime()) {
                maxProgress[i] = recipe.getCookingTime();
                isDirty = true;
            }

            if (currentAvailableHeat < 1000) {
                if (this.state[i] != 4) {
                    this.state[i] = 4;
                    isDirty = true;
                }
                if (progress[i] > 0) {
                    progress[i]--;
                    isDirty = true;
                }
                continue;
            }

            FluidStack output = recipe.getOutput().copy();
            boolean canOutput = tank.fill(output, IFluidHandler.FluidAction.SIMULATE) == output.getAmount();

            if (canOutput) {
                hasItemToMelt = true;
                if (progress[i] >= maxProgress[i]) {
                    inventory.extractItem(i, 1, false);
                    tank.fill(output, IFluidHandler.FluidAction.EXECUTE);
                    progress[i] = 0;
                    this.state[i] = 0;
                    isDirty = true;
                } else {
                    if (this.state[i] != 1) { this.state[i] = 1; isDirty = true; }
                    progress[i]++;
                    isDirty = true;
                }
            } else {
                if (progress[i] >= maxProgress[i]) {
                    if (this.state[i] != 2) { this.state[i] = 2; isDirty = true; }
                } else {
                    if (this.state[i] != 1) { this.state[i] = 1; isDirty = true; }
                    hasItemToMelt = true;
                    progress[i]++;
                    isDirty = true;
                }
            }
        }

        if (isDirty) setChanged(level, pos, blockState);

        boolean isBurning = hasItemToMelt && currentAvailableHeat >= 1000;
        if (blockState.hasProperty(MelterBlock.LIT) && blockState.getValue(MelterBlock.LIT) != isBurning) {
            level.setBlockAndUpdate(pos, blockState.setValue(MelterBlock.LIT, isBurning));
        }
    }
}