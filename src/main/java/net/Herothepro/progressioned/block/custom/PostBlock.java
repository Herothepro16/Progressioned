package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class PostBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PostBlock> CODEC = simpleCodec(PostBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty POS_A = IntegerProperty.create("pos_a", 0, 2);
    public static final IntegerProperty POS_B = IntegerProperty.create("pos_b", 0, 2);

    private static final int STEP = 3;

    private static final VoxelShape[][][] SHAPES = new VoxelShape[3][3][3];
    private static final VoxelShape[][][] COLLISION_SHAPES = new VoxelShape[3][3][3];

    static {
        for (Direction.Axis axis : Direction.Axis.values()) {
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    double da = (a - 1) * STEP;
                    double db = (b - 1) * STEP;
                    VoxelShape outer;
                    VoxelShape hole;
                    switch (axis) {
                        case X -> {
                            outer = Block.box(0, 3 + db, 3 + da, 16, 13 + db, 13 + da);
                            hole = Block.box(0, 7 + db, 7 + da, 16, 9 + db, 9 + da);
                        }
                        case Z -> {
                            outer = Block.box(3 + da, 3 + db, 0, 13 + da, 13 + db, 16);
                            hole = Block.box(7 + da, 7 + db, 0, 9 + da, 9 + db, 16);
                        }
                        default -> {
                            outer = Block.box(3 + da, 0, 3 + db, 13 + da, 16, 13 + db);
                            hole = Block.box(7 + da, 0, 7 + db, 9 + da, 16, 9 + db);
                        }
                    }
                    SHAPES[axis.ordinal()][a][b] = outer;
                    COLLISION_SHAPES[axis.ordinal()][a][b] = Shapes.join(outer, hole, BooleanOp.ONLY_FIRST);
                }
            }
        }
    }

    public PostBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(WATERLOGGED, false)
                .setValue(POS_A, 1)
                .setValue(POS_B, 1));
    }

    @Override
    public MapCodec<? extends RotatedPillarBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, POS_A, POS_B);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = super.getStateForPlacement(context);

        BlockPos pos = context.getClickedPos();
        Vec3 hit = context.getClickLocation();
        double fx = hit.x - pos.getX();
        double fy = hit.y - pos.getY();
        double fz = hit.z - pos.getZ();

        BlockState against = context.replacingClickedOnBlock()
                ? null
                : context.getLevel().getBlockState(pos.relative(context.getClickedFace().getOpposite()));

        int a;
        int b;
        if (against != null
                && against.getBlock() instanceof PostBlock
                && against.getValue(AXIS) == state.getValue(AXIS)
                && !context.isSecondaryUseActive()) {
            a = against.getValue(POS_A);
            b = against.getValue(POS_B);
        } else {
            switch (context.getClickedFace().getAxis()) {
                case X -> { a = third(fz); b = third(fy); }
                case Z -> { a = third(fx); b = third(fy); }
                default -> { a = third(fx); b = third(fz); }
            }
        }

        return state
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(POS_A, a)
                .setValue(POS_B, b);
    }

    private static int third(double fraction) {
        return Mth.clamp((int) (fraction * 3.0D), 0, 2);
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
        return SHAPES[state.getValue(AXIS).ordinal()][state.getValue(POS_A)][state.getValue(POS_B)];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPES[state.getValue(AXIS).ordinal()][state.getValue(POS_A)][state.getValue(POS_B)];
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    private static int[] toOffset(BlockState state) {
        int a = state.getValue(POS_A) - 1;
        int b = state.getValue(POS_B) - 1;
        return switch (state.getValue(AXIS)) {
            case X -> new int[]{0, b, a};
            case Z -> new int[]{a, b, 0};
            default -> new int[]{a, 0, b};
        };
    }

    private static BlockState withOffset(BlockState state, int dx, int dy, int dz) {
        return switch (state.getValue(AXIS)) {
            case X -> state.setValue(POS_A, dz + 1).setValue(POS_B, dy + 1);
            case Z -> state.setValue(POS_A, dx + 1).setValue(POS_B, dy + 1);
            default -> state.setValue(POS_A, dx + 1).setValue(POS_B, dz + 1);
        };
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        int[] o = toOffset(state);
        int nx = o[0];
        int nz = o[2];
        switch (rotation) {
            case CLOCKWISE_90 -> { nx = -o[2]; nz = o[0]; }
            case CLOCKWISE_180 -> { nx = -o[0]; nz = -o[2]; }
            case COUNTERCLOCKWISE_90 -> { nx = o[2]; nz = -o[0]; }
            default -> { }
        }
        return withOffset(super.rotate(state, rotation), nx, o[1], nz);
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        int[] o = toOffset(state);
        int nx = o[0];
        int nz = o[2];
        switch (mirror) {
            case LEFT_RIGHT -> nz = -o[2];
            case FRONT_BACK -> nx = -o[0];
            default -> { }
        }
        return withOffset(state, nx, o[1], nz);
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            Block stripped = getStrippedBlock();
            if (stripped != null && stripped != Blocks.AIR) {
                return stripped.defaultBlockState()
                        .setValue(AXIS, state.getValue(AXIS))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                        .setValue(POS_A, state.getValue(POS_A))
                        .setValue(POS_B, state.getValue(POS_B));
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