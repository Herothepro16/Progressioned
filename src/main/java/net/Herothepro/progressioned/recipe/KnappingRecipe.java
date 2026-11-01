package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

public record KnappingRecipe(Ingredient ingredient, ItemStack result, float chance, TagKey<Block> target,
                             ResourceLocation successSound, ResourceLocation failSound) implements Recipe<KnappingRecipeInput> {

    public static final ResourceLocation DEFAULT_SOUND = ResourceLocation.withDefaultNamespace("block.gravel.break");

    @Override
    public boolean matches(KnappingRecipeInput input, Level level) {
        return ingredient.test(input.stack()) && input.state().is(target);
    }

    @Override
    public ItemStack assemble(KnappingRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.KNAPPING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.KNAPPING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<KnappingRecipe> {
        private static final MapCodec<KnappingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(KnappingRecipe::ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(KnappingRecipe::result),
                Codec.FLOAT.optionalFieldOf("chance", 0.2f).forGetter(KnappingRecipe::chance),
                TagKey.codec(Registries.BLOCK).optionalFieldOf("target", Tags.Blocks.STONES).forGetter(KnappingRecipe::target),
                ResourceLocation.CODEC.optionalFieldOf("success_sound", DEFAULT_SOUND).forGetter(KnappingRecipe::successSound),
                ResourceLocation.CODEC.optionalFieldOf("fail_sound", DEFAULT_SOUND).forGetter(KnappingRecipe::failSound)
        ).apply(instance, (ingredient, result, chance, target, success, fail) ->
                new KnappingRecipe(ingredient, result, chance, target, success, fail)));

        private static final StreamCodec<RegistryFriendlyByteBuf, KnappingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, KnappingRecipe::ingredient,
                ItemStack.STREAM_CODEC, KnappingRecipe::result,
                ByteBufCodecs.FLOAT, KnappingRecipe::chance,
                ResourceLocation.STREAM_CODEC.map(
                        loc -> TagKey.create(Registries.BLOCK, loc),
                        TagKey::location
                ), KnappingRecipe::target,
                ResourceLocation.STREAM_CODEC, KnappingRecipe::successSound,
                ResourceLocation.STREAM_CODEC, KnappingRecipe::failSound,
                (ingredient, result, chance, target, success, fail) ->
                        new KnappingRecipe(ingredient, result, chance, target, success, fail)
        );

        @Override
        public MapCodec<KnappingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KnappingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}