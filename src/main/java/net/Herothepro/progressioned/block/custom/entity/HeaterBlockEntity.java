package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.block.custom.HeaterBlock;
import net.Herothepro.progressioned.util.gui.HeaterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class HeaterBlockEntity extends BlockEntity implements MenuProvider {

    public int burnTime = 0;
    public int maxBurnTime = 0;

    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> HeaterBlockEntity.this.burnTime;
                case 1 -> HeaterBlockEntity.this.maxBurnTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> HeaterBlockEntity.this.burnTime = value;
                case 1 -> HeaterBlockEntity.this.maxBurnTime = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return isAllowedFuel(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    private boolean isAllowedFuel(ItemStack stack) {
        return stack.is(ItemTags.COALS)
                || stack.is(Items.BLAZE_ROD)
                || stack.is(Items.LAVA_BUCKET);
    }

    public HeaterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEATER_BE.get(), pos, state);
    }

    public boolean isLit() {
        return this.burnTime > 0;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        boolean wasLit = isLit();

        if (isLit()) {
            this.burnTime--;
            setChanged();
        }

        if (!isLit() && melterNeedsHeat(level, pos)) {
            ItemStack fuelStack = this.itemHandler.getStackInSlot(0);
            if (!fuelStack.isEmpty() && isAllowedFuel(fuelStack)) {
                int fuelValue = fuelStack.getBurnTime(RecipeType.SMELTING);
                if (fuelValue > 0) {
                    this.burnTime = fuelValue;
                    this.maxBurnTime = fuelValue;

                    if (fuelStack.hasCraftingRemainingItem()) {
                        this.itemHandler.setStackInSlot(0, fuelStack.getCraftingRemainingItem());
                    } else {
                        fuelStack.shrink(1);
                    }
                    setChanged();
                }
            }
        }
        if (wasLit != isLit()) {
            level.setBlock(pos, state.setValue(HeaterBlock.LIT, isLit()), 3);
            setChanged();
        }
    }

    private boolean melterNeedsHeat(Level level, BlockPos pos) {
        return level.getBlockEntity(pos.above()) instanceof MelterBlockEntity melter && melter.needsHeat(level);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.progressioned.porcelain_heater");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new HeaterMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("burnTime", burnTime);
        tag.putInt("maxBurnTime", maxBurnTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        burnTime = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("maxBurnTime");
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
}