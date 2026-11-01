package net.Herothepro.progressioned.integration.emi;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.Herothepro.progressioned.integration.jei.KnappingDeployerRenderer;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KnappingDeployerEMIRecipe implements EmiRecipe {

    private static final int WIDTH = 177;
    private static final int HEIGHT = 70;

    private final ResourceLocation id;
    private final KnappingRecipe recipe;
    private final EmiIngredient ingredient;
    private final EmiIngredient targetBlock;
    private final EmiStack result;

    public KnappingDeployerEMIRecipe(ResourceLocation id, KnappingRecipe recipe) {
        this.id = id;
        this.recipe = recipe;
        this.ingredient = EmiIngredient.of(recipe.ingredient());
        this.targetBlock = EmiIngredient.of(recipe.target());
        this.result = EmiStack.of(recipe.result());
    }

    @Override
    public EmiRecipeCategory getCategory() { return CreateEMICompat.KNAPPING_DEPLOYER_CATEGORY; }

    @Override
    public @Nullable ResourceLocation getId() { return id; }

    @Override
    public List<EmiIngredient> getInputs() { return List.of(ingredient, targetBlock); }

    @Override
    public List<EmiStack> getOutputs() { return List.of(result); }

    @Override
    public int getDisplayWidth() { return WIDTH; }

    @Override
    public int getDisplayHeight() { return HEIGHT; }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addDrawable(0, 0, WIDTH, HEIGHT, (graphics, mouseX, mouseY, delta) -> {
            AllGuiTextures.JEI_SLOT.render(graphics, 26, 50);
            AllGuiTextures.JEI_SLOT.render(graphics, 50, 4);
            (recipe.chance() < 1.0f ? AllGuiTextures.JEI_CHANCE_SLOT : AllGuiTextures.JEI_SLOT).render(graphics, 131, 50);

            AllGuiTextures.JEI_SHADOW.render(graphics, 62, 57);
            AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);
            KnappingDeployerRenderer.draw(graphics, WIDTH / 2 - 13, 22, currentTargetState());
        });

        widgets.addSlot(targetBlock, 26, 50)
                .drawBack(false)
                .appendTooltip(Component.literal("Target block (in the world)").withStyle(ChatFormatting.GRAY));

        widgets.addSlot(ingredient, 50, 4).drawBack(false);

        var output = widgets.addSlot(result, 131, 50).drawBack(false).recipeContext(this);
        if (recipe.chance() < 1.0f) {
            output.appendTooltip(Component.literal(Math.round(recipe.chance() * 100) + "% chance")
                    .withStyle(ChatFormatting.GOLD));
        }
    }

    private BlockState currentTargetState() {
        List<EmiStack> stacks = targetBlock.getEmiStacks();
        if (!stacks.isEmpty()) {
            ItemStack stack = stacks.get((int) ((System.currentTimeMillis() / 1000L) % stacks.size())).getItemStack();
            if (stack.getItem() instanceof BlockItem blockItem) {
                return blockItem.getBlock().defaultBlockState();
            }
        }
        return Blocks.STONE.defaultBlockState();
    }
}