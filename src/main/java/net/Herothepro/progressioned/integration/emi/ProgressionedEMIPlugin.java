package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.KilnRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.recipe.WorkstoneRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@EmiEntrypoint
public class ProgressionedEMIPlugin implements EmiPlugin {

    public static final EmiRecipeCategory CHOPPING_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping"),
            EmiStack.of(ModBlocks.CHOPPING_BLOCK.get())
    );

    public static final EmiRecipeCategory KILN_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_kiln"),
            EmiStack.of(ModBlocks.PORCELAIN_KILN.get())
    );

    public static final EmiRecipeCategory WORKSTONE_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "workstone"),
            EmiStack.of(ModBlocks.WORKSTONE.get())
    );

    @Override
    public void register(EmiRegistry registry) {

        registry.addCategory(CHOPPING_CATEGORY);
        registry.addWorkstation(CHOPPING_CATEGORY, EmiStack.of(ModBlocks.CHOPPING_BLOCK.get()));

        RecipeManager manager = registry.getRecipeManager();

        for (RecipeHolder<ChoppingRecipe> holder : manager.getAllRecipesFor(ModRecipes.CHOPPING_TYPE.get())) {
            registry.addRecipe(new ChoppingEMIRecipe(holder.id(), holder.value()));
        }

        registry.addCategory(KILN_CATEGORY);
        registry.addWorkstation(KILN_CATEGORY, EmiStack.of(ModBlocks.PORCELAIN_KILN.get()));

        for (RecipeHolder<KilnRecipe> holder : manager.getAllRecipesFor(ModRecipes.KILN_TYPE.get())) {
            registry.addRecipe(new KilnEMIRecipe(holder.id(), holder.value()));
        }

        registry.addCategory(WORKSTONE_CATEGORY);
        registry.addWorkstation(WORKSTONE_CATEGORY, EmiStack.of(ModBlocks.WORKSTONE.get()));

        RecipeManager recipeManager = registry.getRecipeManager();

        for (RecipeHolder<WorkstoneRecipe> holder : recipeManager.getAllRecipesFor(ModRecipes.WORKSTONE_TYPE.get())) {
            registry.addRecipe(new WorkstoneEMIRecipe(holder.id(), holder.value()));
        }
    }
}