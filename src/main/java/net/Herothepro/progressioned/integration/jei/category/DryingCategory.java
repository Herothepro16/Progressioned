package net.Herothepro.progressioned.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.recipe.DryingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DryingCategory implements IRecipeCategory<DryingRecipe> {

    public static final RecipeType<DryingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "drying", DryingRecipe.class);

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/drying_gui.png");

    private static final int TIME_X = 40;
    private static final int TIME_Y = 30;

    private final IDrawable background;
    private final IDrawable icon;

    public DryingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 96, 40);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.OAK_DRYING_RACK.get()));
    }

    @Override
    public RecipeType<DryingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.progressioned.drying");
    }

    @Override
    public int getWidth() { return 96; }

    @Override
    public int getHeight() { return 40; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void draw(DryingRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);

        Component timeText = Component.literal(formatTime(recipe.dryingTime()));
        guiGraphics.drawString(Minecraft.getInstance().font, timeText, TIME_X, TIME_Y, 0xFF404040, false);
    }

    private static String formatTime(int ticks) {
        double seconds = ticks / 20.0;
        if (seconds == Math.floor(seconds)) {
            return (int) seconds + "s";
        }
        return String.format("%.1fs", seconds);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DryingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 13)
                .addIngredients(recipe.ingredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 13)
                .addItemStack(recipe.result());
    }
}