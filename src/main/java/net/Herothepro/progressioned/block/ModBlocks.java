package net.Herothepro.progressioned.block;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.custom.*;
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

    public static final DeferredBlock<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK)));

    public static final DeferredBlock<Block> PORCELAIN_BRICKS = BLOCKS.register("porcelain_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<SlabBlock> PORCELAIN_BRICK_SLAB = BLOCKS.register("porcelain_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB)));
    public static final DeferredBlock<StairBlock> PORCELAIN_BRICK_STAIRS = BLOCKS.register("porcelain_brick_stairs",
            () -> new StairBlock(ModBlocks.PORCELAIN_BRICKS.get().defaultBlockState()
                    ,BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_STAIRS)));
    public static final DeferredBlock<WallBlock> PORCELAIN_BRICK_WALL = BLOCKS.register("porcelain_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_WALL)));

    public static final DeferredBlock<ChoppingBlock> CHOPPING_BLOCK = BLOCKS.register("chopping_block",
            () -> new ChoppingBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<WorkstoneBlock> WORKSTONE = BLOCKS.register("workstone",
            () -> new WorkstoneBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final DeferredBlock<CampfireBaseBlock> CAMPFIRE_BASE = BLOCKS.register("campfire_base",
            () -> new CampfireBaseBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.WOOD).noOcclusion()));

    public static final DeferredBlock<KilnBlock> PORCELAIN_KILN = BLOCKS.register("porcelain_kiln",
            () -> new KilnBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().strength(2.0f).lightLevel(state -> state.getValue(KilnBlock.LIT) ? 13 : 0)));

    public static final DeferredBlock<PostBlock> OAK_POST = BLOCKS.register("oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> SPRUCE_POST = BLOCKS.register("spruce_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> BIRCH_POST = BLOCKS.register("birch_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> JUNGLE_POST = BLOCKS.register("jungle_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> ACACIA_POST = BLOCKS.register("acacia_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> DARK_OAK_POST = BLOCKS.register("dark_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> MANGROVE_POST = BLOCKS.register("mangrove_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> CHERRY_POST = BLOCKS.register("cherry_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG).noOcclusion()));

    public static final DeferredBlock<PostBlock> STRIPPED_OAK_POST = BLOCKS.register("stripped_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_SPRUCE_POST = BLOCKS.register("stripped_spruce_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_BIRCH_POST = BLOCKS.register("stripped_birch_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_JUNGLE_POST = BLOCKS.register("stripped_jungle_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_ACACIA_POST = BLOCKS.register("stripped_acacia_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_DARK_OAK_POST = BLOCKS.register("stripped_dark_oak_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_MANGROVE_POST = BLOCKS.register("stripped_mangrove_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_LOG).noOcclusion()));
    public static final DeferredBlock<PostBlock> STRIPPED_CHERRY_POST = BLOCKS.register("stripped_cherry_post",
            () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG).noOcclusion()));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
    DeferredBlock<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name , toReturn);
    return toReturn;
}

private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
    ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
}

public static void  register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
}}