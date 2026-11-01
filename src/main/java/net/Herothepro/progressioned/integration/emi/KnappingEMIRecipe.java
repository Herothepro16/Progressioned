package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KnappingEMIRecipe implements EmiRecipe {

    private final ResourceLocation id;
    private final KnappingRecipe recipe;
    private final EmiIngredient ingredient;
    private final EmiIngredient targetBlock;
    private final EmiStack result;

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/knapping_gui.png");

    public KnappingEMIRecipe(ResourceLocation id, KnappingRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.ingredient = EmiIngredient.of(recipe.ingredient());
        this.targetBlock = EmiIngredient.of(recipe.target());
        this.result = EmiStack.of(recipe.result());
    }

    @Override
    public EmiRecipeCategory getCategory() {return ProgressionedEMIPlugin.KNAPPING_CATEGORY;}

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(ingredient, targetBlock);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(result);
    }

    @Override
    public int getDisplayWidth() {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 48;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 128, 48, 0, 0);

        widgets.addSlot(ingredient, 5, 27).drawBack(false);

        widgets.addDrawable(54, 6, 22, 22, (graphics, mouseX, mouseY, delta) -> {
            graphics.pose().pushPose();
            graphics.pose().translate(9, 9, 0);
            graphics.pose().scale(1.4f, 1.4f, 1.4f);
            targetBlock.render(graphics, -8, -8, delta);
            graphics.pose().popPose();
        }).tooltip((mouseX, mouseY) -> targetBlock.getTooltip());

        widgets.addDrawable(0, 0, 128, 48, (graphics, mouseX, mouseY, delta) -> {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 200.0f);

            long time = System.currentTimeMillis();
            float progress = (time % 800L) / 800.0f;
            float strike = 0;
            if (progress > 0.6f) {
                float p = (progress - 0.6f) / 0.4f;
                strike = (float) Math.sin(p * Math.PI) * 4.0f;
            }
            int currentX = 46 + (int)(strike * 0.8f);
            int currentY = 12 - (int)strike;
            ingredient.render(graphics, currentX, currentY, delta);

            graphics.pose().popPose();
        });

        widgets.addSlot(result, 105, 27).drawBack(false).recipeContext(this);
    }
}