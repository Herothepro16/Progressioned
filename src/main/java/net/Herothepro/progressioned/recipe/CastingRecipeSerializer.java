package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class CastingRecipeSerializer implements RecipeSerializer<CastingRecipe> {
    public static final CastingRecipeSerializer INSTANCE = new CastingRecipeSerializer();

    private static final MapCodec<CastingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("mold").forGetter(CastingRecipe::getMoldInput),
            SizedFluidIngredient.FLAT_CODEC.fieldOf("fluid").forGetter(CastingRecipe::getFluidInput),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(null)),
            Codec.INT.optionalFieldOf("cooling_time", 100).forGetter(CastingRecipe::getCoolingTime),
            Codec.BOOL.optionalFieldOf("consume_cast", false).forGetter(CastingRecipe::isConsumeCast)
    ).apply(instance, CastingRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, CastingRecipe> STREAM_CODEC = StreamCodec.of(
            (buf, recipe) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getMoldInput());
                SizedFluidIngredient.STREAM_CODEC.encode(buf, recipe.getFluidInput());
                ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));
                buf.writeInt(recipe.getCoolingTime());
                buf.writeBoolean(recipe.isConsumeCast());
            },
            buf -> {
                Ingredient mold = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                SizedFluidIngredient fluid = SizedFluidIngredient.STREAM_CODEC.decode(buf);
                ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                int cooling = buf.readInt();
                boolean consume = buf.readBoolean();
                return new CastingRecipe(mold, fluid, result, cooling, consume);
            }
    );

    @Override
    public MapCodec<CastingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CastingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}