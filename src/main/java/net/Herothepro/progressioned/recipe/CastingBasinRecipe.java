package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public record CastingBasinRecipe(FluidStack input, ItemStack output, int coolingTime) implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
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
        return ModRecipes.CASTING_BASIN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CASTING_BASIN_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CastingBasinRecipe> {
        private static final MapCodec<CastingBasinRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                FluidStack.CODEC.fieldOf("input").forGetter(CastingBasinRecipe::input),
                ItemStack.CODEC.fieldOf("result").forGetter(CastingBasinRecipe::output),
                Codec.INT.fieldOf("time").forGetter(CastingBasinRecipe::coolingTime)
        ).apply(instance, CastingBasinRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CastingBasinRecipe> STREAM_CODEC = StreamCodec.composite(
                FluidStack.STREAM_CODEC, CastingBasinRecipe::input,
                ItemStack.STREAM_CODEC, CastingBasinRecipe::output,
                ByteBufCodecs.INT, CastingBasinRecipe::coolingTime,
                CastingBasinRecipe::new
        );

        @Override
        public MapCodec<CastingBasinRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CastingBasinRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}