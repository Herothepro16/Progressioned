package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier FLINT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL,
            70, 1.75f,0f,10, ()-> Ingredient.of(ModItems.FLINT_CHIP));
    public static final Tier BONE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_BONE_TOOL,
            32, 2.5f,0f,20, ()-> Ingredient.of(Items.BONE));
    public static final Tier AMETHYST_BONE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_BONE_TOOL,
            275, 2.5f,0f,25, ()-> Ingredient.of(Items.AMETHYST_SHARD));
    public static final Tier GOLDEN_BONE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_BONE_TOOL,
            350, 2.8f,0f,22, ()-> Ingredient.of(Items.GOLD_INGOT));
    public static final Tier COPPER = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            190, 5.0f,0f,13, ()-> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier FIBER_MESH = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL,
            16, 0f,0f,0, ()-> Ingredient.of(ModItems.PLANT_STRING));
}
