package net.Herothepro.progressioned.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CastingRecipeInput(ItemStack mold) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? mold : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }
}