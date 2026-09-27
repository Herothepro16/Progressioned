package net.Herothepro.progressioned.fluid;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;

@EventBusSubscriber(modid = Progressioned.MOD_ID, value = Dist.CLIENT)
public class FluidClientEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {

        ResourceLocation STILL_TEXTURE = ResourceLocation.fromNamespaceAndPath("progressioned", "fluid/molten_metal_still");
        ResourceLocation FLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath("progressioned", "fluid/molten_metal_flow");

        for (FluidHolder<?> fluidContainer : ModFluids.REGISTERED_FLUIDS) {
            event.registerFluidType(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return STILL_TEXTURE;}
                @Override
                public ResourceLocation getFlowingTexture() {
                    return FLOWING_TEXTURE;
                }
                @Override
                public int getTintColor() {
                    return fluidContainer.color();
                }
            }, fluidContainer.type().get());
        }
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(new DynamicFluidContainerModel.Colors(), ModFluids.MOLTEN_IRON.bucket().get());
        event.register(new DynamicFluidContainerModel.Colors(), ModFluids.MOLTEN_COPPER.bucket().get());
        event.register(new DynamicFluidContainerModel.Colors(), ModFluids.MOLTEN_GOLD.bucket().get());
    }
}
