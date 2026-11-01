package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.Herothepro.progressioned.recipe.KnappingRecipeInput;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = Progressioned.MOD_ID)
public class KnappingEvents {
    private static final long COOLDOWN_TICKS = 10;
    private static final Map<UUID, Long> LAST_HIT_TICK = new HashMap<>();

    @SubscribeEvent
    public static void onKnapping(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getEntity();
        if (player instanceof FakePlayer) return;

        ItemStack held = player.getMainHandItem();
        if (held.isEmpty()) return;

        BlockPos pos = event.getPos();
        BlockState hitState = level.getBlockState(pos);

        var holder = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KNAPPING_TYPE.get(), new KnappingRecipeInput(held, hitState), level)
                .orElse(null);
        if (holder == null) return;
        KnappingRecipe recipe = holder.value();

        long now = level.getGameTime();
        Long last = LAST_HIT_TICK.get(player.getUUID());
        if (last != null && now - last < COOLDOWN_TICKS) return;
        LAST_HIT_TICK.put(player.getUUID(), now);

        if (level.random.nextFloat() < recipe.chance()) {
            held.shrink(1);
            ItemHandlerHelper.giveItemToPlayer(player, recipe.result().copy());
            play(level, pos, recipe.successSound(), 1.0f);
        } else {
            play(level, pos, recipe.failSound(), 0.8f);
        }
    }

    @SubscribeEvent
    public static void onDeployerKnapping(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getEntity();
        ItemStack held = event.getItemStack();
        if (held.isEmpty()) return;

        BlockPos pos = event.getPos();
        BlockState hitState = level.getBlockState(pos);

        var holder = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KNAPPING_TYPE.get(), new KnappingRecipeInput(held, hitState), level)
                .orElse(null);
        if (holder == null) return;
        KnappingRecipe recipe = holder.value();

        if (player instanceof FakePlayer) {
            if (level.random.nextFloat() < recipe.chance()) {
                held.shrink(1);

                ItemStack resultStack = recipe.result().copy();
                ItemEntity itemEntity = new ItemEntity(
                        level,
                        pos.getX() + 0.5D,
                        pos.getY() + 1.0D,
                        pos.getZ() + 0.5D,
                        resultStack
                );
                itemEntity.setDeltaMovement(
                        level.random.nextGaussian() * 0.03D,
                        0.15D,
                        level.random.nextGaussian() * 0.03D
                );
                level.addFreshEntity(itemEntity);
                play(level, pos, recipe.successSound(), 1.0f);
            } else {
                play(level, pos, recipe.failSound(), 0.8f);
            }

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        LAST_HIT_TICK.remove(event.getEntity().getUUID());
    }

    private static void play(Level level, BlockPos pos, ResourceLocation id, float pitch) {
        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getOptional(id).orElse(SoundEvents.GRAVEL_BREAK);
        level.playSound(null, pos, sound, SoundSource.PLAYERS, 0.5f, pitch);
    }
}