package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Progressioned.MOD_ID);

    public static final DeferredItem<Item> FLINT_SHARD = ITEMS.register("flint_shard",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLINT_CHIP = ITEMS.register("flint_chip",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHARP_BONE= ITEMS.register("sharp_bone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PLANT_FIBER = ITEMS.register("plant_fiber",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLANT_STRING = ITEMS.register("plant_string",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FIBER_MESH = ITEMS.register("fiber_mesh",
            () -> new FibermeshItem());

    public static final DeferredItem<PickaxeItem> FLINT_PICK = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FLINT,0.5f,-2.8f))));
    public static final DeferredItem<AxeItem> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.FLINT,4,-3.2f))));
    public static final DeferredItem<SwordItem> FLINT_KNIFE = ITEMS.register("flint_knife",
            () -> new SwordItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.FLINT,1.5f,-2.4f))));
    public static final DeferredItem<ShovelItem> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ShovelItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.FLINT,0.25f,-3f))));
    public static final DeferredItem<SwordItem> FLINT_SPEAR = ITEMS.register("flint_spear",
            () -> new SwordItem(ModToolTiers.FLINT, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4.0, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -2.4, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "flint_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));

    public static final DeferredItem<SwordItem> BONE_SWORD = ITEMS.register("bone_sword",
            () -> new SwordItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE,3f,-2.5f))));
    public static final DeferredItem<SwordItem> BONE_SPEAR = ITEMS.register("bone_spear",
            () -> new SwordItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));

    public static final DeferredItem<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER,4.5f,-2.4f))));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER,1f,-2.8f))));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER,5f,-3.1f))));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COPPER,1.5f,-3f))));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COPPER,0f,-2.4f))));
    public static final DeferredItem<SwordItem> COPPER_SPEAR = ITEMS.register("copper_spear",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "copper_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
