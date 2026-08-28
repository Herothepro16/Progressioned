package net.Herothepro.progressioned.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.integration.jei.category.ChoppingCategory;
import net.Herothepro.progressioned.integration.jei.category.KilnCategory;
import net.Herothepro.progressioned.integration.jei.category.WorkstoneCategory;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.KilnRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.recipe.WorkstoneRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class ProgressionedJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChoppingCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new KilnCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WorkstoneCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ChoppingRecipe> choppingRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CHOPPING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(ChoppingCategory.RECIPE_TYPE, choppingRecipes);

        List<KilnRecipe> kilnRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.KILN_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(KilnCategory.RECIPE_TYPE, kilnRecipes);

        List<WorkstoneRecipe> workstoneRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.WORKSTONE_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(WorkstoneCategory.RECIPE_TYPE, workstoneRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.CHOPPING_BLOCK.get().asItem()),
                ChoppingCategory.RECIPE_TYPE);

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.PORCELAIN_KILN.get().asItem()),
                KilnCategory.RECIPE_TYPE);

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.WORKSTONE.get().asItem()),
                WorkstoneCategory.RECIPE_TYPE);
    }
}