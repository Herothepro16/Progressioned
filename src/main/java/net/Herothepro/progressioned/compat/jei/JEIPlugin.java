package net.Herothepro.progressioned.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.compat.ChoppingCategory;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final RecipeType<ChoppingRecipe> CHOPPING_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "chopping", ChoppingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChoppingJEICategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        List<ChoppingRecipe> recipes = Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.CHOPPING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();
        registration.addRecipes(CHOPPING_TYPE, recipes);
    }
}