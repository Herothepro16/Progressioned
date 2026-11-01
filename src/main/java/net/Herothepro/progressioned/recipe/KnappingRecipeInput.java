package net.Herothepro.progressioned.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record KnappingRecipeInput(ItemStack stack, BlockState state) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }
}