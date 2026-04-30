package net.Herothepro.progressioned.compat;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.Herothepro.progressioned.block.ModBlocks;

public class ChoppingCategory {
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping");

    public static Component getTitle() {
        return Component.translatable("category.progressioned.chopping");
    }

    public static ItemStack getIcon() {
        return new ItemStack(ModBlocks.CHOPPING_BLOCK.get());
    }
}