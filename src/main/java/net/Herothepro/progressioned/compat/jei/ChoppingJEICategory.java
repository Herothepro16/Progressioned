package net.Herothepro.progressioned.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Herothepro.progressioned.compat.ChoppingCategory;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ChoppingJEICategory implements IRecipeCategory<ChoppingRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public ChoppingJEICategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(120, 40);
        this.icon = guiHelper.createDrawableItemStack(ChoppingCategory.getIcon());
    }

    @Override
    public RecipeType<ChoppingRecipe> getRecipeType() {
        return JEIPlugin.CHOPPING_TYPE;
    }

    @Override
    public Component getTitle() {
        return ChoppingCategory.getTitle();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChoppingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 11)
                .addIngredients(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 11)
                .addItemStack(recipe.getResult());
    }
}