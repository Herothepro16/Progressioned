package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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
                .pattern("FFF")
                .pattern(" SP")
                .pattern(" S ")
                .define('f', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_FIBER.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
    }
}