package net.Herothepro.progressioned.integration.emi;

import com.simibubi.create.AllBlocks;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public final class CreateEMICompat {

    public static final EmiRecipeCategory KNAPPING_DEPLOYER_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "knapping_deployer"),
            EmiStack.of(AllBlocks.DEPLOYER.get()));

    private CreateEMICompat() {}

    public static void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        registry.addCategory(KNAPPING_DEPLOYER_CATEGORY);
        registry.addWorkstation(KNAPPING_DEPLOYER_CATEGORY, EmiStack.of(AllBlocks.DEPLOYER.get()));

        for (RecipeHolder<KnappingRecipe> holder : manager.getAllRecipesFor(ModRecipes.KNAPPING_TYPE.get())) {
            registry.addRecipe(new KnappingDeployerEMIRecipe(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "/knapping_deployer/" + holder.id().getPath()),   // leading '/' = synthetic recipe id (EMI dev mode flags ids without it)
                    holder.value()));
        }
    }
}