package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.Herothepro.progressioned.block.custom.entity.renderer.*;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.util.gui.HeaterScreen;
import net.Herothepro.progressioned.util.gui.MelterScreen;
import net.Herothepro.progressioned.util.gui.ModMenuTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ModClientEvents {

    @EventBusSubscriber(modid = Progressioned.MOD_ID, value = Dist.CLIENT)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerBERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.CHOPPING_BLOCK_BE.get(), ChoppingBlockRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.KILN_BE.get(), KilnBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.WORKSTONE_BE.get(), WorkstoneBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MELTER_BE.get(), MelterBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.FAUCET_BE.get(), FaucetBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.TABLE_BE.get(), TableBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.BASIN_BE.get(), BasinBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModList.get().getModContainerById(Progressioned.MOD_ID).ifPresent(container ->
                    container.registerExtensionPoint(IConfigScreenFactory.class,
                            (clientContainer, parent) -> new ModConfigScreen(parent))
            );
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.HEATER_MENU.get(), HeaterScreen::new);
            event.register(ModMenuTypes.MELTER_MENU.get(), MelterScreen::new);
        }
    }

    @EventBusSubscriber(modid = Progressioned.MOD_ID, value = Dist.CLIENT)
    public static class GameBusEvents {

        @SubscribeEvent
        public static void onItemTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            Component description = null;

            if (stack.is(ModItems.FLINT_SHARD.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Obtained by hitting a Flint on Stone").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModItems.FLINT_CHIP.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Obtained by hitting a Flint Shard on Stone").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModItems.PLANT_FIBER.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Obtained by breaking Grass with a knife").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModItems.SHARP_BONE.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Obtained by hitting a Bone on Stone").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModItems.FIRE_STICK.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Hold one in both hands then hold right click while looking at a block to light up").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModItems.STRAW.get())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Obtained by breaking fully grown Wheat with a knife").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.CHOPPING_BLOCK.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Right click with any Log then hit multiple times with an axe to get posts").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.CAMPFIRE_BASE.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Right click with any coal to turn it into an unlit campfire").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.PORCELAIN_KILN.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Put above fire to smelt items inside it").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.WORKSTONE.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Right clicks with Ingots then hit with a Hammer to turn into them plates").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.PORCELAIN_MELTER.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Melts materials into their molten form").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.PORCELAIN_HEATER.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Put under a melter to heat the melter").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            } else if (stack.is(ModBlocks.PORCELAIN_FAUCET.asItem())) {
                description = Component.literal("[").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal("Pours fluids from the block its on onto the block below it").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal("]").withStyle(ChatFormatting.WHITE));
            }

            if (description != null) {
                if (Screen.hasControlDown()) {
                    event.getToolTip().add(description);
                } else {
                    event.getToolTip().add(Component.literal("Hold ")
                            .withStyle(ChatFormatting.GRAY)
                            .append(Component.literal("[Ctrl]").withStyle(ChatFormatting.WHITE))
                            .append(Component.literal(" for info").withStyle(ChatFormatting.GRAY)));
                }
            }
        }
    }
}