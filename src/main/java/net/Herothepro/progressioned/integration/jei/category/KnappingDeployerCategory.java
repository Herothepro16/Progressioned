package net.Herothepro.progressioned.integration.jei.category;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.integration.jei.KnappingDeployerRenderer;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class KnappingDeployerCategory implements IRecipeCategory<KnappingRecipe> {

    public static final RecipeType<KnappingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "knapping_deployer", KnappingRecipe.class);

    private static final int WIDTH = 177;
    private static final int HEIGHT = 70;
    private static final String TARGET_SLOT = "target_block";

    private final IDrawable icon;

    public KnappingDeployerCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AllBlocks.DEPLOYER.get()));
    }

    @Override
    public RecipeType<KnappingRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public Component getTitle() { return Component.translatable("gui.progressioned.category.knapping_deployer"); }

    @Override
    public int getWidth() { return WIDTH; }

    @Override
    public int getHeight() { return HEIGHT; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KnappingRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> targets = BuiltInRegistries.BLOCK.getTag(recipe.target())
                .map(tag -> tag.stream()
                        .map(Holder::value)
                        .map(Block::asItem)
                        .filter(item -> item != Items.AIR)
                        .map(ItemStack::new)
                        .toList())
                .orElse(List.of());

        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51)
                .setSlotName(TARGET_SLOT)
                .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                .addItemStacks(targets)
                .addRichTooltipCallback((view, tooltip) ->
                        tooltip.add(Component.literal("Target block (in the world)").withStyle(ChatFormatting.GRAY)));

        builder.addSlot(RecipeIngredientRole.INPUT, 51, 5)
                .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                .addIngredients(recipe.ingredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 51)
                .setBackground(CreateRecipeCategory.getRenderedSlot(recipe.chance()), -1, -1)
                .addItemStack(recipe.result())
                .addRichTooltipCallback((view, tooltip) -> {
                    if (recipe.chance() < 1.0f) {
                        tooltip.add(Component.literal(Math.round(recipe.chance() * 100) + "% chance")
                                .withStyle(ChatFormatting.GOLD));
                    }
                });
    }

    @Override
    public void draw(KnappingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);

        ItemStack shown = recipeSlotsView.findSlotByName(TARGET_SLOT)
                .flatMap(IRecipeSlotView::getDisplayedItemStack)
                .orElse(ItemStack.EMPTY);
        BlockState state = shown.getItem() instanceof BlockItem blockItem
                ? blockItem.getBlock().defaultBlockState()
                : Blocks.STONE.defaultBlockState();

        KnappingDeployerRenderer.draw(graphics, WIDTH / 2 - 13, 22, state);
    }
}