package net.Herothepro.progressioned.util.gui;

import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.entity.MelterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MelterMenu extends AbstractContainerMenu {
    private final MelterBlockEntity blockEntity;
    private final ContainerData data;
    private final ContainerLevelAccess levelAccess;

    public MelterMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, (MelterBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(16));
    }

    public MelterMenu(int id, Inventory playerInventory, MelterBlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MELTER_MENU.get(), id);
        checkContainerDataCount(data, 16);
        this.blockEntity = entity;
        this.data = data;
        this.levelAccess = ContainerLevelAccess.create(playerInventory.player.level(), entity.getBlockPos());

        this.addSlot(new SlotItemHandler(entity.inventory, 0, 26, 17));
        this.addSlot(new SlotItemHandler(entity.inventory, 1, 26, 35));
        this.addSlot(new SlotItemHandler(entity.inventory, 2, 26, 53));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        this.addDataSlots(data);
    }

    public MelterBlockEntity getBlockEntity() { return blockEntity; }
    public boolean isBurning() { return this.data.get(0) > 0; }
    public int getFuel() { return this.data.get(0); }
    public int getMaxFuel() { return this.data.get(1); }
    public int getScaledFuel() { return this.data.get(1) == 0 ? 0 : this.data.get(0) * 14 / this.data.get(1); }
    public int getScaledProgress(int i) { return this.data.get(6 + i) == 0 ? 0 : this.data.get(3 + i) * 16 / this.data.get(6 + i); }
    public int getState(int i) { return this.data.get(9 + i); }

    public ItemStack getHeaterFuelStack() {
        int count = this.data.get(14);
        int itemId = this.data.get(15);
        if (count > 0 && itemId >= 0) {
            Item item = BuiltInRegistries.ITEM.byId(itemId);
            if (item != null && item != Items.AIR) {
                return new ItemStack(item, count);
            }
        }
        return ItemStack.EMPTY;
    }

    public FluidStack getFluid() {
        FluidStack stack = blockEntity.tank.getFluid();
        if (!stack.isEmpty()) return stack;

        int amount = this.data.get(12);
        int fluidId = this.data.get(13);
        if (amount > 0 && fluidId >= 0) {
            Fluid fluid = BuiltInRegistries.FLUID.byId(fluidId);
            if (fluid != null && fluid != Fluids.EMPTY) {
                return new FluidStack(fluid, amount);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player p) {
        return !blockEntity.isRemoved() && stillValid(levelAccess, p, ModBlocks.PORCELAIN_MELTER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!this.moveItemStackTo(itemstack1, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    private void addPlayerInventory(Inventory inv) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }
}