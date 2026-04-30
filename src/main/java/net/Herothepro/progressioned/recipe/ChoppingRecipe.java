package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ChoppingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient input;
    private final ItemStack result;
    private final int hits;

    public ChoppingRecipe(Ingredient input, ItemStack result, int hits) {
        this.input = input;
        this.result = result;
        this.hits = hits;
    }

    public Ingredient getInput() { return input; }
    public ItemStack getResult() { return result; }
    public int getHits() { return hits; }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) { return result; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CHOPPING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CHOPPING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ChoppingRecipe> {
        public static final MapCodec<ChoppingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("input").forGetter(r -> r.input),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                        com.mojang.serialization.Codec.INT.optionalFieldOf("hits", 5).forGetter(r -> r.hits)
                ).apply(instance, ChoppingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ChoppingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
                        ItemStack.STREAM_CODEC, r -> r.result,
                        ByteBufCodecs.INT, r -> r.hits,
                        ChoppingRecipe::new);

        @Override
        public MapCodec<ChoppingRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ChoppingRecipe> streamCodec() { return STREAM_CODEC; }
    }
}
