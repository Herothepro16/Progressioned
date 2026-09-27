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

    public static final DeferredItem<Item> PORCELAIN_CLAY_BALL = ITEMS.register("porcelain_clay_ball",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PORCELAIN_CLAY_BRICK = ITEMS.register("porcelain_clay_brick",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_PLATE = ITEMS.register("copper_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_PLATE = ITEMS.register("gold_plate",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<HoeItem> FIBER_MESH = ITEMS.register("fiber_mesh",
            () -> new HoeItem(ModToolTiers.FIBER_MESH, new Item.Properties()));

    public static final DeferredItem<FireStickItem> FIRE_STICK = ITEMS.register("fire_stick",
            () -> new FireStickItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<AxeItem> COPPER_SAW = ITEMS.register("copper_saw",
            () -> new AxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER,2f,-3.2f))));
    public static final DeferredItem<AxeItem> IRON_SAW = ITEMS.register("iron_saw",
            () -> new AxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON,3f,-3.2f))));
    public static final DeferredItem<AxeItem> DIAMOND_SAW = ITEMS.register("diamond_saw",
            () -> new AxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND,4f,-3.2f))));

    public static final DeferredItem<PickaxeItem> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new PickaxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER,2.5f,-3.2f))));
    public static final DeferredItem<PickaxeItem> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new PickaxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON,3f,-3.2f))));
    public static final DeferredItem<PickaxeItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new PickaxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND,4f,-3.2f))));

    public static final DeferredItem<SwordItem> COPPER_KNIFE = ITEMS.register("copper_knife",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER,2.5f,-2.8f))));
    public static final DeferredItem<SwordItem> IRON_KNIFE = ITEMS.register("iron_knife",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON,1f,-2.8f))));
    public static final DeferredItem<SwordItem> DIAMOND_KNIFE = ITEMS.register("diamond_knife",
            () -> new SwordItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND,0f,-2.8f))));

    public static final DeferredItem<Item> UNFIRED_CLAY_BUCKET = ITEMS.register("unfired_clay_bucket",
            () -> new Item(new Item.Properties().stacksTo(4)));
    public static final DeferredItem<Item> CLAY_BUCKET = ITEMS.register("clay_bucket",
            () -> new ClayEmptyBucketItem(new Item.Properties().stacksTo(4)));
    public static final DeferredItem<Item> CLAY_WATER_BUCKET = ITEMS.register("clay_water_bucket",
            () -> new ClayWaterBucketItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CLAY_LAVA_BUCKET = ITEMS.register("clay_lava_bucket",
            () -> new ClayLavaBucketItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<PickaxeItem> FLINT_PICK = ITEMS.register("flint_pickaxe",
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
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE,3.75f,-2.4f))));
    public static final DeferredItem<SwordItem> BONE_CLUB = ITEMS.register("bone_club",
            () -> new SwordItem(ModToolTiers.BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE,3.5f,-2.3f))));
    public static final DeferredItem<SwordItem> BONE_SPEAR = ITEMS.register("bone_spear",
            () -> new SwordItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4.25, AttributeModifier.Operation.ADD_VALUE),
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

    public static final DeferredItem<SwordItem> AMETHYST_BONE_SWORD = ITEMS.register("amethyst_bone_sword",
            () -> new SwordItem(ModToolTiers.AMETHYST_BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.AMETHYST_BONE,6f,-2.4f))));
    public static final DeferredItem<SwordItem> AMETHYST_BONE_SPEAR = ITEMS.register("amethyst_bone_spear",
            () -> new SwordItem(ModToolTiers.AMETHYST_BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -2.8, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "amethyst_bone_spear_range"),
                                            0.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .build())));

    public static final DeferredItem<SwordItem> GOLDEN_BONE_SWORD = ITEMS.register("golden_bone_sword",
            () -> new SwordItem(ModToolTiers.GOLDEN_BONE , new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GOLDEN_BONE,5f,-2.4f))));
    public static final DeferredItem<SwordItem> GOLDEN_BONE_SPEAR = ITEMS.register("golden_bone_spear",
            () -> new SwordItem(ModToolTiers.GOLDEN_BONE, new Item.Properties()
                    .attributes(ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE,
                                    new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            4.5, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED,
                                    new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                            -3, AttributeModifier.Operation.ADD_VALUE),
                                    EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE,
                                    new AttributeModifier(
                                            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "golden_bone_spear_range"),
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

    public static final DeferredItem<BlockItem> CHARCOAL_BLOCK = ITEMS.register("charcoal_block",
            () -> new BlockItem(ModBlocks.CHARCOAL_BLOCK.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> THATCH = ITEMS.register("thatch",
            () -> new BlockItem(ModBlocks.THATCH.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> THATCH_SLAB = ITEMS.register("thatch_slab",
            () -> new BlockItem(ModBlocks.THATCH_SLAB.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> PORCELAIN_BRICKS = ITEMS.register("porcelain_bricks",
            () -> new BlockItem(ModBlocks.PORCELAIN_BRICKS.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> PORCELAIN_BRICK_SLAB = ITEMS.register("porcelain_brick_slab",
            () -> new BlockItem(ModBlocks.PORCELAIN_BRICK_SLAB.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> PORCELAIN_BRICK_STAIRS = ITEMS.register("porcelain_brick_stairs",
            () -> new BlockItem(ModBlocks.PORCELAIN_BRICK_STAIRS.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> PORCELAIN_BRICK_WALL = ITEMS.register("porcelain_brick_wall",
            () -> new BlockItem(ModBlocks.PORCELAIN_BRICK_WALL.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> CHOPPING_BLOCK = ITEMS.register("chopping_block",
            () -> new BlockItem(ModBlocks.CHOPPING_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> CAMPFIRE_BASE = ITEMS.register("campfire_base",
            () -> new BlockItem(ModBlocks.CAMPFIRE_BASE.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> PORCELAIN_KILN = ITEMS.register("porcelain_kiln",
            () -> new BlockItem(ModBlocks.PORCELAIN_KILN.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> WORKSTONE = ITEMS.register("workstone",
            () -> new BlockItem(ModBlocks.WORKSTONE.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> OAK_POST = registerBlockItem("oak_post", ModBlocks.OAK_POST);
    public static final DeferredItem<BlockItem> SPRUCE_POST = registerBlockItem("spruce_post", ModBlocks.SPRUCE_POST);
    public static final DeferredItem<BlockItem> BIRCH_POST = registerBlockItem("birch_post", ModBlocks.BIRCH_POST);
    public static final DeferredItem<BlockItem> JUNGLE_POST = registerBlockItem("jungle_post", ModBlocks.JUNGLE_POST);
    public static final DeferredItem<BlockItem> ACACIA_POST = registerBlockItem("acacia_post", ModBlocks.ACACIA_POST);
    public static final DeferredItem<BlockItem> DARK_OAK_POST = registerBlockItem("dark_oak_post", ModBlocks.DARK_OAK_POST);
    public static final DeferredItem<BlockItem> MANGROVE_POST = registerBlockItem("mangrove_post", ModBlocks.MANGROVE_POST);
    public static final DeferredItem<BlockItem> CHERRY_POST = registerBlockItem("cherry_post", ModBlocks.CHERRY_POST);

    public static final DeferredItem<BlockItem> STRIPPED_OAK_POST = registerBlockItem("stripped_oak_post", ModBlocks.STRIPPED_OAK_POST);
    public static final DeferredItem<BlockItem> STRIPPED_SPRUCE_POST = registerBlockItem("stripped_spruce_post", ModBlocks.STRIPPED_SPRUCE_POST);
    public static final DeferredItem<BlockItem> STRIPPED_BIRCH_POST = registerBlockItem("stripped_birch_post", ModBlocks.STRIPPED_BIRCH_POST);
    public static final DeferredItem<BlockItem> STRIPPED_JUNGLE_POST = registerBlockItem("stripped_jungle_post", ModBlocks.STRIPPED_JUNGLE_POST);
    public static final DeferredItem<BlockItem> STRIPPED_ACACIA_POST = registerBlockItem("stripped_acacia_post", ModBlocks.STRIPPED_ACACIA_POST);
    public static final DeferredItem<BlockItem> STRIPPED_DARK_OAK_POST = registerBlockItem("stripped_dark_oak_post", ModBlocks.STRIPPED_DARK_OAK_POST);
    public static final DeferredItem<BlockItem> STRIPPED_MANGROVE_POST = registerBlockItem("stripped_mangrove_post", ModBlocks.STRIPPED_MANGROVE_POST);
    public static final DeferredItem<BlockItem> STRIPPED_CHERRY_POST = registerBlockItem("stripped_cherry_post", ModBlocks.STRIPPED_CHERRY_POST);

    private static DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<? extends Block> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}