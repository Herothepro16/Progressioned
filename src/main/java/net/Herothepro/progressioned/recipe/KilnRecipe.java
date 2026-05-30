package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class KilnRecipe implements Recipe<SingleRecipeInput> {

    public static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "porcelain_kiln");

    private final Ingredient input;
    private final ItemStack output;
    private final int cookTime;

    public KilnRecipe(Ingredient input, ItemStack output, int cookTime) {
        this.input = input;
        this.output = output;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(SingleRecipeInput recipeInput, Level level) {
        return input.test(recipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput recipeInput, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.KILN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.KILN_TYPE.get();
    }

    public Ingredient getIngredient() { return input; }
    public int getCookTime() { return cookTime; }

    public static class Serializer implements RecipeSerializer<KilnRecipe> {

        public static final MapCodec<KilnRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.input),
                ItemStack.CODEC.fieldOf("result").forGetter(r -> r.output),
                net.minecraft.util.ExtraCodecs.intRange(1, Integer.MAX_VALUE)
                        .optionalFieldOf("cooktime", 300) // defaults to 300 (15s)
                        .forGetter(r -> r.cookTime)
        ).apply(inst, KilnRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
                        ItemStack.STREAM_CODEC,            r -> r.output,
                        net.minecraft.network.codec.ByteBufCodecs.INT, r -> r.cookTime,
                        KilnRecipe::new
                );

        @Override
        public MapCodec<KilnRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> streamCodec() { return STREAM_CODEC; }
    }
}