package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(ModTags.Blocks.POSTS)
                .addTag(BlockTags.LOGS)
                .add(ModBlocks.CHOPPING_BLOCK.get())
                .add(ModBlocks.CAMPFIRE_BASE.get())
                .add(ModBlocks.WORKSTONE.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.POSTS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PORCELAIN_KILN.get())
                .add(ModBlocks.CHARCOAL_BLOCK.get())
                .add(ModBlocks.PORCELAIN_BRICKS.get())
                .add(ModBlocks.PORCELAIN_BRICK_SLAB.get())
                .add(ModBlocks.PORCELAIN_BRICK_STAIRS.get())
                .add(ModBlocks.PORCELAIN_BRICK_WALL.get())
                .add(ModBlocks.WORKSTONE.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.THATCH.get())
                .add(ModBlocks.THATCH_SLAB.get());

        tag(ModTags.Blocks.NEEDS_FLINT_TOOL)
                .addTag(BlockTags.LOGS);

        tag(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(BlockTags.INFINIBURN_OVERWORLD)
                .add(Blocks.COAL_BLOCK)
                .add(ModBlocks.CHARCOAL_BLOCK.get());

        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(BlockTags.IRON_ORES);

        tag(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .addTag(BlockTags.IRON_ORES);

        tag(BlockTags.SLABS)
                .add(ModBlocks.PORCELAIN_BRICK_SLAB.get());
        tag(BlockTags.STAIRS)
                .add(ModBlocks.PORCELAIN_BRICK_STAIRS.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.PORCELAIN_BRICK_WALL.get());

        tag(ModTags.Blocks.POSTS)
                .add(ModBlocks.OAK_POST.get())
                .add(ModBlocks.SPRUCE_POST.get())
                .add(ModBlocks.BIRCH_POST.get())
                .add(ModBlocks.JUNGLE_POST.get())
                .add(ModBlocks.DARK_OAK_POST.get())
                .add(ModBlocks.CHERRY_POST.get())
                .add(ModBlocks.MANGROVE_POST.get())
                .add(ModBlocks.ACACIA_POST.get())
                .add(ModBlocks.STRIPPED_OAK_POST.get())
                .add(ModBlocks.STRIPPED_SPRUCE_POST.get())
                .add(ModBlocks.STRIPPED_BIRCH_POST.get())
                .add(ModBlocks.STRIPPED_JUNGLE_POST.get())
                .add(ModBlocks.STRIPPED_DARK_OAK_POST.get())
                .add(ModBlocks.STRIPPED_CHERRY_POST.get())
                .add(ModBlocks.STRIPPED_MANGROVE_POST.get())
                .add(ModBlocks.STRIPPED_ACACIA_POST.get());
    }
}
