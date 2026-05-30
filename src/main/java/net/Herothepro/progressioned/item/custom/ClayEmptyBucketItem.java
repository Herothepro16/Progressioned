package net.Herothepro.progressioned.item.custom;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("deprecation")
public class ClayEmptyBucketItem extends Item {
    public ClayEmptyBucketItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        }

        BlockPos pos = blockhitresult.getBlockPos();
        Direction direction = blockhitresult.getDirection();
        BlockPos pos1 = pos.relative(direction);

        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos1, direction, itemstack)) {
            BlockState state = level.getBlockState(pos);

            if (state.getBlock() instanceof BucketPickup pickup) {
                FluidState fluidState = level.getFluidState(pos);
                Fluid fluid = fluidState.getType();

                Item filledBucket = null;
                // Only accept water or lava
                if (fluid == Fluids.WATER) {
                    filledBucket = ModItems.CLAY_WATER_BUCKET.get();
                } else if (fluid == Fluids.LAVA) {
                    filledBucket = ModItems.CLAY_LAVA_BUCKET.get();
                }

                if (filledBucket != null) {
                    ItemStack filledStack = pickup.pickupBlock(player, level, pos, state);
                    if (!filledStack.isEmpty()) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                        pickup.getPickupSound().ifPresent(sound -> player.playSound(sound, 1.0F, 1.0F));
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                        ItemStack result = ItemUtils.createFilledResult(itemstack, player, new ItemStack(filledBucket));
                        return InteractionResultHolder.sidedSuccess(result, level.isClientSide());
                    }
                }
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }
}
