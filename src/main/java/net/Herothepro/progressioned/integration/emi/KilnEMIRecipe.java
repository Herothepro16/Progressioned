package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.recipe.KilnRecipe;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class KilnEMIRecipe implements EmiRecipe {

    private final ResourceLocation id;
    private final KilnRecipe recipe;
    private final EmiIngredient input;
    private final EmiStack output;

    public KilnEMIRecipe(ResourceLocation id, KilnRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.input = EmiIngredient.of(recipe.getIngredient());
        this.output = EmiStack.of(recipe.getResultItem(null));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.KILN_CATEGORY;
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
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 82;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int cookTimeTicks = recipe.getCookTime();
        int cookTimeMs = cookTimeTicks * 50;

        widgets.addSlot(input, 0, 2);
        widgets.addSlot(output, 56, 0).large(true).recipeContext(this);

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 22);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 22, 10000, false, true, true);

        double seconds = cookTimeTicks / 20.0;
        Component durationText = Component.literal(String.format("%.1fs", seconds));

        widgets.addFillingArrow(24, 2, cookTimeMs)
                .tooltip((mouseX, mouseY) -> List.of(ClientTooltipComponent.create(durationText.getVisualOrderText())));
    }
}