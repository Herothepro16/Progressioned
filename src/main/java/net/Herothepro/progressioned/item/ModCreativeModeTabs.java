package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
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
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.FLINT_SHARD.get()))
                    .title(Component.translatable("creativetab.progressioned.stuff"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.FLINT_SHARD);
                        output.accept(ModItems.FLINT_CHIP);
                        output.accept(ModItems.PLANT_FIBER);
                        output.accept(ModItems.PLANT_STRING);
                        output.accept(ModItems.FIBER_MESH);
                        output.accept(ModItems.SHARP_BONE);
                        output.accept(ModItems.FLINT_PICK);
                        output.accept(ModItems.FLINT_AXE);
                        output.accept(ModItems.FLINT_SHOVEL);
                        output.accept(ModItems.FLINT_KNIFE);
                        output.accept(ModItems.FLINT_SPEAR);
                        output.accept(ModItems.BONE_SWORD);
                        output.accept(ModItems.BONE_SPEAR);
                        output.accept(ModItems.COPPER_SWORD);
                        output.accept(ModItems.COPPER_PICKAXE);
                        output.accept(ModItems.COPPER_AXE);
                        output.accept(ModItems.COPPER_SHOVEL);
                        output.accept(ModItems.COPPER_HOE);
                        output.accept(ModItems.COPPER_SPEAR);

                    }).build());

    public static void register(IEventBus eventBus) {CREATIVE_MODE_TAB.register(eventBus);}
}
