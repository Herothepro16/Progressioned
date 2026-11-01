package net.Herothepro.progressioned.item;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.custom.ClayEmptyBucketItem;
import net.Herothepro.progressioned.item.custom.ClayLavaBucketItem;
import net.Herothepro.progressioned.item.custom.ClayWaterBucketItem;
import net.Herothepro.progressioned.item.custom.FireStickItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
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

    public static final DeferredItem<Item> STRAW = ITEMS.register("straw",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STRAW_CORD = ITEMS.register("straw_cord",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MUD_BALL = ITEMS.register("mud_ball",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MUD_BRICK = ITEMS.register("mud_brick",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PORCELAIN_CLAY_BALL = ITEMS.register("porcelain_clay_ball",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PORCELAIN_CLAY_BRICK = ITEMS.register("porcelain_clay_brick",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<HoeItem> FIBER_MESH = ITEMS.register("fiber_mesh",
            () -> new HoeItem(ModToolTiers.FIBER_MESH, new Item.Properties()));

    public static final DeferredItem<FireStickItem> FIRE_STICK = ITEMS.register("fire_stick",
            () -> new FireStickItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LEATHER_CORD = ITEMS.register("leather_cord",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_TIN = ITEMS.register("raw_tin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIN_DUST = ITEMS.register("tin_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_SILVER = ITEMS.register("raw_silver",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.register("silver_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_PLATE = ITEMS.register("silver_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVER_DUST = ITEMS.register("silver_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_PLATE = ITEMS.register("bronze_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRONZE_DUST = ITEMS.register("bronze_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ROSE_GOLD_INGOT = ITEMS.register("rose_gold_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROSE_GOLD_NUGGET = ITEMS.register("rose_gold_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROSE_GOLD_PLATE = ITEMS.register("rose_gold_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROSE_GOLD_DUST = ITEMS.register("rose_gold_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_PLATE = ITEMS.register("copper_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_PLATE = ITEMS.register("gold_plate",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<AxeItem> COPPER_SAW = ITEMS.register("copper_saw",
            () -> new AxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER,2f,-3.2f))));
    public static final DeferredItem<AxeItem> IRON_SAW = ITEMS.register("iron_saw",
            () -> new AxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON,3f,-3.2f))));
    public static final DeferredItem<AxeItem> DIAMOND_SAW = ITEMS.register("diamond_saw",
            () -> new AxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND,4f,-3.2f))));

    public static final DeferredItem<PickaxeItem> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new PickaxeItem(Tiers.STONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.STONE,2f,-3.2f))));
    public static final DeferredItem<PickaxeItem> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new PickaxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER,4f,-3.2f))));
    public static final DeferredItem<PickaxeItem> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new PickaxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON,3f,-3.2f))));
    public static final DeferredItem<PickaxeItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new PickaxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND,3.5f,-3f))));

    public static final DeferredItem<SwordItem> COPPER_KNIFE = ITEMS.register("copper_knife",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER,2.5f,-2.8f))));
    public static final DeferredItem<SwordItem> IRON_KNIFE = ITEMS.register("iron_knife",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON,1f,-2.8f))));
    public static final DeferredItem<SwordItem> DIAMOND_KNIFE = ITEMS.register("diamond_knife",
            () -> new SwordItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND,0.5f,-2.8f))));

    public static final DeferredItem<Item> UNFIRED_CLAY_BUCKET = ITEMS.register("unfired_clay_bucket",
            () -> new Item(new Item.Properties().stacksTo(4)));
    public static final DeferredItem<Item> CLAY_BUCKET = ITEMS.register("clay_bucket",
            () -> new ClayEmptyBucketItem(new Item.Properties().stacksTo(4)));
    public static final DeferredItem<Item> CLAY_WATER_BUCKET = ITEMS.register("clay_water_bucket",
            () -> new ClayWaterBucketItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CLAY_LAVA_BUCKET = ITEMS.register("clay_lava_bucket",
            () -> new ClayLavaBucketItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<PickaxeItem> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FLINT,0.5f,-2.8f))));
    public static final DeferredItem<AxeItem> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.FLINT,5,-3.2f))));
    public static final DeferredItem<SwordItem> FLINT_KNIFE = ITEMS.register("flint_knife",
            () -> new SwordItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.FLINT,1.5f,-2.4f))));
    public static final DeferredItem<ShovelItem> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ShovelItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.FLINT,0.25f,-3f))));
    public static final DeferredItem<HoeItem> FLINT_HOE = ITEMS.register("flint_hoe",
            () -> new HoeItem(ModToolTiers.FLINT , new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.FLINT,0f,-2.4f))));
    public static final DeferredItem<SwordItem> FLINT_SPEAR = ITEMS.register("flint_spear",
            () -> new SwordItem(ModToolTiers.FLINT, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            3, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "amethyst_bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));

    public static final DeferredItem<SwordItem> BONE_SWORD = ITEMS.register("bone_sword",
            () -> new SwordItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE,3.75f,-2.6f))));
    public static final DeferredItem<PickaxeItem> BONE_PICKAXE = ITEMS.register("bone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BONE,0.75f,-2.8f))));
    public static final DeferredItem<AxeItem> BONE_AXE = ITEMS.register("bone_axe",
            () -> new AxeItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BONE,4.25f,-3f))));
    public static final DeferredItem<SwordItem> BONE_CLUB = ITEMS.register("bone_club",
            () -> new SwordItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE,3.5f,-2.5f))));
    public static final DeferredItem<SwordItem> BONE_SPEAR = ITEMS.register("bone_spear",
            () -> new SwordItem(ModToolTiers.BONE, new Item.Properties().attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4.25, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3.1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "amethyst_bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())));

    public static final DeferredItem<SwordItem> AMETHYST_BONE_SWORD = ITEMS.register("amethyst_bone_sword",
            () -> new SwordItem(ModToolTiers.AMETHYST_BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.AMETHYST_BONE,6f,-2.4f))));
    public static final DeferredItem<PickaxeItem> AMETHYST_BONE_PICKAXE = ITEMS.register("amethyst_bone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.AMETHYST_BONE , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.AMETHYST_BONE,1f,-2.8f))));
    public static final DeferredItem<AxeItem> AMETHYST_BONE_AXE = ITEMS.register("amethyst_bone_axe",
            () -> new AxeItem(ModToolTiers.AMETHYST_BONE , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.AMETHYST_BONE,7f,-2.8f))));
    public static final DeferredItem<SwordItem> AMETHYST_BONE_SPEAR = ITEMS.register("amethyst_bone_spear",
            () -> new SwordItem(ModToolTiers.AMETHYST_BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -2.8, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "amethyst_bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND).build())));

    public static final DeferredItem<SwordItem> GOLDEN_BONE_SWORD = ITEMS.register("golden_bone_sword",
            () -> new SwordItem(ModToolTiers.GOLDEN_BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GOLDEN_BONE,5f,-2.4f))));
    public static final DeferredItem<PickaxeItem> GOLDEN_BONE_PICKAXE = ITEMS.register("golden_bone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.GOLDEN_BONE , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GOLDEN_BONE,1f,-2.8f))));
    public static final DeferredItem<AxeItem> GOLDEN_BONE_AXE = ITEMS.register("golden_bone_axe",
            () -> new AxeItem(ModToolTiers.GOLDEN_BONE , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GOLDEN_BONE,6f,-2.8f))));
    public static final DeferredItem<SwordItem> GOLDEN_BONE_SPEAR = ITEMS.register("golden_bone_spear",
            () -> new SwordItem(ModToolTiers.GOLDEN_BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "golden_bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND).build())));

    public static final DeferredItem<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER,4.5f,-2.4f))));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER,1f,-2.8f))));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER,6f,-3.1f))));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COPPER,1.5f,-3f))));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER , new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COPPER,0f,-2.4f))));
    public static final DeferredItem<SwordItem> COPPER_SPEAR = ITEMS.register("copper_spear",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "copper_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND).build())));

    public static final DeferredItem<SwordItem> BRONZE_SWORD = ITEMS.register("bronze_sword",
            () -> new SwordItem(ModToolTiers.BRONZE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BRONZE,4.75f,-2.4f))));
    public static final DeferredItem<PickaxeItem> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BRONZE , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BRONZE,1f,-2.8f))));
    public static final DeferredItem<AxeItem> BRONZE_AXE = ITEMS.register("bronze_axe",
            () -> new AxeItem(ModToolTiers.BRONZE , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BRONZE,6f,-3.1f))));
    public static final DeferredItem<ShovelItem> BRONZE_SHOVEL = ITEMS.register("bronze_shovel",
            () -> new ShovelItem(ModToolTiers.BRONZE , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BRONZE,1.5f,-3f))));
    public static final DeferredItem<HoeItem> BRONZE_HOE = ITEMS.register("bronze_hoe",
            () -> new HoeItem(ModToolTiers.BRONZE , new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BRONZE,0f,-2.4f))));

    public static final DeferredItem<ArmorItem> BRONZE_HELMET = ITEMS.register("bronze_helmet",
            () -> new ArmorItem(ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(24))));
    public static final DeferredItem<ArmorItem> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(24))));
    public static final DeferredItem<ArmorItem> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings",
            () -> new ArmorItem(ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(24))));
    public static final DeferredItem<ArmorItem> BRONZE_BOOTS = ITEMS.register("bronze_boots",
            () -> new ArmorItem(ModArmorMaterials.BRONZE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(24))));
    
    public static final DeferredItem<SwordItem> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword",
            () -> new SwordItem(ModToolTiers.ROSE_GOLD , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ROSE_GOLD,5.5f,-2.4f))));
    public static final DeferredItem<PickaxeItem> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ROSE_GOLD , new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ROSE_GOLD,1f,-2.8f))));
    public static final DeferredItem<AxeItem> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe",
            () -> new AxeItem(ModToolTiers.ROSE_GOLD , new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ROSE_GOLD,8f,-3f))));
    public static final DeferredItem<ShovelItem> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel",
            () -> new ShovelItem(ModToolTiers.ROSE_GOLD , new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ROSE_GOLD,1.5f,-3f))));
    public static final DeferredItem<HoeItem> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe",
            () -> new HoeItem(ModToolTiers.ROSE_GOLD , new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ROSE_GOLD,0f,-2.4f))));

    public static final DeferredItem<ArmorItem> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet",
            () -> new ArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(53))));
    public static final DeferredItem<ArmorItem> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(53))));
    public static final DeferredItem<ArmorItem> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings",
            () -> new ArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(53))));
    public static final DeferredItem<ArmorItem> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots",
            () -> new ArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(53))));

    public static final DeferredItem<Item> BLANK_MOLD = ITEMS.register("blank_mold",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> INGOT_MOLD = ITEMS.register("ingot_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> NUGGET_MOLD = ITEMS.register("nugget_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PLATE_MOLD = ITEMS.register("plate_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SWORD_BLADE_MOLD = ITEMS.register("sword_blade_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PICKAXE_HEAD_MOLD = ITEMS.register("pickaxe_head_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> AXE_HEAD_MOLD = ITEMS.register("axe_head_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SHOVEL_HEAD_MOLD = ITEMS.register("shovel_head_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HOE_HEAD_MOLD = ITEMS.register("hoe_head_mold",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> COPPER_SWORD_BLADE = ITEMS.register("copper_sword_blade",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COPPER_PICKAXE_HEAD = ITEMS.register("copper_pickaxe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COPPER_AXE_HEAD = ITEMS.register("copper_axe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COPPER_SHOVEL_HEAD = ITEMS.register("copper_shovel_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COPPER_HOE_HEAD = ITEMS.register("copper_hoe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    
    public static final DeferredItem<Item> BRONZE_SWORD_BLADE = ITEMS.register("bronze_sword_blade",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BRONZE_PICKAXE_HEAD = ITEMS.register("bronze_pickaxe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BRONZE_AXE_HEAD = ITEMS.register("bronze_axe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BRONZE_SHOVEL_HEAD = ITEMS.register("bronze_shovel_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BRONZE_HOE_HEAD = ITEMS.register("bronze_hoe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> IRON_SWORD_BLADE = ITEMS.register("iron_sword_blade",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> IRON_PICKAXE_HEAD = ITEMS.register("iron_pickaxe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> IRON_AXE_HEAD = ITEMS.register("iron_axe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> IRON_SHOVEL_HEAD = ITEMS.register("iron_shovel_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> IRON_HOE_HEAD = ITEMS.register("iron_hoe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> GOLD_SWORD_BLADE = ITEMS.register("gold_sword_blade",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLD_PICKAXE_HEAD = ITEMS.register("gold_pickaxe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLD_AXE_HEAD = ITEMS.register("gold_axe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLD_SHOVEL_HEAD = ITEMS.register("gold_shovel_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLD_HOE_HEAD = ITEMS.register("gold_hoe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ROSE_GOLD_SWORD_BLADE = ITEMS.register("rose_gold_sword_blade",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ROSE_GOLD_PICKAXE_HEAD = ITEMS.register("rose_gold_pickaxe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ROSE_GOLD_AXE_HEAD = ITEMS.register("rose_gold_axe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ROSE_GOLD_SHOVEL_HEAD = ITEMS.register("rose_gold_shovel_head",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ROSE_GOLD_HOE_HEAD = ITEMS.register("rose_gold_hoe_head",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}