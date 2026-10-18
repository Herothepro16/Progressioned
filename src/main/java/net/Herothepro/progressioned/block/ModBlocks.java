package net.Herothepro.progressioned.block;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.custom.*;
import net.Herothepro.progressioned.block.custom.entity.TableBlockEntity;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Progressioned.MOD_ID);

    public static final DeferredBlock<Block> CHARCOAL_BLOCK = registerBlock("charcoal_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK)));

    public static final DeferredBlock<Block> THATCH = registerBlock("thatch",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredBlock<RotatableSlabBlock> THATCH_SLAB = registerBlock("thatch_slab",
            () -> new RotatableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredBlock<Block> PORCELAIN_BRICKS = registerBlock("porcelain_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<SlabBlock> PORCELAIN_BRICK_SLAB = registerBlock("porcelain_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB)));
    public static final DeferredBlock<StairBlock> PORCELAIN_BRICK_STAIRS = registerBlock("porcelain_brick_stairs",
            () -> new StairBlock(ModBlocks.PORCELAIN_BRICKS.get().defaultBlockState()
                    ,BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_STAIRS)));
    public static final DeferredBlock<WallBlock> PORCELAIN_BRICK_WALL = registerBlock("porcelain_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_WALL)));

    public static final DeferredBlock<ChoppingBlock> CHOPPING_BLOCK = registerBlock("chopping_block",
            () -> new ChoppingBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<WorkstoneBlock> WORKSTONE = registerBlock("workstone",
            () -> new WorkstoneBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final DeferredBlock<CampfireBaseBlock> CAMPFIRE_BASE = registerBlock("campfire_base",
            () -> new CampfireBaseBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.WOOD).noOcclusion()));

    public static final DeferredBlock<KilnBlock> PORCELAIN_KILN = registerBlock("porcelain_kiln",
            () -> new KilnBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(2.0f).lightLevel(state -> state.getValue(KilnBlock.LIT) ? 13 : 0)));

    public static final DeferredBlock<Block> PORCELAIN_MELTER = registerBlock("porcelain_melter",
            () -> new MelterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion()));

    public static final DeferredBlock<HeaterBlock> PORCELAIN_HEATER = registerBlock("porcelain_heater",
            () -> new HeaterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));

    public static final DeferredBlock<FaucetBlock> PORCELAIN_FAUCET = registerBlock("porcelain_faucet",
            () -> new FaucetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));

    public static final DeferredBlock<TableBlock> PORCELAIN_CASTING_TABLE = registerBlock("porcelain_casting_table",
            () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));

    public static final DeferredBlock<BasinBlock> PORCELAIN_CASTING_BASIN = registerBlock("porcelain_casting_basin",
            () -> new BasinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).noOcclusion()));

    public static final DeferredBlock<Block> TIN_BLOCK = registerBlock("tin_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> SILVER_BLOCK = registerBlock("silver_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> BRONZE_BLOCK = registerBlock("bronze_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> ROSE_GOLD_BLOCK = registerBlock("rose_gold_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)));

    public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)));
    public static final DeferredBlock<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)));
    public static final DeferredBlock<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK)));

    public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK)));

    public static final DeferredBlock<PostBlock> OAK_POST = registerBlock("oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> SPRUCE_POST = registerBlock("spruce_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> BIRCH_POST = registerBlock("birch_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> JUNGLE_POST = registerBlock("jungle_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> ACACIA_POST = registerBlock("acacia_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> DARK_OAK_POST = registerBlock("dark_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> MANGROVE_POST = registerBlock("mangrove_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> CHERRY_POST = registerBlock("cherry_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG).noOcclusion()));

    public static final DeferredBlock<PostBlock> STRIPPED_OAK_POST = registerBlock("stripped_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_SPRUCE_POST = registerBlock("stripped_spruce_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_BIRCH_POST = registerBlock("stripped_birch_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_JUNGLE_POST = registerBlock("stripped_jungle_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_ACACIA_POST = registerBlock("stripped_acacia_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_DARK_OAK_POST = registerBlock("stripped_dark_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_MANGROVE_POST = registerBlock("stripped_mangrove_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_CHERRY_POST = registerBlock("stripped_cherry_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG).noOcclusion()));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}