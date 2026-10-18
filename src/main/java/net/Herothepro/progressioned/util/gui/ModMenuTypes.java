package net.Herothepro.progressioned.util.gui;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Progressioned.MOD_ID);

    public static final Supplier<MenuType<HeaterMenu>> HEATER_MENU =
            MENUS.register("heater_menu", () -> IMenuTypeExtension.create(HeaterMenu::new));
    public static final Supplier<MenuType<MelterMenu>> MELTER_MENU =
            MENUS.register("melter_menu", () -> IMenuTypeExtension.create(MelterMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}