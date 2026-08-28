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
import net.Herothepro.progressioned.recipe.WorkstoneRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class WorkstoneCategory implements IRecipeCategory<WorkstoneRecipe> {

    public static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "workstone");

    public static final RecipeType<WorkstoneRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "workstone", WorkstoneRecipe.class);

    private static final TagKey<Item> HAMMERS_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/hammer"));

    private final IDrawable background;
    private final IDrawable icon;
    private final List<ItemStack> hammers;

    public WorkstoneCategory(IGuiHelper helper) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                Progressioned.MOD_ID, "textures/gui/chopping_gui.png");

        this.background = helper.createDrawable(texture, 0, 0, 94, 50);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.WORKSTONE.get()));

        this.hammers = BuiltInRegistries.ITEM.getTag(HAMMERS_TAG)
                .map(named -> named.stream().map(ItemStack::new).toList())
                .orElse(List.of());
    }

    @Override
    public RecipeType<WorkstoneRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.progressioned.workstone");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 94;
    }

    @Override
    public int getHeight() {
        return 50;
    }

    @Override
    public void draw(WorkstoneRecipe recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphics guiGraphics, double mouseX, double mouseY) {

        background.draw(guiGraphics, 0, 0);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 10.0F, -100.0F);
        guiGraphics.pose().scale(1.75F, 1.75F, 1.0F);
        icon.draw(guiGraphics, 0, 0);
        guiGraphics.pose().popPose();

        Component hitsText = Component.literal(recipe.hitsRequired() + " Hits");
        Minecraft minecraft = Minecraft.getInstance();
        int textWidth = minecraft.font.width(hitsText);
        guiGraphics.drawString(minecraft.font, hitsText, 40 - (textWidth / 2), 2, 0xFF404040, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WorkstoneRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 11)
                .addIngredients(recipe.ingredient());

        builder.addSlot(RecipeIngredientRole.CATALYST, 31, 32)
                .addItemStacks(this.hammers);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 12)
                .addItemStack(recipe.output());
    }
}