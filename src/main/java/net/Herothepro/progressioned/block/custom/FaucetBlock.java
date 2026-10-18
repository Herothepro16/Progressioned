package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.custom.entity.FaucetBlockEntity;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FaucetBlock extends BaseEntityBlock {
    public static final MapCodec<FaucetBlock> CODEC = simpleCodec(FaucetBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POURING = BooleanProperty.create("pouring");

    private static final Map<Direction, VoxelShape> SHAPES = new HashMap<>() {{
        put(Direction.NORTH, Shapes.or(
                Shapes.box(0.25, 0.3125, 0.625, 0.375, 0.5625, 1.0),
                Shapes.box(0.625, 0.3125, 0.625, 0.75, 0.5625, 1.0),
                Shapes.box(0.25, 0.1875, 0.625, 0.75, 0.3125, 1.0)));
        put(Direction.SOUTH, Shapes.or(
                Shapes.box(0.625, 0.3125, 0.0, 0.75, 0.5625, 0.375),
                Shapes.box(0.25, 0.3125, 0.0, 0.375, 0.5625, 0.375),
                Shapes.box(0.25, 0.1875, 0.0, 0.75, 0.3125, 0.375)));
        put(Direction.WEST, Shapes.or(
                Shapes.box(0.625, 0.3125, 0.25, 1.0, 0.5625, 0.375),
                Shapes.box(0.625, 0.3125, 0.625, 1.0, 0.5625, 0.75),
                Shapes.box(0.625, 0.1875, 0.25, 1.0, 0.3125, 0.75)));
        put(Direction.EAST, Shapes.or(
                Shapes.box(0.0, 0.3125, 0.625, 0.375, 0.5625, 0.75),
                Shapes.box(0.0, 0.3125, 0.25, 0.375, 0.5625, 0.375),
                Shapes.box(0.0, 0.1875, 0.25, 0.375, 0.3125, 0.75)));
    }};

    public FaucetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POURING, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POURING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FaucetBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.FAUCET_BE.get(), FaucetBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof FaucetBlockEntity blockEntity) {
            if (!level.isClientSide) {
                blockEntity.activate();
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.getBlockEntity(pos) instanceof FaucetBlockEntity blockEntity) {
            boolean hasSignal = level.hasNeighborSignal(pos);
            blockEntity.handleRedstone(hasSignal);
        }
    }
}