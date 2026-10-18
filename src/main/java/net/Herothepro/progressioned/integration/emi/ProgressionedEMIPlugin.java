package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.recipe.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@EmiEntrypoint
public class ProgressionedEMIPlugin implements EmiPlugin {

    public static final EmiRecipeCategory CHOPPING_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping"),
            EmiStack.of(ModBlocks.CHOPPING_BLOCK.get()));

    public static final EmiRecipeCategory KILN_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_kiln"),
            EmiStack.of(ModBlocks.PORCELAIN_KILN.get()));

    public static final EmiRecipeCategory WORKSTONE_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "workstone"),
            EmiStack.of(ModBlocks.WORKSTONE.get()));

    public static final EmiRecipeCategory MELTING_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_melter"),
            EmiStack.of(ModBlocks.PORCELAIN_MELTER.get()));

    public static final EmiRecipeCategory CASTING_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_casting_table"),
            EmiStack.of(ModBlocks.PORCELAIN_CASTING_TABLE.get()));

    public static final EmiRecipeCategory CASTING_BASIN_CATEGORY = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_casting_basin"),
            EmiStack.of(ModBlocks.PORCELAIN_CASTING_BASIN.get()));

    @Override
    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        registry.addCategory(CHOPPING_CATEGORY);
        registry.addWorkstation(CHOPPING_CATEGORY, EmiStack.of(ModBlocks.CHOPPING_BLOCK.get()));
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
        for (RecipeHolder<WorkstoneRecipe> holder : manager.getAllRecipesFor(ModRecipes.WORKSTONE_TYPE.get())) {
            registry.addRecipe(new WorkstoneEMIRecipe(holder.id(), holder.value()));
        }

        registry.addCategory(MELTING_CATEGORY);
        registry.addWorkstation(MELTING_CATEGORY, EmiStack.of(ModBlocks.PORCELAIN_MELTER.get()));
        for (RecipeHolder<MeltingRecipe> holder : manager.getAllRecipesFor(ModRecipes.MELTING_TYPE.get())) {
            registry.addRecipe(new MeltingEMIRecipe(holder.id(), holder.value()));
        }

        registry.addCategory(CASTING_CATEGORY);
        registry.addWorkstation(CASTING_CATEGORY, EmiStack.of(ModBlocks.PORCELAIN_CASTING_TABLE.get()));
        for (RecipeHolder<CastingRecipe> holder : manager.getAllRecipesFor(ModRecipes.CASTING_TYPE.get())) {
            registry.addRecipe(new CastingEMIRecipe(holder.id(), holder.value()));
        }

        registry.addCategory(CASTING_BASIN_CATEGORY);
        registry.addWorkstation(CASTING_BASIN_CATEGORY, EmiStack.of(ModBlocks.PORCELAIN_CASTING_BASIN.get()));
        for (RecipeHolder<CastingBasinRecipe> holder : manager.getAllRecipesFor(ModRecipes.CASTING_BASIN_TYPE.get())) {
            registry.addRecipe(new CastingBasinEMIRecipe(holder.id(), holder.value()));
        }
    }
}