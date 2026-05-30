package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
        basicItem(ModItems.FIBER_MESH.get());
        basicItem(ModItems.SHARP_BONE.get());
        basicItem(ModItems.PORCELAIN_CLAY_BALL.get());
        basicItem(ModItems.PORCELAIN_CLAY_BRICK.get());

        basicItem(ModItems.UNFIRED_CLAY_BUCKET.get());
        basicItem(ModItems.CLAY_BUCKET.get());
        basicItem(ModItems.CLAY_WATER_BUCKET.get());
        basicItem(ModItems.CLAY_LAVA_BUCKET.get());

        handheldItem(ModItems.FIRE_STICK.get());

        handheldItem(ModItems.FLINT_SAW.get());
        handheldItem(ModItems.IRON_SAW.get());
        handheldItem(ModItems.DIAMOND_SAW.get());

        handheldItem(ModItems.FLINT_PICK.get());
        handheldItem(ModItems.FLINT_AXE.get());
        handheldItem(ModItems.FLINT_KNIFE.get());
        handheldItem(ModItems.FLINT_SHOVEL.get());
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

    }
    private ItemModelBuilder itemModelBuilder(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID,"item/"+ item.getId().getPath()));
    }
}