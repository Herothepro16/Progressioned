package net.Herothepro.progressioned;

import com.mojang.logging.LogUtils;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.Herothepro.progressioned.fluid.ModFluids;
import net.Herothepro.progressioned.item.ModCreativeModeTabs;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.util.ModConfigScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

@Mod(Progressioned.MOD_ID)
public class Progressioned {
    public static final String MOD_ID = "progressioned";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MOD_ID);

    public Progressioned(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onClientSetup);
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);

        FLUIDS.register(modEventBus);
        FLUID_TYPES.register(modEventBus);
        ModFluids.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModRecipes.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FireBlock fire = (FireBlock) Blocks.FIRE;
            fire.setFlammable(Blocks.COAL_BLOCK, 0, 0);
        });
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        ModContainer container = net.neoforged.fml.ModList.get().getModContainerById(MOD_ID).orElseThrow();
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (clientContainer, parent) -> new ModConfigScreen(parent)
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}