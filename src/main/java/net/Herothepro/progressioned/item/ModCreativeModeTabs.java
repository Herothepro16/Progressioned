package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Progressioned.MOD_ID);

    public static final Supplier<CreativeModeTab> PROGRESSIONED_TAB = CREATIVE_MODE_TAB.register("progressioned_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.FLINT_CHIP.get()))
                    .title(Component.translatable("creativetab.progressioned.stuff"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.FLINT_CHIP);
                        output.accept(ModItems.PLANT_FIBER);
                        output.accept(ModItems.FLINT_PICK);
                        output.accept(ModItems.FLINT_AXE);

                    }).build());

    public static void register(IEventBus eventBus) {CREATIVE_MODE_TAB.register(eventBus);}
}
