package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class PostBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PostBlock> CODEC = simpleCodec(PostBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE_Y = Block.box(3, 0, 3, 13, 16, 13);
    protected static final VoxelShape SHAPE_X = Block.box(0, 3, 3, 16, 13, 13);
    protected static final VoxelShape SHAPE_Z = Block.box(3, 3, 0, 13, 13, 16);

    public PostBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<? extends RotatedPillarBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> SHAPE_X;
            case Z -> SHAPE_Z;
            default -> SHAPE_Y;
        };
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            Block stripped = getStrippedBlock();
            if (stripped != null) {
                return stripped.defaultBlockState()
                        .setValue(AXIS, state.getValue(AXIS))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

    private Block getStrippedBlock() {
        if (this == ModBlocks.OAK_POST.get()) return ModBlocks.STRIPPED_OAK_POST.get();
        if (this == ModBlocks.SPRUCE_POST.get()) return ModBlocks.STRIPPED_SPRUCE_POST.get();
        if (this == ModBlocks.BIRCH_POST.get()) return ModBlocks.STRIPPED_BIRCH_POST.get();
        if (this == ModBlocks.JUNGLE_POST.get()) return ModBlocks.STRIPPED_JUNGLE_POST.get();
        if (this == ModBlocks.ACACIA_POST.get()) return ModBlocks.STRIPPED_ACACIA_POST.get();
        if (this == ModBlocks.DARK_OAK_POST.get()) return ModBlocks.STRIPPED_DARK_OAK_POST.get();
        if (this == ModBlocks.MANGROVE_POST.get()) return ModBlocks.STRIPPED_MANGROVE_POST.get();
        if (this == ModBlocks.CHERRY_POST.get()) return ModBlocks.STRIPPED_CHERRY_POST.get();
        return null;
    }
}