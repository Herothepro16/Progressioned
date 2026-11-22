package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
import net.Herothepro.progressioned.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.CHARCOAL_BLOCK.get());
        this.add(ModBlocks.SALT_BLOCK.get(), block ->
                this.createSilkTouchDispatchTable(block,
                        this.applyExplosionDecay(block,
                                LootItem.lootTableItem(ModItems.SALT.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                        )
                )
        );
        dropSelf(ModBlocks.THATCH.get());
        this.add(ModBlocks.THATCH_SLAB.get(), block -> createSlabItemTable(ModBlocks.THATCH_SLAB.get()));

        dropSelf(ModBlocks.PORCELAIN_BRICKS.get());
        this.add(ModBlocks.PORCELAIN_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.PORCELAIN_BRICK_SLAB.get()));
        dropSelf(ModBlocks.PORCELAIN_BRICK_STAIRS.get());
        dropSelf(ModBlocks.PORCELAIN_BRICK_WALL.get());

        dropSelf(ModBlocks.CHOPPING_BLOCK.get());
        dropSelf(ModBlocks.WORKSTONE.get());
        dropSelf(ModBlocks.CAMPFIRE_BASE.get());
        dropSelf(ModBlocks.PORCELAIN_KILN.get());
        dropSelf(ModBlocks.PORCELAIN_MELTER.get());
        dropSelf(ModBlocks.PORCELAIN_HEATER.get());
        dropSelf(ModBlocks.PORCELAIN_FAUCET.get());
        dropSelf(ModBlocks.PORCELAIN_CASTING_TABLE.get());
        dropSelf(ModBlocks.PORCELAIN_CASTING_BASIN.get());

        dropSelf(ModBlocks.OAK_DRYING_RACK.get());
        dropSelf(ModBlocks.SPRUCE_DRYING_RACK.get());
        dropSelf(ModBlocks.BIRCH_DRYING_RACK.get());
        dropSelf(ModBlocks.JUNGLE_DRYING_RACK.get());
        dropSelf(ModBlocks.ACACIA_DRYING_RACK.get());
        dropSelf(ModBlocks.DARK_OAK_DRYING_RACK.get());
        dropSelf(ModBlocks.MANGROVE_DRYING_RACK.get());
        dropSelf(ModBlocks.CHERRY_DRYING_RACK.get());

        dropSelf(ModBlocks.TIN_BLOCK.get());
        dropSelf(ModBlocks.SILVER_BLOCK.get());
        dropSelf(ModBlocks.BRONZE_BLOCK.get());
        dropSelf(ModBlocks.ROSE_GOLD_BLOCK.get());

        this.add(ModBlocks.TIN_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TIN.get()));
        this.add(ModBlocks.DEEPSLATE_TIN_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TIN.get()));
        dropSelf(ModBlocks.RAW_TIN_BLOCK.get());

        this.add(ModBlocks.SILVER_ORE.get(), block -> createOreDrop(block, ModItems.RAW_SILVER.get()));
        this.add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), block -> createOreDrop(block, ModItems.RAW_SILVER.get()));
        dropSelf(ModBlocks.RAW_SILVER_BLOCK.get());

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if (entry.get() instanceof PostBlock postBlock) {
                dropSelf(postBlock);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(entry -> (Block) entry.get())
                .filter(block -> block != null && block != Blocks.AIR)
                .collect(Collectors.toList());
    }
}