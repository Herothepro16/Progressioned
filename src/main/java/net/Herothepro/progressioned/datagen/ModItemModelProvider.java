package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.fluid.FluidHolder;
import net.Herothepro.progressioned.fluid.ModFluids;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.FLINT_SHARD.get());
        basicItem(ModItems.FLINT_CHIP.get());
        basicItem(ModItems.PLANT_FIBER.get());
        basicItem(ModItems.PLANT_STRING.get());
        basicItem(ModItems.STRAW.get());
        basicItem(ModItems.STRAW_CORD.get());
        basicItem(ModItems.FIBER_MESH.get());
        basicItem(ModItems.SHARP_BONE.get());
        basicItem(ModItems.PORCELAIN_CLAY_BALL.get());
        basicItem(ModItems.PORCELAIN_CLAY_BRICK.get());

        basicItem(ModItems.UNFIRED_CLAY_BUCKET.get());
        basicItem(ModItems.CLAY_BUCKET.get());
        basicItem(ModItems.CLAY_WATER_BUCKET.get());
        basicItem(ModItems.CLAY_LAVA_BUCKET.get());

        handheldItem(ModItems.FIRE_STICK.get());

        handheldItem(ModItems.COPPER_KNIFE.get());
        handheldItem(ModItems.IRON_KNIFE.get());
        handheldItem(ModItems.DIAMOND_KNIFE.get());

        handheldItem(ModItems.COPPER_SAW.get());
        handheldItem(ModItems.IRON_SAW.get());
        handheldItem(ModItems.DIAMOND_SAW.get());

        basicItem(ModItems.COPPER_PLATE.get());
        basicItem(ModItems.IRON_PLATE.get());
        basicItem(ModItems.GOLD_PLATE.get());

        handheldItem(ModItems.COPPER_HAMMER.get());
        handheldItem(ModItems.IRON_HAMMER.get());
        handheldItem(ModItems.DIAMOND_HAMMER.get());

        handheldItem(ModItems.FLINT_PICK.get());
        handheldItem(ModItems.FLINT_AXE.get());
        handheldItem(ModItems.FLINT_KNIFE.get());
        handheldItem(ModItems.FLINT_SHOVEL.get());
        handheldItem(ModItems.FLINT_HOE.get());
        handheldItem(ModItems.FLINT_SPEAR.get());

        handheldItem(ModItems.BONE_SWORD.get());
        handheldItem(ModItems.BONE_SPEAR.get());
        handheldItem(ModItems.AMETHYST_BONE_SWORD.get());
        handheldItem(ModItems.AMETHYST_BONE_SPEAR.get());
        handheldItem(ModItems.GOLDEN_BONE_SWORD.get());
        handheldItem(ModItems.GOLDEN_BONE_SPEAR.get());

        handheldItem(ModItems.COPPER_SWORD.get());
        handheldItem(ModItems.COPPER_PICKAXE.get());
        handheldItem(ModItems.COPPER_AXE.get());
        handheldItem(ModItems.COPPER_SHOVEL.get());
        handheldItem(ModItems.COPPER_HOE.get());
        handheldItem(ModItems.COPPER_SPEAR.get());

        basicItem(ModItems.CAMPFIRE_BASE.get());

        wallItem(ModBlocks.PORCELAIN_BRICK_WALL, ModBlocks.PORCELAIN_BRICKS);

    }
    private ItemModelBuilder itemModelBuilder(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID,"item/"+ item.getId().getPath()));
    }
    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}