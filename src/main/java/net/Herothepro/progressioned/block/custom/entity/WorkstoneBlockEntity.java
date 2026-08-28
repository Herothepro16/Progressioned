package net.Herothepro.progressioned.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WorkstoneBlockEntity extends BlockEntity {

    private ItemStack inputStack = ItemStack.EMPTY;
    private ItemStack outputStack = ItemStack.EMPTY;
    private int hits = 0;

    public WorkstoneBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WORKSTONE_BE.get(), pos, state);
    }

    public ItemStack getInput() { return inputStack; }
    public ItemStack getOutput() { return outputStack; }
    public int getHits() { return hits; }

    public void setInput(ItemStack stack) {
        this.inputStack = stack;
        sync();
    }

    public void setOutput(ItemStack stack) {
        this.outputStack = stack;
        sync();
    }

    public void setHits(int hits) {
        this.hits = hits;
        sync();
    }

    private void sync() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Input", inputStack.saveOptional(registries));
        tag.put("Output", outputStack.saveOptional(registries));
        tag.putInt("Hits", hits);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inputStack = ItemStack.parseOptional(registries, tag.getCompound("Input"));
        this.outputStack = ItemStack.parseOptional(registries, tag.getCompound("Output"));
        this.hits = tag.getInt("Hits");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public void shrinkInput(int count) {
        if (!this.inputStack.isEmpty()) {
            this.inputStack.shrink(count);
            if (this.inputStack.isEmpty()) {
                this.inputStack = ItemStack.EMPTY;
            }
            sync();
        }
    }

    public void growOutput(ItemStack stack) {
        if (this.outputStack.isEmpty()) {
            this.outputStack = stack.copy();
        } else if (ItemStack.isSameItemSameComponents(this.outputStack, stack)) {
            this.outputStack.grow(stack.getCount());
        }
        sync();
    }
}