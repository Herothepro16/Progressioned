package net.Herothepro.progressioned.recipe;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Progressioned.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Progressioned.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ChoppingRecipe>> CHOPPING_TYPE =
            TYPES.register("chopping", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChoppingRecipe>> CHOPPING_SERIALIZER =
            SERIALIZERS.register("chopping", ChoppingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DamagingShapelessRecipe>> DAMAGING_SHAPELESS_SERIALIZER =
            SERIALIZERS.register("damaging_shapeless", DamagingShapelessRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<KilnRecipe>> KILN_TYPE =
            TYPES.register("porcelain_kiln", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_kiln")));

    public static final DeferredHolder<RecipeSerializer<?>, KilnRecipe.Serializer> KILN_SERIALIZER =
            SERIALIZERS.register("porcelain_kiln", KilnRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}