package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = Progressioned.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().is(BlockTags.LOGS)) {
            Player player = event.getPlayer();
            ItemStack held = player.getMainHandItem();
            if (!held.isCorrectToolForDrops(event.getState())) {
                event.getLevel().destroyBlock(event.getPos(), false);
                event.setCanceled(true);
            }
        }
    }
    private static final Map<UUID, Long> lastHitTime = new HashMap<>();

    @SubscribeEvent
    public static void onFlintHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(Items.FLINT)) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(Blocks.STONE)) return;
        if (event.getLevel().isClientSide) return;

        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.FLINT_SHARD.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }
    @SubscribeEvent
    public static void onFlintShardHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(ModItems.FLINT_SHARD)) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(Blocks.STONE)) return;
        if (event.getLevel().isClientSide) return;

        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.FLINT_CHIP.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }
    @SubscribeEvent
    public static void onBoneHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(Items.BONE)) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(Blocks.STONE)) return;
        if (event.getLevel().isClientSide) return;

        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.SHARP_BONE.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.BONE_BLOCK_HIT, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (state.is(BlockTags.LOGS) && held.isEmpty()) {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof Player player)) return;
        if (!player.getMainHandItem().is(ModTags.Items.KNIVES)) return;

        BlockState state = event.getState();

        if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS) || state.is(Blocks.FERN)) {
            player.getMainHandItem().hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

            if (event.getLevel().random.nextFloat() < 0.75f) {
                event.getDrops().add(new ItemEntity(event.getLevel(),
                        event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(),
                        new ItemStack(ModItems.PLANT_FIBER.get())));
            }
        }

        else if (state.is(Blocks.WHEAT)) {

            if (state.getValue(CropBlock.AGE) != CropBlock.MAX_AGE) return;

            player.getMainHandItem().hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

            if (event.getLevel().random.nextFloat() < 0.75f) {
                event.getDrops().add(new ItemEntity(event.getLevel(),
                        event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(),
                        new ItemStack(ModItems.STRAW.get())));
            }
        }
    }
    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(ModBlocks.CHARCOAL_BLOCK.get().asItem())) {
            event.setBurnTime(16000);
        }
    }
}
