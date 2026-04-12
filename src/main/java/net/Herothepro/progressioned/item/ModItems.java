package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Progressioned.MOD_ID);

    public static final DeferredItem<Item> FLINT_CHIP = ITEMS.register("flint_chip",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PLANT_FIBER = ITEMS.register("plant_fiber",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<PickaxeItem> FLINT_PICK = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FLINT,2,-2.4f))));
    public static final DeferredItem<AxeItem> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FLINT,2,-2.4f))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
