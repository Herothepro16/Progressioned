package net.Herothepro.progressioned.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class CastingRecipe implements Recipe<CastingRecipeInput> {
    private final Ingredient moldInput;
    private final SizedFluidIngredient fluidInput;
    private final ItemStack output;
    private final int coolingTime;
    private final boolean consumeCast;

    public CastingRecipe(Ingredient moldInput, SizedFluidIngredient fluidInput, ItemStack output, int coolingTime, boolean consumeCast) {
        this.moldInput = moldInput;
        this.fluidInput = fluidInput;
        this.output = output;
        this.coolingTime = coolingTime;
        this.consumeCast = consumeCast;
    }

    @Override
    public boolean matches(CastingRecipeInput input, Level level) {
        return moldInput.test(input.mold());
    }

    public boolean matchesFluidType(FluidStack fluid) {
        return fluidInput.ingredient().test(fluid);
    }

    public int getFluidAmount() {
        return fluidInput.amount();
    }

    public int getCoolingTime() {
        return coolingTime;
    }

    public boolean isConsumeCast() {
        return consumeCast;
    }

    @Override
    public ItemStack assemble(CastingRecipeInput input, HolderLookup.Provider registries) {
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
        return ModRecipes.CASTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CASTING_TYPE.get();
    }

    public Ingredient getMoldInput() {
        return moldInput;
    }

    public SizedFluidIngredient getFluidInput() {
        return fluidInput;
    }
}