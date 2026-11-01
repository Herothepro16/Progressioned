package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.recipe.WorkstoneRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WorkstoneEMIRecipe implements EmiRecipe {

    private static final TagKey<Item> HAMMERS_TAG =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/hammer"));

    private final ResourceLocation id;
    private final WorkstoneRecipe recipe;
    private final EmiIngredient input;
    private final EmiStack output;
    private final EmiIngredient catalyst;

    public WorkstoneEMIRecipe(ResourceLocation id, WorkstoneRecipe recipe) {
        this.id = id;
        this.recipe = recipe;

        this.input = EmiIngredient.of(recipe.ingredient());
        this.output = EmiStack.of(recipe.output());
        this.catalyst = EmiIngredient.of(HAMMERS_TAG);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.WORKSTONE_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(catalyst);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 94;
    }

    @Override
    public int getDisplayHeight() {
        return 50;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                Progressioned.MOD_ID, "textures/gui/chopping_gui.png");

        widgets.addTexture(texture, 0, 0, 94, 50, 0, 0);

        widgets.addDrawable(0, 0, 94, 50, (guiGraphics, mouseX, mouseY, delta) -> {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 10.0F, -100.0F);
            guiGraphics.pose().scale(1.8F, 1.8F, 1.0F);
            guiGraphics.renderFakeItem(new ItemStack(ModBlocks.WORKSTONE.get()), 0, 0);
            guiGraphics.pose().popPose();

            Component hitsText = Component.literal(recipe.hitsRequired() + " Hits");
            Minecraft minecraft = Minecraft.getInstance();
            int textWidth = minecraft.font.width(hitsText);
            guiGraphics.drawString(minecraft.font, hitsText, 40 - (textWidth / 2), 2, 0xFF404040, false);
        });

        widgets.addSlot(input, 5, 9).drawBack(false);
        widgets.addSlot(catalyst, 30, 31).drawBack(false);
        widgets.addSlot(output, 60, 11).drawBack(false).recipeContext(this);
    }
}