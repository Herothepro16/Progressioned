package net.Herothepro.progressioned.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.integration.jei.category.*;
import net.Herothepro.progressioned.recipe.*;
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
        registration.addRecipeCategories(new MeltingCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CastingCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CastingBasinCategory(
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

        List<MeltingRecipe> meltingRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.MELTING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MeltingCategory.RECIPE_TYPE, meltingRecipes);

        List<CastingRecipe> castingRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CASTING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CastingCategory.RECIPE_TYPE, castingRecipes);

        List<CastingBasinRecipe> castingBasinRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CASTING_BASIN_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CastingBasinCategory.RECIPE_TYPE, castingBasinRecipes);
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

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.PORCELAIN_MELTER.get().asItem()),
                MeltingCategory.RECIPE_TYPE);

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.PORCELAIN_CASTING_TABLE.get().asItem()),
                CastingCategory.RECIPE_TYPE);

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.PORCELAIN_CASTING_BASIN.get().asItem()),
                CastingBasinCategory.RECIPE_TYPE);
    }
}