package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICK.get())
                .pattern("CFC")
                .pattern(" SP")
                .pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE.get())
                .pattern("FC ")
                .pattern("PS ")
                .pattern("   ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL.get())
                .pattern(" F ")
                .pattern(" SP")
                .pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_KNIFE.get())
                .pattern(" C ")
                .pattern("SP ")
                .pattern("   ")
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SPEAR.get())
                .pattern("  C")
                .pattern(" SP")
                .pattern("S  ")
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIBER_MESH.get())
                .pattern("SF ")
                .pattern("FS ")
                .pattern("   ")
                .define('S',Items.STICK)
                .define('F',ModItems.PLANT_STRING.get())
                .unlockedBy("has_plant_string", has(ModItems.PLANT_STRING)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.FLINT)
                .requires(ModItems.FIBER_MESH,1)
                .requires(Items.GRAVEL, 3)
                .unlockedBy("has_fiber_mesh", has(ModItems.FIBER_MESH)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_STRING)
                .requires(ModItems.PLANT_FIBER, 3)
                .unlockedBy("has_plant_fiber", has(ModItems.PLANT_FIBER)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_FIBER)
                .requires(Items.WHEAT_SEEDS, 3)
                .unlockedBy("has_wheat_seeds", has(Items.WHEAT_SEEDS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SWORD.get())
                .pattern(" C ")
                .pattern(" C ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_PICKAXE.get())
                .pattern("CCC")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_AXE.get())
                .pattern("CC ")
                .pattern("CS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SHOVEL.get())
                .pattern(" C ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HOE.get())
                .pattern("CC ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SPEAR.get())
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SPEAR.get())
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SWORD.get())
                .pattern(" C ")
                .pattern(" B ")
                .pattern(" S ")
                .define('B', Items.BONE)
                .define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
    }
}