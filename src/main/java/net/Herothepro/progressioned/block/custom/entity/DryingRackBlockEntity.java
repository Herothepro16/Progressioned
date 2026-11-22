package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.recipe.DryingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DryingRackBlockEntity extends BlockEntity {

    public static final int SLOTS = 1;

    private final int[] progress = new int[SLOTS];
    private final int[] maxTime = new int[SLOTS];

    public final ItemStackHandler inventory = new ItemStackHandler(SLOTS) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            progress[slot] = 0;
            maxTime[slot] = 0;
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRYING_RACK_BE.get(), pos, state);
    }

    private DryingRecipe findRecipe(ItemStack stack) {
        if (level == null || stack.isEmpty()) return null;
        return level.getRecipeManager()
                .getRecipeFor(ModRecipes.DRYING_TYPE.get(), new SingleRecipeInput(stack), level)
                .map(RecipeHolder::value)
                .orElse(null);
    }

    private int firstEmptySlot() {
        for (int i = 0; i < SLOTS; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) return i;
        }
        return -1;
    }

    public boolean isEmpty() {
        for (int i = 0; i < SLOTS; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    public boolean canAccept(ItemStack stack) {
        return firstEmptySlot() >= 0 && findRecipe(stack) != null;
    }

    public boolean insert(ItemStack stack) {
        int slot = firstEmptySlot();
        if (slot < 0) return false;
        inventory.setStackInSlot(slot, stack.copyWithCount(1));
        return true;
    }

    public ItemStack removeLast() {
        for (int i = SLOTS - 1; i >= 0; i--) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                ItemStack removed = stack.copy();
                inventory.setStackInSlot(i, ItemStack.EMPTY);
                return removed;
            }
        }
        return ItemStack.EMPTY;
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        boolean drying = false;
        for (int i = 0; i < SLOTS; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (maxTime[i] == 0) {
                DryingRecipe recipe = findRecipe(stack);
                maxTime[i] = recipe == null ? -1 : Math.max(1, recipe.dryingTime());
            }
            if (maxTime[i] < 0) continue;

            drying = true;
            if (++progress[i] >= maxTime[i]) {
                DryingRecipe recipe = findRecipe(stack);
                if (recipe != null) {
                    inventory.setStackInSlot(i, recipe.result().copy());
                    level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS, 0.6f, 1.2f);
                } else {
                    maxTime[i] = -1;
                }
            }
        }
        if (drying) setChanged();
    }

    public void dropContents() {
        if (level == null || level.isClientSide) return;
        for (int i = 0; i < SLOTS; i++) {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), inventory.getStackInSlot(i));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putIntArray("progress", progress);
        tag.putIntArray("maxTime", maxTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("inventory")) inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        int[] savedProgress = tag.getIntArray("progress");
        if (savedProgress.length == SLOTS) System.arraycopy(savedProgress, 0, progress, 0, SLOTS);
        int[] savedMax = tag.getIntArray("maxTime");
        if (savedMax.length == SLOTS) System.arraycopy(savedMax, 0, maxTime, 0, SLOTS);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
