package net.Herothepro.progressioned.item.custom;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ClayBucketInteractions {

    public static void register() {
        registerCauldron();
        registerDispenser();
    }

    private static void registerCauldron() {
        Item clayBucket = ModItems.CLAY_BUCKET.get();
        Item clayWater = ModItems.CLAY_WATER_BUCKET.get();
        Item clayLava = ModItems.CLAY_LAVA_BUCKET.get();

        CauldronInteraction.EMPTY.map().put(clayWater, (state, level, pos, player, hand, stack) ->
                emptyIntoCauldron(level, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY, new ItemStack(clayBucket)));

        CauldronInteraction.EMPTY.map().put(clayLava, (state, level, pos, player, hand, stack) ->
                emptyIntoCauldron(level, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.defaultBlockState(),
                        SoundEvents.BUCKET_EMPTY_LAVA, ItemStack.EMPTY));

        CauldronInteraction.WATER.map().put(clayBucket, (state, level, pos, player, hand, stack) ->
                CauldronInteraction.fillBucket(state, level, pos, player, hand, stack,
                        new ItemStack(clayWater),
                        s -> s.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL));

        CauldronInteraction.LAVA.map().put(clayBucket, (state, level, pos, player, hand, stack) ->
                CauldronInteraction.fillBucket(state, level, pos, player, hand, stack,
                        new ItemStack(clayLava),
                        s -> true,
                        SoundEvents.BUCKET_FILL_LAVA));
    }

    private static ItemInteractionResult emptyIntoCauldron(Level level, BlockPos pos, Player player,
                                                           InteractionHand hand, ItemStack stack,
                                                           BlockState newState, SoundEvent sound,
                                                           ItemStack remainder) {
        if (!level.isClientSide) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, remainder));
            if (remainder.isEmpty() && !player.getAbilities().instabuild) {
                level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, newState);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    private static void registerDispenser() {
        DispenserBlock.registerBehavior(ModItems.CLAY_WATER_BUCKET.get(),
                new ClayBucketDispenseBehavior(new ItemStack(ModItems.CLAY_BUCKET.get())));
        DispenserBlock.registerBehavior(ModItems.CLAY_LAVA_BUCKET.get(),
                new ClayBucketDispenseBehavior(ItemStack.EMPTY));
    }

    private static class ClayBucketDispenseBehavior extends DefaultDispenseItemBehavior {
        private final DefaultDispenseItemBehavior fallback = new DefaultDispenseItemBehavior();
        private final ItemStack remainder;

        ClayBucketDispenseBehavior(ItemStack remainder) {
            this.remainder = remainder;
        }

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem item = (DispensibleContainerItem) stack.getItem();
            BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level level = source.level();
            if (item.emptyContents(null, level, pos, null)) {
                item.checkExtraContent(null, level, stack, pos);
                if (remainder.isEmpty()) {
                    level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return this.consumeWithRemainder(source, stack, remainder.copy());
            }
            return fallback.dispense(source, stack);
        }
    }
}