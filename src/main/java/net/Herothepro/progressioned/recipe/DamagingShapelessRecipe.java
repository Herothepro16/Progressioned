package net.Herothepro.progressioned.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DamagingShapelessRecipe implements CraftingRecipe {
    private final String group;
    private final CraftingBookCategory category;
    private final ItemStack result;
    private final List<Ingredient> ingredients;
    private final Ingredient damagingTool;
    private final int damageAmount;

    public DamagingShapelessRecipe(String group, CraftingBookCategory category,
                                   ItemStack result, List<Ingredient> ingredients,
                                   Ingredient damagingTool, int damageAmount) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.damagingTool = damagingTool;
        this.damageAmount = damageAmount;
    }

    @Override
    public String getGroup() { return group; }

    @Override
    public CraftingBookCategory category() { return category; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) { return result; }

    @Override
    public net.minecraft.core.NonNullList<Ingredient> getIngredients() {
        net.minecraft.core.NonNullList<Ingredient> list = net.minecraft.core.NonNullList.create();
        list.addAll(ingredients);
        list.add(damagingTool);
        return list;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<Ingredient> remaining = new ArrayList<>(ingredients);
        boolean toolFound = false;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (!toolFound && damagingTool.test(stack)) {
                if (stack.getDamageValue() + damageAmount > stack.getMaxDamage()) return false;
                toolFound = true;
                continue;
            }

            boolean matched = false;
            for (int j = 0; j < remaining.size(); j++) {
                if (remaining.get(j).test(stack)) {
                    remaining.remove(j);
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }

        return toolFound && remaining.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public net.minecraft.core.NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        net.minecraft.core.NonNullList<ItemStack> remaining =
                net.minecraft.core.NonNullList.withSize(input.size(), ItemStack.EMPTY);

        boolean toolHandled = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!toolHandled && damagingTool.test(stack)) {
                toolHandled = true;
                ItemStack damaged = stack.copy();
                damaged.setDamageValue(damaged.getDamageValue() + damageAmount);
                if (damaged.getDamageValue() < damaged.getMaxDamage()) {
                    remaining.set(i, damaged);
                }
            }
        }
        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= ingredients.size() + 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DAMAGING_SHAPELESS_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    public static class Serializer implements RecipeSerializer<DamagingShapelessRecipe> {
        public static final MapCodec<DamagingShapelessRecipe> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        com.mojang.serialization.Codec.STRING.optionalFieldOf("group", "")
                                .forGetter(r -> r.group),
                        CraftingBookCategory.CODEC.fieldOf("category")
                                .orElse(CraftingBookCategory.MISC).forGetter(r -> r.category),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                        Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients),
                        Ingredient.CODEC.fieldOf("tool").forGetter(r -> r.damagingTool),
                        com.mojang.serialization.Codec.INT.optionalFieldOf("damage", 1)
                                .forGetter(r -> r.damageAmount)
                ).apply(instance, DamagingShapelessRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DamagingShapelessRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8, r -> r.group,
                        ByteBufCodecs.fromCodec(CraftingBookCategory.CODEC), r -> r.category,
                        ItemStack.STREAM_CODEC, r -> r.result,
                        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> r.ingredients,
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.damagingTool,
                        ByteBufCodecs.INT, r -> r.damageAmount,
                        DamagingShapelessRecipe::new);

        @Override
        public MapCodec<DamagingShapelessRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DamagingShapelessRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
