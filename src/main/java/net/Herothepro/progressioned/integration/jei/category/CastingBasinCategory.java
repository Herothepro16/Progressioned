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
import net.Herothepro.progressioned.recipe.CastingBasinRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CastingBasinCategory implements IRecipeCategory<CastingBasinRecipe> {
    public static final RecipeType<CastingBasinRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "casting_basin", CastingBasinRecipe.class);

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/casting_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CastingBasinCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 128, 48);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PORCELAIN_CASTING_BASIN.get()));
    }

    @Override
    public RecipeType<CastingBasinRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.progressioned.porcelain_casting_basin");
    }

    @Override
    public int getWidth() {
        return 128;
    }

    @Override
    public int getHeight() {
        return 52;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CastingBasinRecipe recipe, IFocusGroup focuses) {
        var fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 37, 9);
        fluidSlot.addFluidStack(recipe.input().getFluid(), recipe.input().getAmount());
        fluidSlot.setFluidRenderer(recipe.input().getAmount(), true, 7, 27);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 20)
                .addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(CastingBasinRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 4);

        guiGraphics.blit(GUI_TEXTURE, 32, 36, 144, 0, 16, 16);

        float seconds = recipe.coolingTime() / 20.0f;
        String timeText = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(timeText), 62, 40, 0xFF404040, false);
    }
}