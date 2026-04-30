package net.Herothepro.progressioned.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ChoppingEMIRecipe implements EmiRecipe {
    private final EmiRecipeCategory category;
    private final ResourceLocation id;
    private final ChoppingRecipe recipe;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;

    public ChoppingEMIRecipe(EmiRecipeCategory category, ResourceLocation id, ChoppingRecipe recipe) {
        this.category = category;
        this.id = id;
        this.recipe = recipe;
        this.inputs = List.of(EmiIngredient.of(recipe.getInput()));
        this.outputs = List.of(EmiStack.of(recipe.getResult()));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return category;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 45, 11);
        widgets.addSlot(inputs.get(0), 1, 11);
        widgets.addSlot(outputs.get(0), 95, 11).recipeContext(this);
    }
}