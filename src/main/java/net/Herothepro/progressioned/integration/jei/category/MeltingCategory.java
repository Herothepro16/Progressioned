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
import net.Herothepro.progressioned.recipe.MeltingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class MeltingCategory implements IRecipeCategory<MeltingRecipe> {
    public static final RecipeType<MeltingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "porcelain_melter", MeltingRecipe.class);

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/melting_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MeltingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 132, 44);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.PORCELAIN_MELTER.get()));
    }

    @Override
    public RecipeType<MeltingRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public Component getTitle() { return Component.translatable("block.progressioned.porcelain_melter"); }

    @Override
    public int getWidth() { return 132; }

    @Override
    public int getHeight() { return 44; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MeltingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 25)
                .addIngredients(Ingredient.of(Items.COAL, Items.CHARCOAL, Items.LAVA_BUCKET, Items.BLAZE_ROD));

        builder.addSlot(RecipeIngredientRole.INPUT, 32, 7)
                .addIngredients(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 2)
                .addFluidStack(recipe.getOutput().getFluid(), recipe.getOutput().getAmount())
                .setFluidRenderer(1296, true, 40, 36);
    }

    @Override
    public void draw(MeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);

        long realTimeMs = System.currentTimeMillis();
        long flameCycle = 16000;
        int flameHeight = 14 - (int) ((realTimeMs % flameCycle) * 14 / flameCycle);
        if (flameHeight > 0) {
            guiGraphics.blit(GUI_TEXTURE,
                    5, 7 + (14 - flameHeight),
                    176, 135 + (14 - flameHeight),
                    14, flameHeight);
        }

        guiGraphics.blit(GUI_TEXTURE, 88, 2, 132, 2, 7, 33);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, MeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 57 && mouseX <= 81 && mouseY >= 7 && mouseY <= 23) {
            float seconds = recipe.getCookingTime() / 20.0f;
            String timeString = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);
            tooltip.add(Component.literal(timeString));
        }
    }
}