package net.Herothepro.progressioned.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.MeltingRecipe;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.List;

public class MeltingEMIRecipe implements EmiRecipe {

    private final ResourceLocation id;
    private final MeltingRecipe recipe;
    private final EmiIngredient input;
    private final EmiStack outputFluid;
    private final EmiIngredient fuel;

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/melting_gui.png");

    public MeltingEMIRecipe(ResourceLocation id, MeltingRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.input = EmiIngredient.of(recipe.getInput());
        this.outputFluid = EmiStack.of(recipe.getOutput().getFluid(), recipe.getOutput().getAmount());

        this.fuel = EmiIngredient.of(List.of(
                EmiStack.of(Items.COAL),
                EmiStack.of(Items.CHARCOAL),
                EmiStack.of(Items.LAVA_BUCKET),
                EmiStack.of(Items.BLAZE_ROD)
        ));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ProgressionedEMIPlugin.MELTING_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(fuel, input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(outputFluid);
    }

    @Override
    public int getDisplayWidth() {
        return 132;
    }

    @Override
    public int getDisplayHeight() {
        return 44;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(GUI_TEXTURE, 0, 0, 132, 44, 0, 0);

        widgets.addFillingArrow(57, 7, 16000)
                .tooltip((mouseX, mouseY) ->
                        List.of(ClientTooltipComponent.create(Component.literal(String.format("%.1fs", recipe.getCookingTime() / 20.0)).getVisualOrderText())));

        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 5, 7, 16000, false, true, true);

        widgets.addSlot(input, 31, 6)
                        .drawBack(false);

        widgets.addSlot(fuel, 4, 24);

        int tankX = 87;
        int tankY = 1;
        int tankWidth = 42;
        int maxTankHeight = 38;

        widgets.addTank(outputFluid, tankX, tankY, tankWidth, maxTankHeight, 1296)
                .drawBack(false)
                .recipeContext(this);

        widgets.addTexture(GUI_TEXTURE,88,2,7,33,132,2);
    }
}