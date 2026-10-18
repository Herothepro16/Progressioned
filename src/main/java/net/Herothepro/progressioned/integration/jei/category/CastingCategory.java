package net.Herothepro.progressioned.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.recipe.CastingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CastingCategory implements IRecipeCategory<CastingRecipe> {
    public static final RecipeType<CastingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "casting", CastingRecipe.class);

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/casting_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CastingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 128, 48);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PORCELAIN_CASTING_TABLE.get()));
    }

    @Override
    public RecipeType<CastingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.progressioned.porcelain_casting_table");
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
    public void setRecipe(IRecipeLayoutBuilder builder, CastingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 20)
                .addIngredients(recipe.getMoldInput());

        var fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 37, 9);
        for (var fs : recipe.getFluidInput().getFluids()) {
            fluidSlot.addFluidStack(fs.getFluid(), recipe.getFluidInput().amount());
        }
        fluidSlot.setFluidRenderer(recipe.getFluidAmount(), true, 7, 12);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 20)
                .addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(CastingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 4);

        guiGraphics.blit(GUI_TEXTURE, 32, 36, 128, 0, 16, 16);

        float seconds = recipe.getCoolingTime() / 20.0f;
        String timeText = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(timeText), 62, 40, 0xFF404040, false);
    }
}