package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.block.ModBlocks;
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
        dropSelf(ModBlocks.CAMPFIRE_BASE.get());
        dropSelf(ModBlocks.PORCELAIN_KILN.get());
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