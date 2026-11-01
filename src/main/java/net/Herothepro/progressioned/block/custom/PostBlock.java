package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class PostBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PostBlock> CODEC = simpleCodec(PostBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE_Y = Block.box(3, 0, 3, 13, 16, 13);
    protected static final VoxelShape SHAPE_X = Block.box(0, 3, 3, 16, 13, 13);
    protected static final VoxelShape SHAPE_Z = Block.box(3, 3, 0, 13, 13, 16);

    protected static final VoxelShape COLLISION_SHAPE_Y = Shapes.join(
            SHAPE_Y,
            Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D),
            BooleanOp.ONLY_FIRST);

    protected static final VoxelShape COLLISION_SHAPE_X = Shapes.join(
            SHAPE_X,
            Block.box(0.0D, 7.0D, 7.0D, 16.0D, 9.0D, 9.0D),
            BooleanOp.ONLY_FIRST);

    protected static final VoxelShape COLLISION_SHAPE_Z = Shapes.join(
            SHAPE_Z,
            Block.box(7.0D, 7.0D, 0.0D, 9.0D, 9.0D, 16.0D),
            BooleanOp.ONLY_FIRST);

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
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> COLLISION_SHAPE_X;
            case Z -> COLLISION_SHAPE_Z;
            default -> COLLISION_SHAPE_Y;
        };
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            Block stripped = getStrippedBlock();
            if (stripped != null && stripped != Blocks.AIR) {
                return stripped.defaultBlockState()
                        .setValue(AXIS, state.getValue(AXIS))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            }
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

    private Block getStrippedBlock() {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(this);
        if (id != null) {
            String path = id.getPath();
            if (!path.startsWith("stripped_")) {
                ResourceLocation strippedId = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "stripped_" + path);
                return BuiltInRegistries.BLOCK.get(strippedId);
            }
        }
        return null;
    }
}