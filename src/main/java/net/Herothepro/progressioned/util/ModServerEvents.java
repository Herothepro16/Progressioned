package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.custom.entity.ModBlockEntities;
import net.Herothepro.progressioned.recipe.DamagingShapelessRecipe;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
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

            String logModId = "unknown";
            List<Ingredient> logIngredients = new ArrayList<>();

            for (Ingredient ing : shapeless.getIngredients()) {
                boolean isLog = Arrays.stream(ing.getItems()).anyMatch(stack -> stack.is(ItemTags.LOGS));
                if (isLog) {
                    logIngredients.add(ing);
                    for (ItemStack stack : ing.getItems()) {
                        if (stack.is(ItemTags.LOGS)) {
                            logModId = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                            break;
                        }
                    }
                }
            }

            if (logIngredients.isEmpty()) return false;

            String plankModId = BuiltInRegistries.ITEM.getKey(result.getItem()).getNamespace();

            String uniquePath = "replaced/" + logModId + "_to_" + plankModId + "_" + holder.id().getPath();
            ResourceLocation newId = ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, uniquePath);

            toAdd.add(new RecipeHolder<>(
                    newId,
                    new DamagingShapelessRecipe("", CraftingBookCategory.MISC,
                            result.copy(), logIngredients,
                            Ingredient.of(ModTags.Items.SAWS), 1)));

            return true;
        });

        allRecipes.addAll(toAdd);
        recipeManager.replaceRecipes(allRecipes);
    }

    @SubscribeEvent
    public static void modifyVanillaDurability(ModifyDefaultComponentsEvent event) {

        int ironToolDurability = 336;

        int ironArmorFactor = 20;

        event.modify(Items.IRON_PICKAXE, b -> b.set(DataComponents.MAX_DAMAGE, ironToolDurability));
        event.modify(Items.IRON_AXE, b -> b.set(DataComponents.MAX_DAMAGE, ironToolDurability));
        event.modify(Items.IRON_SHOVEL, b -> b.set(DataComponents.MAX_DAMAGE, ironToolDurability));
        event.modify(Items.IRON_HOE, b -> b.set(DataComponents.MAX_DAMAGE, ironToolDurability));
        event.modify(Items.IRON_SWORD, b -> b.set(DataComponents.MAX_DAMAGE, ironToolDurability));

        event.modify(Items.IRON_HELMET, b -> b.set(DataComponents.MAX_DAMAGE, ironArmorFactor * 11));
        event.modify(Items.IRON_CHESTPLATE, b -> b.set(DataComponents.MAX_DAMAGE, ironArmorFactor * 16));
        event.modify(Items.IRON_LEGGINGS, b -> b.set(DataComponents.MAX_DAMAGE, ironArmorFactor * 15));
        event.modify(Items.IRON_BOOTS, b -> b.set(DataComponents.MAX_DAMAGE, ironArmorFactor * 13));
    }
}