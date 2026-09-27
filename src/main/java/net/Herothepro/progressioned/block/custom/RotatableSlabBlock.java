package net.Herothepro.progressioned.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.SlabType;

public class RotatableSlabBlock extends SlabBlock {
    public static final DirectionProperty BOTTOM_FACING = DirectionProperty.create("bottom_facing", Direction.Plane.HORIZONTAL);
    public static final DirectionProperty TOP_FACING = DirectionProperty.create("top_facing", Direction.Plane.HORIZONTAL);

    public RotatableSlabBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(TYPE, SlabType.BOTTOM)
                .setValue(BOTTOM_FACING, Direction.NORTH)
                .setValue(TOP_FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        Direction playerFacing = context.getHorizontalDirection().getOpposite();

        if (clickedState.is(this)) {
            SlabType clickedType = clickedState.getValue(TYPE);
            if (clickedType == SlabType.BOTTOM) {
                return clickedState.setValue(TYPE, SlabType.DOUBLE).setValue(TOP_FACING, playerFacing);
            } else if (clickedType == SlabType.TOP) {
                return clickedState.setValue(TYPE, SlabType.DOUBLE).setValue(BOTTOM_FACING, playerFacing);
            }
        }

        BlockState superState = super.getStateForPlacement(context);
        if (superState != null) {
            return superState
                    .setValue(BOTTOM_FACING, playerFacing)
                    .setValue(TOP_FACING, playerFacing);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOTTOM_FACING, TOP_FACING);
    }
}