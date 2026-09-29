package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.custom.entity.WorkstoneBlockEntity;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.recipe.WorkstoneRecipe;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WorkstoneBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<WorkstoneBlock> CODEC = simpleCodec(WorkstoneBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 10, 14);

    public WorkstoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED, false));
    }
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WorkstoneBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof WorkstoneBlockEntity be) {
            ItemStack input = be.getInput();

            if (!stack.isEmpty() && !stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/hammer")))) {
                if (input.isEmpty()) {
                    int amount = Math.min(stack.getCount(), 16);
                    if (!level.isClientSide) {
                        be.setInput(stack.split(amount));
                        level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    return ItemInteractionResult.SUCCESS;
                } else if (input.is(stack.getItem()) && input.getCount() < 16) {
                    int roomLeft = 16 - input.getCount();
                    int amountToAdd = Math.min(stack.getCount(), roomLeft);

                    if (!level.isClientSide && amountToAdd > 0) {
                        input.grow(amountToAdd);
                        stack.shrink(amountToAdd);
                        be.setInput(input);
                        level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof WorkstoneBlockEntity be) {
            boolean extractingOutput = !be.getOutput().isEmpty();
            ItemStack toExtract = extractingOutput ? be.getOutput() : be.getInput();

            if (!toExtract.isEmpty()) {
                if (!level.isClientSide) {
                    ItemStack copy = toExtract.copy();

                    if (player.getInventory().add(copy)) {
                        if (extractingOutput) {
                            be.setOutput(ItemStack.EMPTY);
                        } else {
                            be.setInput(ItemStack.EMPTY);
                            be.setHits(0);
                        }
                    } else {
                        int taken = toExtract.getCount() - copy.getCount();
                        if (taken > 0) {
                            if (extractingOutput) {
                                be.getOutput().shrink(taken);
                                be.setOutput(be.getOutput());
                            } else {
                                be.getInput().shrink(taken);
                                be.setInput(be.getInput());
                                if (be.getInput().isEmpty()) be.setHits(0);
                            }
                        }
                    }
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 1.0F);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof WorkstoneBlockEntity be) {
            ItemStack heldItem = player.getMainHandItem();

            if (heldItem.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/hammer"))) && !be.getInput().isEmpty()) {
                if (!level.isClientSide) {
                    SingleRecipeInput recipeInput = new SingleRecipeInput(be.getInput());
                    var recipeHolder = level.getRecipeManager().getRecipeFor(ModRecipes.WORKSTONE_TYPE.get(), recipeInput, level);

                    if (recipeHolder.isPresent()) {
                        WorkstoneRecipe recipe = recipeHolder.get().value();

                        boolean canOutput = be.getOutput().isEmpty() ||
                                (be.getOutput().is(recipe.output().getItem()) &&
                                        be.getOutput().getCount() + recipe.output().getCount() <= be.getOutput().getMaxStackSize());

                        if (canOutput) {
                            int newHits = be.getHits() + 1;
                            be.setHits(newHits);

                            heldItem.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                            if (newHits >= recipe.hitsRequired()) {
                                be.shrinkInput(recipe.inputCount());
                                be.growOutput(recipe.output().copy());
                                be.setHits(0);
                                level.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 0.75F, 0.9F);
                            } else {
                                level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.5F, 5.9F);
                            }
                        }
                    }
                }
            }
        }
        super.attack(state, level, pos, player);
    }
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WorkstoneBlockEntity be) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.getInput());
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.getOutput());

                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}