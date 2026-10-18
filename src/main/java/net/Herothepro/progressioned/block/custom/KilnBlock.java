package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.custom.entity.KilnBlockEntity;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class KilnBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape COLLISION_SHAPE = Shapes.join(
            Shapes.block(),
            Block.box(7.0D, 7.0D, 7.0D, 9.0D, 9.0D, 9.0D),
            BooleanOp.ONLY_FIRST);

    public KilnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KilnBlockEntity(pos, state);
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {return 1.0F;}
    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {return true;}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.KILN_BE.get(), KilnBlockEntity::tick);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        if (!(level.getBlockEntity(pos) instanceof KilnBlockEntity kiln)) return InteractionResult.PASS;

        ItemStack held = player.getMainHandItem();

        if (held.isEmpty()) {
            if (!kiln.getOutput().isEmpty()) {
                String name = kiln.getOutput().getHoverName().getString();
                int count = kiln.getOutput().getCount();
                ItemStack retrieved = kiln.extractOutput();
                player.addItem(retrieved);
                player.displayClientMessage(
                        Component.literal("Retrieved: " + count + "x " + name), true);
                return InteractionResult.CONSUME;
            }

            if (player.isShiftKeyDown() && !kiln.getInput().isEmpty()) {
                String name = kiln.getInput().getHoverName().getString();
                int count = kiln.getInput().getCount();
                ItemStack returned = kiln.extractInput();
                player.addItem(returned);
                player.displayClientMessage(
                        Component.literal("Retrieved input: " + count + "x " + name), true);
                return InteractionResult.CONSUME;
            }

            if (!kiln.getInput().isEmpty()) {
                player.displayClientMessage(
                        Component.literal("Smelting " + kiln.getInput().getCount() + "x " + kiln.getInput().getHoverName().getString() + "... ("
                                + (kiln.getCookTime() * 100 / KilnBlockEntity.COOK_TIME_TOTAL) + "%)"), true);
            } else {
                player.displayClientMessage(
                        Component.literal("Kiln is empty."), true);
            }
            return InteractionResult.PASS;
        }

        int added = kiln.addInput(held, level);
        if (added > 0) {
            held.shrink(added);
            if (held.isEmpty()) player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            player.displayClientMessage(
                    Component.literal("Added " + added + " item(s). ("
                            + kiln.getInput().getCount() + "/16)"), true);
            return InteractionResult.CONSUME;
        } else if (added == -1) {
            return InteractionResult.PASS;
        } else {
            if (!kiln.getInput().isEmpty()
                    && !ItemStack.isSameItemSameComponents(kiln.getInput(), held)) {
                player.displayClientMessage(
                        Component.literal("Kiln already contains a different item!"), true);
            } else {
                player.displayClientMessage(
                        Component.literal("Input slot is full! (16/16)"), true);
            }
            return InteractionResult.PASS;
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) return;

        double d0 = pos.getX() + 0.5;
        double d1 = pos.getY();
        double d2 = pos.getZ() + 0.5;

        if (random.nextDouble() < 0.1) {
            level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE,
                    SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }

        Direction direction = state.getValue(FACING);
        Direction.Axis axis = direction.getAxis();
        double d3 = 0.52;
        double d4 = random.nextDouble() * 0.6 - 0.3;
        double d5 = axis == Direction.Axis.X ? direction.getStepX() * d3 : d4;
        double d6 = random.nextDouble() * 6.0 / 16.0;
        double d7 = axis == Direction.Axis.Z ? direction.getStepZ() * d3 : d4;

        level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0, 0, 0);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof KilnBlockEntity kiln) {
                kiln.dropContents(level, pos);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}