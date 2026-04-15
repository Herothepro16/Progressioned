package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
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
                event.getLevel().destroyBlock(event.getPos(), false); // no drops
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

        CompoundTag tag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int hits = tag.getInt("flint_hits");
        hits++;

        if (hits >= 5) {
            held.shrink(1);
            if (!held.isEmpty()) {
                CompoundTag newTag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                newTag.putInt("flint_hits", 0);
                held.set(DataComponents.CUSTOM_DATA, CustomData.of(newTag));
            }
            player.getInventory().add(new ItemStack(ModItems.FLINT_SHARD.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            tag.putInt("flint_hits", hits);
            held.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
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

        // Cooldown check - prevent multiple fires per click
        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        CompoundTag tag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int hits = tag.getInt("flint_shard_hits");
        hits++;

        if (hits >= 4) {
            held.shrink(1);
            // Reset hits on remaining stack
            if (!held.isEmpty()) {
                CompoundTag newTag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                newTag.putInt("flint_shard_hits", 0);
                held.set(DataComponents.CUSTOM_DATA, CustomData.of(newTag));
            }
            player.getInventory().add(new ItemStack(ModItems.FLINT_CHIP.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            tag.putInt("flint_shard_hits", hits);
            held.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
        }
    }
    @SubscribeEvent
    public static void onSharpBoneHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(Items.BONE)) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(Blocks.STONE)) return;
        if (event.getLevel().isClientSide) return;

        // Cooldown check - prevent multiple fires per click
        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        CompoundTag tag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int hits = tag.getInt("bone_hits");
        hits++;

        if (hits >= 4) {
            held.shrink(1);
            // Reset hits on remaining stack
            if (!held.isEmpty()) {
                CompoundTag newTag = held.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                newTag.putInt("bone_hits", 0);
                held.set(DataComponents.CUSTOM_DATA, CustomData.of(newTag));
            }
            player.getInventory().add(new ItemStack(ModItems.SHARP_BONE.get()));
            event.getLevel().playSound(null, event.getPos(),
                    SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
        } else {
            tag.putInt("bone_hits", hits);
            held.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
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
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f); // 10% of normal speed
        }
    }
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        BlockState state = event.getState();
        Player player = (Player) event.getBreaker();

        if (player == null) return;
        if (!state.is(Blocks.SHORT_GRASS) && !state.is(Blocks.TALL_GRASS)) return;
        if (!player.getMainHandItem().is(ModItems.FLINT_KNIFE.get())) return;

        player.getMainHandItem().hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

        if (event.getLevel().random.nextFloat() < 0.15f) {
            event.getDrops().add(new ItemEntity(event.getLevel(),
                    event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(),
                    new ItemStack(ModItems.PLANT_FIBER.get())));
        }
    }
}
