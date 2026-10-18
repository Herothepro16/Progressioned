package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.block.custom.FaucetBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FaucetBlockEntity extends BlockEntity {

    private boolean isPouring = false;
    private boolean lastRedstone = false;
    private FluidStack renderFluid = FluidStack.EMPTY;

    public FaucetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FAUCET_BE.get(), pos, state);
    }

    public FluidStack getRenderFluid() { return renderFluid; }
    public boolean isPouring() { return isPouring; }

    public void activate() {
        if (!isPouring) {
            isPouring = true;
            updateBlockState();
        } else {
            stopPouring();
        }
    }

    public void handleRedstone(boolean hasSignal) {
        if (hasSignal != lastRedstone) {
            lastRedstone = hasSignal;
            if (hasSignal && !isPouring) {
                isPouring = true;
                updateBlockState();
            } else if (!hasSignal && isPouring && !level.hasNeighborSignal(worldPosition)) {
                stopPouring();
            }
            setChanged();
        }
    }

    private void stopPouring() {
        isPouring = false;
        renderFluid = FluidStack.EMPTY;
        updateBlockState();
    }

    private void updateBlockState() {
        if (level != null) {
            BlockState state = getBlockState();
            if (state.hasProperty(FaucetBlock.POURING)) {
                level.setBlock(worldPosition, state.setValue(FaucetBlock.POURING, isPouring), Block.UPDATE_ALL);
            }
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
            setChanged();
        }
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FaucetBlockEntity blockEntity) {
        if (!blockEntity.isPouring && !blockEntity.lastRedstone) return;

        Direction facing = blockState.getValue(FaucetBlock.FACING);
        BlockPos posBehind = blockPos.relative(facing.getOpposite());
        BlockPos posBelow = blockPos.below();

        IFluidHandler source = level.getCapability(Capabilities.FluidHandler.BLOCK, posBehind, facing);
        IFluidHandler destination = level.getCapability(Capabilities.FluidHandler.BLOCK, posBelow, Direction.UP);

        if (source != null && destination != null) {
            FluidStack simulatedDrain = source.drain(12, IFluidHandler.FluidAction.SIMULATE);

            if (!simulatedDrain.isEmpty()) {
                int filled = destination.fill(simulatedDrain, IFluidHandler.FluidAction.SIMULATE);

                if (filled > 0) {
                    if (!blockEntity.isPouring) {
                        blockEntity.isPouring = true;
                        blockEntity.updateBlockState();
                    }

                    FluidStack actuallyDrained = source.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                    destination.fill(actuallyDrained, IFluidHandler.FluidAction.EXECUTE);

                    if (blockEntity.renderFluid.isEmpty() || blockEntity.renderFluid.getFluid() != actuallyDrained.getFluid()) {
                        blockEntity.renderFluid = actuallyDrained.copy();
                        blockEntity.renderFluid.setAmount(1000);
                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL);
                    }
                    return;
                }
            }
        }

        if (blockEntity.isPouring && !blockEntity.lastRedstone) {
            blockEntity.stopPouring();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("pouring", isPouring);
        tag.putBoolean("lastRedstone", lastRedstone);
        if (!renderFluid.isEmpty()) {
            tag.put("fluid", renderFluid.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        isPouring = tag.getBoolean("pouring");
        lastRedstone = tag.getBoolean("lastRedstone");
        if (tag.contains("fluid")) {
            renderFluid = FluidStack.parse(registries, tag.getCompound("fluid")).orElse(FluidStack.EMPTY);
        } else {
            renderFluid = FluidStack.EMPTY;
        }
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
}