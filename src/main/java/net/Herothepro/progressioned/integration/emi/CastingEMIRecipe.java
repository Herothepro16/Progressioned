package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.CastingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CastingEMIRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final CastingRecipe recipe;
    private final EmiIngredient moldInput;
    private final EmiIngredient fluidInput;
    private final EmiStack output;

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/casting_gui.png");

    public CastingEMIRecipe(ResourceLocation id, CastingRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.moldInput = EmiIngredient.of(recipe.getMoldInput());

        List<EmiStack> fluidStacks = Arrays.stream(recipe.getFluidInput().getFluids())
                .map(fs -> EmiStack.of(fs.getFluid(), recipe.getFluidInput().amount()))
                .toList();
        this.fluidInput = EmiIngredient.of(fluidStacks);

        this.output = EmiStack.of(recipe.getResultItem(null));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.CASTING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(moldInput, fluidInput);
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

        widgets.addTexture(GUI_TEXTURE,32,36,16,16,128,0);

        widgets.addSlot(moldInput, 31, 19).drawBack(false);

        widgets.addTank(fluidInput, 37, 8 , 7, 13, recipe.getFluidAmount()).drawBack(false);

        float seconds = recipe.getCoolingTime() / 20.0f;
        String timeText = (seconds % 1 == 0) ? String.format("%ds", (int) seconds) : String.format("%.1fs", seconds);
        widgets.addText(Component.literal(timeText), 62, 38, 0xFF404040, false);

        widgets.addFillingArrow(60, 20, 16000);

        widgets.addSlot(output, 95, 19).drawBack(false).recipeContext(this);
    }
}