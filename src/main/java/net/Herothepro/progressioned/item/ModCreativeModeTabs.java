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

                        output.accept(ModItems.FLINT_SHARD.get());
                        output.accept(ModItems.FLINT_CHIP.get());
                        output.accept(ModItems.PLANT_FIBER.get());
                        output.accept(ModItems.PLANT_STRING.get());
                        output.accept(ModItems.FIBER_MESH.get());
                        output.accept(ModItems.FIRE_STICK.get());
                        output.accept(ModItems.SHARP_BONE.get());
                        output.accept(ModItems.PORCELAIN_CLAY_BALL.get());
                        output.accept(ModItems.PORCELAIN_CLAY_BRICK.get());
                        output.accept(ModItems.UNFIRED_CLAY_BUCKET.get());
                        output.accept(ModItems.CLAY_BUCKET.get());
                        output.accept(ModItems.CLAY_WATER_BUCKET.get());
                        output.accept(ModItems.CLAY_LAVA_BUCKET.get());
                        output.accept(ModItems.FLINT_PICK.get());
                        output.accept(ModItems.FLINT_AXE.get());
                        output.accept(ModItems.FLINT_SHOVEL.get());
                        output.accept(ModItems.FLINT_KNIFE.get());
                        output.accept(ModItems.FLINT_SPEAR.get());
                        output.accept(ModItems.BONE_SWORD.get());
                        output.accept(ModItems.BONE_SPEAR.get());
                        output.accept(ModItems.BONE_CLUB.get());
                        output.accept(ModItems.AMETHYST_BONE_SWORD.get());
                        output.accept(ModItems.AMETHYST_BONE_SPEAR.get());
                        output.accept(ModItems.GOLDEN_BONE_SWORD.get());
                        output.accept(ModItems.GOLDEN_BONE_SPEAR.get());
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_PICKAXE.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_HOE.get());
                        output.accept(ModItems.COPPER_SPEAR.get());
                        output.accept(ModItems.FLINT_SAW.get());
                        output.accept(ModItems.IRON_SAW.get());
                        output.accept(ModItems.DIAMOND_SAW.get());
                        output.accept(new ItemStack(ModItems.CHOPPING_BLOCK.get()));
                        output.accept(new ItemStack(ModItems.CAMPFIRE_BASE.get()));
                        output.accept(new ItemStack(ModItems.PORCELAIN_KILN.get()));
                        output.accept(ModBlocks.OAK_POST);
                        output.accept(ModBlocks.SPRUCE_POST);
                        output.accept(ModBlocks.BIRCH_POST);
                        output.accept(ModBlocks.JUNGLE_POST);
                        output.accept(ModBlocks.ACACIA_POST);
                        output.accept(ModBlocks.DARK_OAK_POST);
                        output.accept(ModBlocks.MANGROVE_POST);
                        output.accept(ModBlocks.CHERRY_POST);
                        output.accept(ModBlocks.STRIPPED_OAK_POST);
                        output.accept(ModBlocks.STRIPPED_SPRUCE_POST);
                        output.accept(ModBlocks.STRIPPED_BIRCH_POST);
                        output.accept(ModBlocks.STRIPPED_JUNGLE_POST);
                        output.accept(ModBlocks.STRIPPED_ACACIA_POST);
                        output.accept(ModBlocks.STRIPPED_DARK_OAK_POST);
                        output.accept(ModBlocks.STRIPPED_MANGROVE_POST);
                        output.accept(ModBlocks.STRIPPED_CHERRY_POST);

                    }).build());

    public static void register(IEventBus eventBus) {CREATIVE_MODE_TAB.register(eventBus);}
}
