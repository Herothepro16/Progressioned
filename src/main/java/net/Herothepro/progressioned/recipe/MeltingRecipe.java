package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public class MeltingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient input;
    private final FluidStack output;
    private final int cookingTime;

    public MeltingRecipe(Ingredient input, FluidStack output, int cookingTime) {
        this.input = input;
        this.output = output;
        this.cookingTime = cookingTime;
    }

    public Ingredient getInput() {
        return input;
    }

    public FluidStack getOutput() {
        return output;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        if (level.isClientSide()) return false;
        return this.input.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY; // Returns fluid, so item output is empty
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MELTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MELTING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MeltingRecipe> {
        public static final MapCodec<MeltingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(MeltingRecipe::getInput),
                        FluidStack.CODEC.fieldOf("result").forGetter(MeltingRecipe::getOutput),
                        Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(MeltingRecipe::getCookingTime)
                ).apply(instance, MeltingRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, MeltingRecipe::getInput,
                FluidStack.STREAM_CODEC, MeltingRecipe::getOutput,
                ByteBufCodecs.INT, MeltingRecipe::getCookingTime,
                MeltingRecipe::new
        );

        @Override
        public MapCodec<MeltingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
