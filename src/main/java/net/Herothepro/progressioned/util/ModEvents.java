package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.ModConfig;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.item.ModToolTiers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.Herothepro.progressioned.util.ModTags.Items.COMMON_KNIVES;

@EventBusSubscriber(modid = Progressioned.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        ItemStack stack = event.getEntity().getMainHandItem();

        if (stack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() == ModToolTiers.FLINT) {
            BlockState target = event.getTargetBlock();

            boolean isCopperOre = target.is(Tags.Blocks.ORES_COPPER);

            if (isCopperOre) {
                event.setCanHarvest(ModConfig.CAN_FLINT_MINE_COPPER.get());
            }
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if ((state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS)) && !held.isCorrectToolForDrops(state)) {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }

        if (held.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() == ModToolTiers.FLINT) {
            boolean isCopperOre = state.is(Tags.Blocks.ORES_COPPER);
            boolean canMineCopper = ModConfig.CAN_FLINT_MINE_COPPER.get();

            if (state.is(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL)) {
                if (!(isCopperOre && canMineCopper)) {
                    event.setNewSpeed(1.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();

        if (state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS)) {
            Player player = event.getPlayer();
            ItemStack held = player.getMainHandItem();

            if (!held.isCorrectToolForDrops(state)) {
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

        if (!held.is(Items.FLINT) || !event.getLevel().getBlockState(event.getPos()).is(Tags.Blocks.STONES) || event.getLevel().isClientSide) return;

        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.FLINT_SHARD.get()));
            event.getLevel().playSound(null, event.getPos(), SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 0.5f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(), SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 0.5f, 0.8f);
        }
    }

    @SubscribeEvent
    public static void onFlintShardHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(ModItems.FLINT_SHARD) || !event.getLevel().getBlockState(event.getPos()).is(Tags.Blocks.STONES) || event.getLevel().isClientSide) return;

        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.FLINT_CHIP.get()));
            event.getLevel().playSound(null, event.getPos(), SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 0.5f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(), SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 0.5f, 0.8f);
        }
    }

    @SubscribeEvent
    public static void onBoneHitStone(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = player.getMainHandItem();

        if (!held.is(Items.BONE ) || !event.getLevel().getBlockState(event.getPos()).is(Tags.Blocks.STONES) || event.getLevel().isClientSide) return;
        long currentTime = System.currentTimeMillis();
        UUID playerId = player.getUUID();
        if (lastHitTime.containsKey(playerId) && currentTime - lastHitTime.get(playerId) < 500) return;
        lastHitTime.put(playerId, currentTime);

        if (event.getLevel().random.nextFloat() < 0.20f) {
            held.shrink(1);
            player.getInventory().add(new ItemStack(ModItems.SHARP_BONE.get()));
            event.getLevel().playSound(null, event.getPos(), SoundEvents.BONE_BLOCK_HIT, SoundSource.PLAYERS, 0.5f, 1.0f);
        } else {
            event.getLevel().playSound(null, event.getPos(), SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 0.5f, 0.8f);
        }
    }

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof Player player)) return;

        ItemStack held = player.getMainHandItem();

        boolean isKnife = held.is(ModTags.Items.KNIVES)
                || (ModConfig.KNIFE_COMPAT.get() && held.is(COMMON_KNIVES));

        if (!isKnife) return;

        BlockState state = event.getState();

        if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS) || state.is(Blocks.FERN)) {
            held.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            float fiberChance = ModConfig.PLANT_FIBER_DROP_CHANCE.get() / 100.0f;

            if (event.getLevel().random.nextFloat() < fiberChance) {
                event.getDrops().add(new ItemEntity(event.getLevel(),
                        event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                        new ItemStack(ModItems.PLANT_FIBER.get())));
            }
        } else if (state.is(Blocks.WHEAT)) {
            if (state.getValue(CropBlock.AGE) != CropBlock.MAX_AGE) return;
            held.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            float strawChance = ModConfig.STRAW_DROP_CHANCE.get() / 100.0f;

            if (event.getLevel().random.nextFloat() < strawChance) {
                event.getDrops().add(new ItemEntity(event.getLevel(),
                        event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
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