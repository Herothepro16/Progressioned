package net.Herothepro.progressioned.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FibermeshItem extends Item {
    public FibermeshItem() {
        super(new Properties()
                .durability(8)
                .setNoRepair());
    }
    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        container.hurtAndBreak(1, null, null, (t) -> {});
        return container;
    }
    @Override
    public boolean hasCraftingRemainingItem(@NotNull ItemStack stack) {
        return true;
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }
}

