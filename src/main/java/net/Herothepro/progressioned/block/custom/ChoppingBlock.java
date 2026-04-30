package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.entity.ChoppingBlockEntity;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;
import java.util.Optional;

public class ChoppingBlock extends BaseEntityBlock {
    public static final MapCodec<ChoppingBlock> CODEC = simpleCodec(ChoppingBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 6, 16);
    private static final int HITS_REQUIRED = 5;

    public ChoppingBlock(Properties properties) {
        super(properties);
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

        // Retrieve log with empty hand
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