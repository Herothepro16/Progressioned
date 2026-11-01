package net.Herothepro.progressioned.integration.jei;

import com.simibubi.create.AllBlocks;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Herothepro.progressioned.integration.jei.category.KnappingDeployerCategory;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class CreateJEICompat {

    private CreateJEICompat() {}

    public static void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new KnappingDeployerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    public static void registerRecipes(IRecipeRegistration registration, List<KnappingRecipe> knappingRecipes) {
        registration.addRecipes(KnappingDeployerCategory.RECIPE_TYPE, knappingRecipes);
    }

    public static void registerCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AllBlocks.DEPLOYER.get()), KnappingDeployerCategory.RECIPE_TYPE);
    }
}