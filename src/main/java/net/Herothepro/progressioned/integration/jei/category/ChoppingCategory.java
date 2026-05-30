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
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class ChoppingCategory implements IRecipeCategory<ChoppingRecipe> {

    public static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping");

    public static final RecipeType<ChoppingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "chopping", ChoppingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final List<ItemStack> axes;

    public ChoppingCategory(IGuiHelper helper) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                Progressioned.MOD_ID, "textures/gui/chopping_gui.png");

        this.background = helper.createDrawable(texture, 0, 0, 94, 50);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.CHOPPING_BLOCK.get()));

        this.axes = BuiltInRegistries.ITEM.getTag(ItemTags.AXES)
                .map(named -> named.stream().map(ItemStack::new).toList())
                .orElse(List.of());
    }

    @Override
    public RecipeType<ChoppingRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public Component getTitle() { return Component.translatable("block.progressioned.chopping_block"); }

    @Override
    public @Nullable IDrawable getIcon() { return icon; }

    @Override
    public int getWidth() { return 94; }

    @Override
    public int getHeight() { return 50; }

    @Override
    public void draw(ChoppingRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {

        background.draw(guiGraphics, 0, 0);

        guiGraphics.pose().pushPose();

        guiGraphics.pose().translate(0, 8, 0);

        guiGraphics.pose().scale(1.75F, 1.75F, 1.0F);

        icon.draw(guiGraphics, 0, 0);

        guiGraphics.pose().popPose();

        Component hitsText = Component.literal(recipe.getHits() + " Hits");
        Minecraft minecraft = Minecraft.getInstance();
        int textWidth = minecraft.font.width(hitsText);
        guiGraphics.drawString(minecraft.font, hitsText, 40 - (textWidth / 2), 2, 0xFF404040, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChoppingRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 11)
                .addIngredients(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.CATALYST, 31, 32)
                .addItemStacks(this.axes);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 12)
                .addItemStack(recipe.getResult());
    }
}