package net.Herothepro.progressioned.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("Progressioned");

    @Inject(method = "apply", at = @At("HEAD"))
    private void replaceVanillaRecipes(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        String woodMat = "#minecraft:planks";
        String stoneMat = "minecraft:stone";
        String stickTag = "#c:rods/wooden";
        String plantString = "progressioned:plant_string";
        String strawCord = "progressioned:straw_cord";
        String flintItem = "minecraft:flint_and_steel";
        String netherrack = "minecraft:netherrack";
        String campfireBase = "progressioned:campfire_base";
        String ironPlate = "progressioned:iron_plate";
        String goldPlate = "progressioned:gold_plate";

        for (String material : new String[]{"iron", "golden"}) {
            for (String tool : new String[]{"sword", "pickaxe", "axe", "shovel", "hoe"}) {
                map.remove(ResourceLocation.withDefaultNamespace(material + "_" + tool));
            }
        }

        for (String metal : new String[]{"iron", "gold", "copper"}) {
            for (String method : new String[]{"smelting", "blasting"}) {
                ResourceLocation id = ResourceLocation.withDefaultNamespace(metal + "_ingot_from_" + method + "_raw_" + metal);
                if (map.remove(id) == null) {
                    LOGGER.warn("[Progressioned] Recipe not found to remove: {}", id);
                }
            }
        }

        //campfire change
        setupShapedRecipe(map, "minecraft:campfire", "misc",
                new String[]{" N ", "NCN", " F "},
                Map.of('N', netherrack, 'F', flintItem, 'C', campfireBase),
                "minecraft:campfire");

        //wooden replacements
        setupShapedRecipe(map, "minecraft:wooden_sword", "misc",
                new String[]{" T ", " T ", "PS "},
                Map.of('T', woodMat, 'P', plantString, 'S', stickTag),
                "minecraft:wooden_sword");
        setupShapedRecipe(map, "minecraft:wooden_pickaxe", "misc",
                new String[]{"TTT", "PS ", " S "},
                Map.of('T', woodMat, 'P', plantString, 'S', stickTag),
                "minecraft:wooden_pickaxe");
        setupShapedRecipe(map, "minecraft:wooden_axe", "misc",
                new String[]{"TT ", "TSP", " S "},
                Map.of('T', woodMat, 'P', plantString, 'S', stickTag),
                "minecraft:wooden_axe");
        setupShapedRecipe(map, "minecraft:wooden_shovel", "misc",
                new String[]{" T ", "PS ", " S "},
                Map.of('T', woodMat, 'P', plantString, 'S', stickTag),
                "minecraft:wooden_shovel");
        setupShapedRecipe(map, "minecraft:wooden_hoe", "misc",
                new String[]{"TT ", " SP", " S "},
                Map.of('T', woodMat, 'P', plantString, 'S', stickTag),
                "minecraft:wooden_hoe");

        //stone replacements
        setupShapedRecipe(map, "minecraft:stone_sword", "misc",
                new String[]{" T ", " T ", "PS "},
                Map.of('T', stoneMat, 'P', strawCord, 'S', stickTag),
                "minecraft:stone_sword");
        setupShapedRecipe(map, "minecraft:stone_pickaxe", "misc",
                new String[]{"TTT", "PS ", " S "},
                Map.of('T', stoneMat, 'P', strawCord, 'S', stickTag),
                "minecraft:stone_pickaxe");
        setupShapedRecipe(map, "minecraft:stone_axe", "misc",
                new String[]{"TT ", "TSP", " S "},
                Map.of('T', stoneMat, 'P', strawCord, 'S', stickTag),
                "minecraft:stone_axe");
        setupShapedRecipe(map, "minecraft:stone_shovel", "misc",
                new String[]{" T ", "PS ", " S "},
                Map.of('T', stoneMat, 'P', strawCord, 'S', stickTag),
                "minecraft:stone_shovel");
        setupShapedRecipe(map, "minecraft:stone_hoe", "misc",
                new String[]{"TT ", " SP", " S "},
                Map.of('T', stoneMat, 'P', strawCord, 'S', stickTag),
                "minecraft:stone_hoe");

        //iron replacements
        setupShapedRecipe(map, "minecraft:iron_helmet", "misc",
                new String[]{"PPP", "P P", "   "},
                Map.of('P', ironPlate),
                "minecraft:iron_helmet");
        setupShapedRecipe(map, "minecraft:iron_chestplate", "misc",
                new String[]{"P P", "PPP", "PPP"},
                Map.of('P', ironPlate),
                "minecraft:iron_chestplate");
        setupShapedRecipe(map, "minecraft:iron_leggings", "misc",
                new String[]{"PPP", "P P", "P P"},
                Map.of('P', ironPlate),
                "minecraft:iron_leggings");
        setupShapedRecipe(map, "minecraft:iron_boots", "misc",
                new String[]{"P P", "P P", "   "},
                Map.of( 'P', ironPlate),
                "minecraft:iron_boots");

        //gold replacements
        setupShapedRecipe(map, "minecraft:golden_helmet", "misc",
                new String[]{"PPP", "P P", "   "},
                Map.of('P', goldPlate),
                "minecraft:golden_helmet");
        setupShapedRecipe(map, "minecraft:golden_chestplate", "misc",
                new String[]{"P P", "PPP", "PPP"},
                Map.of('P', goldPlate),
                "minecraft:golden_chestplate");
        setupShapedRecipe(map, "minecraft:golden_leggings", "misc",
                new String[]{"PPP", "P P", "P P"},
                Map.of('P', goldPlate),
                "minecraft:golden_leggings");
        setupShapedRecipe(map, "minecraft:golden_boots", "misc",
                new String[]{"P P", "P P", "   "},
                Map.of( 'P', goldPlate),
                "minecraft:golden_boots");
    }

    @Unique
    private void setupShapedRecipe(Map<ResourceLocation, JsonElement> map, String recipeId, String group, String[] pattern, Map<Character, String> ingredients, String resultItem) {
        try {
            ResourceLocation target = ResourceLocation.parse(recipeId);
            map.remove(target);

            JsonObject recipeJson = new JsonObject();
            recipeJson.addProperty("type", "minecraft:crafting_shaped");
            recipeJson.addProperty("group", group);

            JsonArray patternArray = new JsonArray();
            for (String row : pattern) {
                patternArray.add(row);
            }
            recipeJson.add("pattern", patternArray);

            JsonObject keyMap = new JsonObject();
            ingredients.forEach((character, itemOrTag) -> {
                JsonObject individualKey = new JsonObject();
                if (itemOrTag.startsWith("#")) {
                    individualKey.addProperty("tag", itemOrTag.substring(1));
                } else {
                    individualKey.addProperty("item", itemOrTag);
                }
                keyMap.add(String.valueOf(character), individualKey);
            });
            recipeJson.add("key", keyMap);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("id", resultItem);
            resultObj.addProperty("count", 1);
            recipeJson.add("result", resultObj);

            map.put(target, recipeJson);
        } catch (Exception e) {
            LOGGER.error("[Progressioned] Fatal error injecting recipe for: {}", recipeId, e);
        }
    }
}