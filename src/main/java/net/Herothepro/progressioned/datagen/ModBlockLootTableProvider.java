package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.CHOPPING_BLOCK.get());

        dropSelf(ModBlocks.WORKSTONE.get());

        dropSelf(ModBlocks.CAMPFIRE_BASE.get());

        dropSelf(ModBlocks.PORCELAIN_KILN.get());

        dropSelf(ModBlocks.PORCELAIN_MELTER.get());
        dropSelf(ModBlocks.PORCELAIN_HEATER.get());
        dropSelf(ModBlocks.PORCELAIN_FAUCET.get());
        dropSelf(ModBlocks.PORCELAIN_CASTING_TABLE.get());
        dropSelf(ModBlocks.PORCELAIN_CASTING_BASIN.get());

        dropSelf(ModBlocks.CHARCOAL_BLOCK.get());

        dropSelf(ModBlocks.THATCH.get());
        add(ModBlocks.THATCH_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.THATCH_SLAB.get()));

        dropSelf(ModBlocks.PORCELAIN_BRICKS.get());
        add(ModBlocks.PORCELAIN_BRICK_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.PORCELAIN_BRICK_SLAB.get()));
        dropSelf(ModBlocks.PORCELAIN_BRICK_STAIRS.get());
        dropSelf(ModBlocks.PORCELAIN_BRICK_WALL.get());

        dropSelf(ModBlocks.TIN_BLOCK.get());
        dropSelf(ModBlocks.SILVER_BLOCK.get());
        dropSelf(ModBlocks.BRONZE_BLOCK.get());
        dropSelf(ModBlocks.ROSE_GOLD_BLOCK.get());

        add(ModBlocks.TIN_ORE.get(),
                block -> createOreDrop(ModBlocks.TIN_ORE.get(), ModItems.RAW_TIN.get()));
        add(ModBlocks.DEEPSLATE_TIN_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_TIN_ORE.get(), ModItems.RAW_TIN.get()));
        dropSelf(ModBlocks.RAW_TIN_BLOCK.get());

        add(ModBlocks.SILVER_ORE.get(),
                block -> createOreDrop(ModBlocks.SILVER_ORE.get(), ModItems.RAW_SILVER.get()));
        add(ModBlocks.DEEPSLATE_SILVER_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModItems.RAW_SILVER.get()));
        dropSelf(ModBlocks.RAW_SILVER_BLOCK.get());

        dropSelf(ModBlocks.OAK_POST.get());
        dropSelf(ModBlocks.BIRCH_POST.get());
        dropSelf(ModBlocks.SPRUCE_POST.get());
        dropSelf(ModBlocks.JUNGLE_POST.get());
        dropSelf(ModBlocks.DARK_OAK_POST.get());
        dropSelf(ModBlocks.MANGROVE_POST.get());
        dropSelf(ModBlocks.CHERRY_POST.get());
        dropSelf(ModBlocks.ACACIA_POST.get());
        dropSelf(ModBlocks.STRIPPED_OAK_POST.get());
        dropSelf(ModBlocks.STRIPPED_BIRCH_POST.get());
        dropSelf(ModBlocks.STRIPPED_SPRUCE_POST.get());
        dropSelf(ModBlocks.STRIPPED_JUNGLE_POST.get());
        dropSelf(ModBlocks.STRIPPED_DARK_OAK_POST.get());
        dropSelf(ModBlocks.STRIPPED_MANGROVE_POST.get());
        dropSelf(ModBlocks.STRIPPED_CHERRY_POST.get());
        dropSelf(ModBlocks.STRIPPED_ACACIA_POST.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}