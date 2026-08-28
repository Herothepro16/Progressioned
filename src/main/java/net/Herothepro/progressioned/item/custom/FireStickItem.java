package net.Herothepro.progressioned.item.custom;

import net.Herothepro.progressioned.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FireStickItem extends Item {
    private static final int USE_DURATION = 72000;
    private static final int REQUIRED_TICKS = 60;

    public FireStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (!mainHand.is(this) || !offHand.is(this)) return InteractionResult.PASS;

        player.startUsingItem(context.getHand());
        return InteractionResult.CONSUME;
    }

    @Override
    public void onUseTick(Level level, net.minecraft.world.entity.LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player)) return;

        int usedTicks = USE_DURATION - remainingUseDuration;

        if (usedTicks % 8 == 0) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.WOOD_HIT, SoundSource.PLAYERS, 0.5f,
                    0.8f + level.random.nextFloat() * 0.4f);

        }

        if (usedTicks % 8 == 4) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.WOOD_HIT, SoundSource.PLAYERS, 0.5f,
                    0.8f + level.random.nextFloat() * 0.4f);
        }

        // Spark particles
        if (level.isClientSide && usedTicks % 4 == 0) {
            var hitResult = player.pick(5.0, 0, false);
            if (hitResult instanceof BlockHitResult blockHit) {
                BlockPos pos = blockHit.getBlockPos();
                level.addParticle(ParticleTypes.CRIT,
                        pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
                        (level.random.nextDouble() - 0.5) * 0.3,
                        level.random.nextDouble() * 0.1,
                        (level.random.nextDouble() - 0.5) * 0.3);
                level.addParticle(ParticleTypes.SMOKE,
                        pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
                        (level.random.nextDouble() - 0.5) * 0.1,
                        level.random.nextDouble() * 0.05,
                        (level.random.nextDouble() - 0.5) * 0.1);
            }
        }

        if (!level.isClientSide && usedTicks >= REQUIRED_TICKS) {
            var hitResult = player.pick(5.0, 0, false);
            if (hitResult instanceof BlockHitResult blockHit) {
                BlockPos pos = blockHit.getBlockPos();
                BlockState state = level.getBlockState(pos);

                if (state.is(Blocks.CAMPFIRE) && !state.getValue(CampfireBlock.LIT)) {
                    level.setBlock(pos, state.setValue(CampfireBlock.LIT, true), 3);
                    level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE,
                            SoundSource.BLOCKS, 1.0f, level.random.nextFloat() * 0.4f + 0.8f);
                    player.getMainHandItem().shrink(1);
                    player.getOffhandItem().shrink(1);
                    player.stopUsingItem();
                } else {
                    BlockPos firePos = pos.relative(blockHit.getDirection());
                    if (BaseFireBlock.canBePlacedAt(level, firePos, blockHit.getDirection())) {
                        level.setBlock(firePos, BaseFireBlock.getState(level, firePos), 11);
                        level.playSound(null, firePos, SoundEvents.FLINTANDSTEEL_USE,
                                SoundSource.BLOCKS, 1.0f, level.random.nextFloat() * 0.4f + 0.8f);
                        player.getMainHandItem().shrink(1);
                        player.getOffhandItem().shrink(1);
                        player.stopUsingItem();
                    }
                }
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity entity) {
        return stack;
    }
}