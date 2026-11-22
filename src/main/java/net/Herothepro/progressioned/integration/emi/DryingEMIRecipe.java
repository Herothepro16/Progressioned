package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.DryingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DryingEMIRecipe implements EmiRecipe {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/drying_gui.png");

    private static final int TIME_X = 40;
    private static final int TIME_Y = 30;

    private final ResourceLocation id;
    private final int dryingTime;
    private final EmiIngredient input;
    private final EmiStack output;

    public DryingEMIRecipe(ResourceLocation id, DryingRecipe recipe) {
        this.id = id;
        this.dryingTime = recipe.dryingTime();
        this.input = EmiIngredient.of(recipe.ingredient());
        this.output = EmiStack.of(recipe.result());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.DRYING_CATEGORY;
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
        return 96;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    private static String formatTime(int ticks) {
        double seconds = ticks / 20.0;
        if (seconds == Math.floor(seconds)) {
            return (int) seconds + "s";
        }
        return String.format("%.1fs", seconds);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 96, 40, 0, 0);

        widgets.addSlot(input, 11, 12).drawBack(false);
        widgets.addSlot(output, 67, 12).drawBack(false).recipeContext(this);

        widgets.addDrawable(0, 0, 96, 40, (guiGraphics, mouseX, mouseY, delta) -> {
            Component timeText = Component.literal(formatTime(dryingTime));
            guiGraphics.drawString(Minecraft.getInstance().font, timeText, TIME_X, TIME_Y, 0xFF404040, false);
        });
    }
}