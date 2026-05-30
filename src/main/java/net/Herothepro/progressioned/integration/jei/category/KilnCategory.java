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
import net.Herothepro.progressioned.recipe.KilnRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class KilnCategory implements IRecipeCategory<KilnRecipe> {
    public static final RecipeType<KilnRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "porcelain_kiln", KilnRecipe.class);

    private static final ResourceLocation FURNACE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/furnace.png");

    private final IDrawable background;
    private final IDrawable icon;

    public KilnCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(FURNACE_TEXTURE, 55, 16, 82, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PORCELAIN_KILN.get()));
    }

    @Override
    public RecipeType<KilnRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public Component getTitle() { return Component.translatable("block.progressioned.porcelain_kiln"); }

    @Override
    public int getWidth() { return 82; }

    @Override
    public int getHeight() { return 54; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KilnRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(recipe.getIngredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 19)
                .addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(KilnRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);

        long realTimeMs = System.currentTimeMillis();

        long flameCycle = 1200;
        int flameHeight = 14 - (int) ((realTimeMs % flameCycle) * 14 / flameCycle);
        if (flameHeight > 0) {
            guiGraphics.blit(FURNACE_TEXTURE,
                    1, 20 + (14 - flameHeight),
                    176, 14 - flameHeight,
                    14, flameHeight,
                    256, 256);
        }

        long arrowCycle = 2000;
        int arrowWidth = (int) ((realTimeMs % arrowCycle) * 24 / arrowCycle);
        if (arrowWidth > 0) {
            guiGraphics.blit(FURNACE_TEXTURE,
                    24, 18,
                    176, 14,
                    arrowWidth, 17,
                    256, 256);
        }

        float seconds = recipe.getCookTime() / 20.0f;
        String timeString = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);

        var font = Minecraft.getInstance().font;
        int textX = 61 + 9 - (font.width(timeString) / 2);
        guiGraphics.drawString(font, timeString, textX, 44, 0xFF404040, false);
    }
}