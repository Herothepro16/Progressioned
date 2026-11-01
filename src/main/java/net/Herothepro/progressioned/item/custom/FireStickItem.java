package net.Herothepro.progressioned.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FireStickItem extends Item {
    private static final int USE_DURATION = 72000;
    private static final int REQUIRED_TICKS = 72;

    public FireStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        if (!player.getMainHandItem().is(this) || !player.getOffhandItem().is(this)) {
            return InteractionResult.PASS;
        }

        player.startUsingItem(context.getHand());
        return InteractionResult.CONSUME;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player)) return;

        if (!player.getMainHandItem().is(this) || !player.getOffhandItem().is(this)) {
            player.stopUsingItem();
            return;
        }

        int usedTicks = USE_DURATION - remainingUseDuration;
        var hitResult = player.pick(5.0, 0, false);

        int cycle = usedTicks % 12;
        if (cycle == 0 || cycle == 6) {
            InteractionHand hand = cycle == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            player.swing(hand);

            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.WOOD_HIT, SoundSource.PLAYERS, 0.5f,
                    0.8f + level.random.nextFloat() * 0.4f);

            if (level.isClientSide && hitResult instanceof BlockHitResult blockHit) {
                Vec3 loc = blockHit.getLocation();
                for (int i = 0; i < 3; i++) {
                    level.addParticle(ParticleTypes.CRIT,
                            loc.x, loc.y + 0.05, loc.z,
                            (level.random.nextDouble() - 0.5) * 0.3,
                            level.random.nextDouble() * 0.2,
                            (level.random.nextDouble() - 0.5) * 0.3);
                }
                level.addParticle(ParticleTypes.SMOKE,
                        loc.x, loc.y + 0.05, loc.z,
                        (level.random.nextDouble() - 0.5) * 0.1,
                        level.random.nextDouble() * 0.05,
                        (level.random.nextDouble() - 0.5) * 0.1);
            }
        }

        if (!level.isClientSide && usedTicks >= REQUIRED_TICKS
                && hitResult instanceof BlockHitResult blockHit
                && hitResult.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {

            UseOnContext fakeContext = new UseOnContext(level, player, InteractionHand.MAIN_HAND,
                    new ItemStack(Items.FLINT_AND_STEEL), blockHit);
            InteractionResult result = Items.FLINT_AND_STEEL.useOn(fakeContext);

            if (result.consumesAction()) {
                player.getMainHandItem().shrink(1);
                player.getOffhandItem().shrink(1);
            }
            player.stopUsingItem();
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return stack;
    }
}