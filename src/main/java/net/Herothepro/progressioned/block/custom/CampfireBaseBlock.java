package net.Herothepro.progressioned.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CampfireBaseBlock extends HorizontalDirectionalBlock {
    private static final TagKey<Item> COALS =
            ItemTags.COALS;

    public CampfireBaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 7, 16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected net.minecraft.world.ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                                                  BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(COALS)) {
            if (!level.isClientSide) {
                Direction facing = state.getValue(FACING);
                level.setBlock(pos, Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.LIT, false)
                        .setValue(CampfireBlock.FACING, facing), 3);
                if (!player.isCreative()) stack.shrink(1);
            }
            if (!level.isClientSide) {
                Direction facing = state.getValue(FACING);
                level.setBlock(pos, Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.LIT, false)
                        .setValue(CampfireBlock.FACING, facing), 3);
                if (!player.isCreative()) stack.shrink(1);

                level.playSound(null, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            if (level.isClientSide) {
                for (int i = 0; i < 8; i++) {
                    level.addParticle(ParticleTypes.SMOKE,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            (level.random.nextDouble() - 0.5) * 0.1,
                            level.random.nextDouble() * 0.1,
                            (level.random.nextDouble() - 0.5) * 0.1);
                }
            }
            return net.minecraft.world.ItemInteractionResult.SUCCESS;
        }
        return net.minecraft.world.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}