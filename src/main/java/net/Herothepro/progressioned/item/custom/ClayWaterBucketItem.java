package net.Herothepro.progressioned.item.custom;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

public class ClayWaterBucketItem extends BucketItem {
    public ClayWaterBucketItem(Properties properties) {
        super(Fluids.WATER, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);

        if (result.getResult().consumesAction() && result.getObject().is(Items.BUCKET)) {
            return InteractionResultHolder.sidedSuccess(
                    new ItemStack(ModItems.CLAY_BUCKET.get()), level.isClientSide());
        }
        return result;
    }
}