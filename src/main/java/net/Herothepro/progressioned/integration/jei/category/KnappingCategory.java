package net.Herothepro.progressioned.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.recipe.KnappingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class KnappingCategory implements IRecipeCategory<KnappingRecipe> {

    public static final RecipeType<KnappingRecipe> RECIPE_TYPE =
            RecipeType.create(Progressioned.MOD_ID, "knapping", KnappingRecipe.class);

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/knapping_gui.png");

    private static final int WIDTH = 128;
    private static final int HEIGHT = 48;

    private final IDrawable background;
    private final IDrawable icon;

    private final Map<KnappingRecipe, List<ItemStack>> targetCache = new IdentityHashMap<>();

    public KnappingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.STONE));
    }

    @Override
    public RecipeType<KnappingRecipe> getRecipeType() { return RECIPE_TYPE; }

    @Override
    public Component getTitle() { return Component.translatable("gui.progressioned.category.knapping"); }

    @Override
    public int getWidth() { return WIDTH; }

    @Override
    public int getHeight() { return HEIGHT; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KnappingRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> targets = BuiltInRegistries.BLOCK.getTag(recipe.target())
                .map(tag -> tag.stream()
                        .map(Holder::value)
                        .map(Block::asItem)
                        .filter(item -> item != net.minecraft.world.item.Items.AIR)
                        .map(ItemStack::new)
                        .toList())
                .orElse(List.of());
        targetCache.put(recipe, targets);

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 28).addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 28).addItemStack(recipe.result());

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(targets);
    }

    @Override
    public void draw(KnappingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        background.draw(graphics, 0, 0);

        ItemStack target = cycle(targetCache.getOrDefault(recipe, List.of()));
        if (!target.isEmpty()) {
            graphics.pose().pushPose();
            graphics.pose().translate(54 + 9, 6 + 9, 0);
            graphics.pose().scale(1.4f, 1.4f, 1.4f);
            graphics.renderItem(target, -8, -8);
            graphics.pose().popPose();
        }

        ItemStack tool = cycle(recipe.ingredient().getItems());
        if (!tool.isEmpty()) {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 200.0f);

            long time = System.currentTimeMillis();
            float progress = (time % 800L) / 800.0f;
            float strike = 0;
            if (progress > 0.6f) {
                float p = (progress - 0.6f) / 0.4f;
                strike = (float) Math.sin(p * Math.PI) * 4.0f;
            }
            int currentX = 46 + (int) (strike * 0.8f);
            int currentY = 12 - (int) strike;
            graphics.renderItem(tool, currentX, currentY);

            graphics.pose().popPose();
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, KnappingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 54 && mouseX < 76 && mouseY >= 6 && mouseY < 28) {
            ItemStack target = cycle(targetCache.getOrDefault(recipe, List.of()));
            Minecraft mc = Minecraft.getInstance();
            if (!target.isEmpty() && mc.level != null) {
                tooltip.addAll(target.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL));
            }
        }
    }

    private static ItemStack cycle(List<ItemStack> stacks) {
        if (stacks.isEmpty()) return ItemStack.EMPTY;
        return stacks.get((int) ((System.currentTimeMillis() / 1000L) % stacks.size()));
    }

    private static ItemStack cycle(ItemStack[] stacks) {
        if (stacks.length == 0) return ItemStack.EMPTY;
        return stacks[(int) ((System.currentTimeMillis() / 1000L) % stacks.length)];
    }
}