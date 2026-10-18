package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Progressioned.MOD_ID)
public class ModCapabilites {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.MELTER_BE.get(), (melterEntity, side) -> melterEntity.tank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.MELTER_BE.get(), (melterEntity, side) -> melterEntity.externalItemHandler);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.TABLE_BE.get(), (tableEntity, side) -> tableEntity.inventory);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.TABLE_BE.get(), (tableEntity, side) -> tableEntity.tank);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.BASIN_BE.get(), (basin, side) -> basin.tank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.BASIN_BE.get(), (basin, side) -> basin.inventory);
    }
}
