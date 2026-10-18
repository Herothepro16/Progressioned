package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.CastingBasinRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CastingBasinEMIRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final CastingBasinRecipe recipe;
    private final EmiStack fluidInput;
    private final EmiStack output;

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/casting_gui.png");

    public CastingBasinEMIRecipe(ResourceLocation id, CastingBasinRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.fluidInput = EmiStack.of(recipe.input().getFluid(), recipe.input().getAmount());
        this.output = EmiStack.of(recipe.getResultItem(null));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.CASTING_BASIN_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(fluidInput);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 52;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 4, 128, 48, 0, 0);

        widgets.addTexture(GUI_TEXTURE,32,36,16,16,144,0);

        widgets.addTank(fluidInput, 37, 7, 7, 30, recipe.input().getAmount()).drawBack(false);

        int castingMs = recipe.coolingTime() * 50;
        widgets.addFillingArrow(60, 20, castingMs);

        float seconds = recipe.coolingTime() / 20.0f;
        String timeText = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);
        widgets.addText(Component.literal(timeText), 62, 40, 0xFF404040, false);

        widgets.addSlot(output, 95, 19).drawBack(false).recipeContext(this);
    }
}