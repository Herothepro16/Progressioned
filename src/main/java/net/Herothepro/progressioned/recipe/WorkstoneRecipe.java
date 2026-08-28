package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record WorkstoneRecipe(Ingredient ingredient, int inputCount, ItemStack output, int hitsRequired) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.item()) && input.item().getCount() >= this.inputCount;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.WORKSTONE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.WORKSTONE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<WorkstoneRecipe> {

        public static final MapCodec<WorkstoneRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(WorkstoneRecipe::ingredient),
                Codec.INT.optionalFieldOf("input_count", 1).forGetter(WorkstoneRecipe::inputCount),
                ItemStack.CODEC.fieldOf("result").forGetter(WorkstoneRecipe::output),
                Codec.INT.fieldOf("hits").forGetter(WorkstoneRecipe::hitsRequired)
        ).apply(inst, WorkstoneRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WorkstoneRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public MapCodec<WorkstoneRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WorkstoneRecipe> streamCodec() { return STREAM_CODEC; }

        private static WorkstoneRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            return new WorkstoneRecipe(Ingredient.CONTENTS_STREAM_CODEC.decode(buf), buf.readInt(), ItemStack.STREAM_CODEC.decode(buf), buf.readInt());
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, WorkstoneRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient());
            buf.writeInt(recipe.inputCount());
            ItemStack.STREAM_CODEC.encode(buf, recipe.output());
            buf.writeInt(recipe.hitsRequired());
        }
    }
}