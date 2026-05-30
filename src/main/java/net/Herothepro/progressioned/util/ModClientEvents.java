package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.Herothepro.progressioned.block.custom.entity.renderer.ChoppingBlockRenderer;
import net.Herothepro.progressioned.block.custom.entity.renderer.KilnBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Progressioned.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerBERs(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.CHOPPING_BLOCK_BE.get(),
                ChoppingBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.KILN_BE.get(),
                KilnBlockEntityRenderer::new);
    }
}