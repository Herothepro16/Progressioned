package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.entity.ChoppingBlockEntity;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.Optional;

public class ChoppingBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<ChoppingBlock> CODEC = simpleCodec(ChoppingBlock::new);
    protected static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 6, 15);

    // 1. Define the Waterlogged property
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ChoppingBlock(Properties properties) {
        super(properties);
        // 2. Set the default state to NOT waterlogged
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof ChoppingBlockEntity be) {
                if (!be.getStoredLog().isEmpty()) {
                    spawnItem(level, pos, be.getStoredLog().copy());
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
        return state;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChoppingBlockEntity(pos, state);
    }

    // 3. Make sure it detects if it's being placed inside water
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    // 4. Return the correct fluid state based on whether it is waterlogged
    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // 5. Tell the engine to tick the water fluid if a block updates around it
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // 6. Register the property to your block's state definition builder
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof ChoppingBlockEntity be)) return InteractionResult.PASS;

        ItemStack held = player.getMainHandItem();

        if (held.is(ItemTags.LOGS) && be.getStoredLog().isEmpty()) {
            if (!level.isClientSide) {
                be.setStoredLog(held.copyWithCount(1));
                be.setChopHits(0);
                held.shrink(1);
                level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }

        if (!be.getStoredLog().isEmpty() && held.isEmpty()) {
            if (!level.isClientSide) {
                spawnItem(level, pos, be.getStoredLog().copy());
                be.setStoredLog(ItemStack.EMPTY);
                be.setChopHits(0);
                level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void spawnItem(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity item = new ItemEntity(level,
                pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, stack);
        item.setDefaultPickUpDelay();
        level.addFreshEntity(item);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return;
        if (!(level.getBlockEntity(pos) instanceof ChoppingBlockEntity be)) return;
        if (be.getStoredLog().isEmpty()) return;

        ItemStack held = player.getMainHandItem();
        if (!(held.getItem() instanceof AxeItem)) return;

        SingleRecipeInput input = new SingleRecipeInput(be.getStoredLog());
        Optional<RecipeHolder<ChoppingRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.CHOPPING_TYPE.get(), input, level);

        if (recipe.isEmpty()) return;

        ChoppingRecipe choppingRecipe = recipe.get().value();
        int hits = be.getChopHits() + 1;

        held.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f,
                0.8f + level.random.nextFloat() * 0.4f);

        if (hits >= choppingRecipe.getHits()) {
            spawnItem(level, pos, choppingRecipe.assemble(input, level.registryAccess()));
            be.setStoredLog(ItemStack.EMPTY);
            be.setChopHits(0);
            level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            be.setChopHits(hits);
        }
    }
}