package net.Herothepro.progressioned.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.Herothepro.progressioned.compat.ChoppingCategory;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.minecraft.client.Minecraft;

@EmiEntrypoint
public class EMIPlugin implements EmiPlugin {
    public static final EmiRecipeCategory CHOPPING_CATEGORY = new EmiRecipeCategory(
            ChoppingCategory.ID,
            EmiStack.of(ChoppingCategory.getIcon()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(CHOPPING_CATEGORY);
        registry.addWorkstation(CHOPPING_CATEGORY, EmiStack.of(ChoppingCategory.getIcon()));

        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.CHOPPING_TYPE.get())
                .forEach(holder -> registry.addRecipe(
                        new ChoppingEMIRecipe(CHOPPING_CATEGORY, holder.id(), holder.value())));
    }
}