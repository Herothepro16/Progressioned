package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.DamagingShapelessRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.*;

@EventBusSubscriber(modid = Progressioned.MOD_ID)
public class ModServerEvents {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        RecipeManager recipeManager = event.getServer().getRecipeManager();
        replaceLogToPlankRecipes(recipeManager);
    }

    private static void replaceLogToPlankRecipes(RecipeManager recipeManager) {
        List<RecipeHolder<?>> allRecipes = new ArrayList<>(recipeManager.getOrderedRecipes());
        List<RecipeHolder<?>> toAdd = new ArrayList<>();

        allRecipes.removeIf(holder -> {
            if (!(holder.value() instanceof ShapelessRecipe shapeless)) return false;

            ItemStack result = shapeless.getResultItem(null);
            if (!result.is(ItemTags.PLANKS)) return false;

            boolean hasLog = shapeless.getIngredients().stream()
                    .anyMatch(ing -> Arrays.stream(ing.getItems())
                            .anyMatch(stack -> stack.is(ItemTags.LOGS)));

            if (!hasLog) return false;

            // Replace with damaging version
            List<Ingredient> ingredients = shapeless.getIngredients().stream()
                    .filter(ing -> Arrays.stream(ing.getItems())
                            .anyMatch(stack -> stack.is(ItemTags.LOGS)))
                    .collect(java.util.stream.Collectors.toList());

            toAdd.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID,
                            "replaced/" + holder.id().getPath()),
                    new DamagingShapelessRecipe("", CraftingBookCategory.MISC,
                            result.copy(), ingredients,
                            Ingredient.of(ModTags.Items.SAWS), 1)));

            return true; // remove original
        });

        allRecipes.addAll(toAdd);
        recipeManager.replaceRecipes(allRecipes);
    }

}