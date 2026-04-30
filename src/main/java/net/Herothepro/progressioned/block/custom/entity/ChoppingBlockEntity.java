package net.Herothepro.progressioned.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChoppingBlockEntity extends BlockEntity {
    private ItemStack storedLog = ItemStack.EMPTY;
    private int chopHits = 0;

    public ChoppingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHOPPING_BLOCK_BE.get(), pos, state);
    }

    public ItemStack getStoredLog() { return storedLog; }

    public void setStoredLog(ItemStack stack) {
        this.storedLog = stack.copy();
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getChopHits() { return chopHits; }

    public void setChopHits(int hits) {
        this.chopHits = hits;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!storedLog.isEmpty()) {
            tag.put("stored_log", storedLog.save(registries));
        }
        tag.putInt("chop_hits", chopHits);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storedLog = tag.contains("stored_log")
                ? ItemStack.parse(registries, tag.getCompound("stored_log")).orElse(ItemStack.EMPTY)
                : ItemStack.EMPTY;
        chopHits = tag.getInt("chop_hits");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}