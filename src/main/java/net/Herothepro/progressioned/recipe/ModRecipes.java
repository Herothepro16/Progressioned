package net.Herothepro.progressioned.recipe;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

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

    public static final DeferredHolder<RecipeType<?>, RecipeType<MeltingRecipe>> MELTING_TYPE =
            TYPES.register("melting", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "melting")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER =
            SERIALIZERS.register("melting", MeltingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CastingRecipe>> CASTING_TYPE = TYPES.register("casting", () -> new RecipeType<CastingRecipe>() {
        @Override
        public String toString() {
            return "casting";
        }
    });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CastingRecipe>> CASTING_SERIALIZER =
            SERIALIZERS.register("casting", () -> CastingRecipeSerializer.INSTANCE);

    public static final Supplier<RecipeType<CastingBasinRecipe>> CASTING_BASIN_TYPE =
            TYPES.register("casting_basin", () -> new RecipeType<>() {});
    public static final Supplier<RecipeSerializer<CastingBasinRecipe>> CASTING_BASIN_SERIALIZER =
            SERIALIZERS.register("casting_basin", CastingBasinRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<WorkstoneRecipe>> WORKSTONE_TYPE =
            TYPES.register("workstone_hammering", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "workstone_hammering")));

    public static final DeferredHolder<RecipeSerializer<?>, WorkstoneRecipe.Serializer> WORKSTONE_SERIALIZER =
            SERIALIZERS.register("workstone_hammering", WorkstoneRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}