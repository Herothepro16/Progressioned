package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier FLINT = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL,
            60, 2f,1f,8, ()-> Ingredient.of(ModItems.FLINT_CHIP));
}
