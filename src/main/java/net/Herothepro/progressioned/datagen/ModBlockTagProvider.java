package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Progressioned.MOD_ID, existingFileHelper);
    }

    private record MaterialBlock(String name, Supplier<? extends Block> block) {}

    private record PostVariant(TagKey<Block> tag, Supplier<? extends Block> post, Supplier<? extends Block> strippedPost) {}

    private static TagKey<Block> commonTag(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private void materialTags(String category, MaterialBlock... materials) {
        var general = tag(commonTag(category));
        for (MaterialBlock material : materials) {
            TagKey<Block> specific = commonTag(category + "/" + material.name());
            tag(specific).add(material.block().get());
            general.addTag(specific);
        }
    }

    private void oreTags(String material, TagKey<Block> modOres) {
        TagKey<Block> specific = commonTag("ores/" + material);
        tag(specific).addTag(modOres);
        tag(commonTag("ores")).addTag(specific);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(ModTags.Blocks.POSTS)
                .addTag(BlockTags.LOGS)
                .add(ModBlocks.CHOPPING_BLOCK.get())
                .add(ModBlocks.CAMPFIRE_BASE.get())
                .add(ModBlocks.WORKSTONE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                // machines
                .add(ModBlocks.PORCELAIN_KILN.get())
                .add(ModBlocks.PORCELAIN_MELTER.get())
                .add(ModBlocks.PORCELAIN_HEATER.get())
                .add(ModBlocks.PORCELAIN_FAUCET.get())
                .add(ModBlocks.PORCELAIN_CASTING_TABLE.get())
                .add(ModBlocks.PORCELAIN_CASTING_BASIN.get())
                .add(ModBlocks.WORKSTONE.get())
                // porcelain bricks
                .add(ModBlocks.PORCELAIN_BRICKS.get())
                .add(ModBlocks.PORCELAIN_BRICK_SLAB.get())
                .add(ModBlocks.PORCELAIN_BRICK_STAIRS.get())
                .add(ModBlocks.PORCELAIN_BRICK_WALL.get())
                // metal + storage blocks
                .add(ModBlocks.CHARCOAL_BLOCK.get())
                .add(ModBlocks.TIN_BLOCK.get())
                .add(ModBlocks.SILVER_BLOCK.get())
                .add(ModBlocks.BRONZE_BLOCK.get())
                .add(ModBlocks.ROSE_GOLD_BLOCK.get())
                .add(ModBlocks.RAW_TIN_BLOCK.get())
                .add(ModBlocks.RAW_SILVER_BLOCK.get())
                // ores
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.THATCH.get())
                .add(ModBlocks.THATCH_SLAB.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.POSTS);

        tag(BlockTags.INFINIBURN_OVERWORLD)
                .add(Blocks.COAL_BLOCK)
                .add(ModBlocks.CHARCOAL_BLOCK.get());

        tag(BlockTags.SLABS).add(ModBlocks.PORCELAIN_BRICK_SLAB.get());
        tag(BlockTags.STAIRS).add(ModBlocks.PORCELAIN_BRICK_STAIRS.get());
        tag(BlockTags.WALLS).add(ModBlocks.PORCELAIN_BRICK_WALL.get());

        tag(ModTags.Blocks.TIN_ORES)
                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.get());

        tag(ModTags.Blocks.SILVER_ORES)
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get());

        oreTags("tin", ModTags.Blocks.TIN_ORES);
        oreTags("silver", ModTags.Blocks.SILVER_ORES);

        materialTags("storage_blocks",
                new MaterialBlock("tin", ModBlocks.TIN_BLOCK),
                new MaterialBlock("silver", ModBlocks.SILVER_BLOCK),
                new MaterialBlock("bronze", ModBlocks.BRONZE_BLOCK),
                new MaterialBlock("rose_gold", ModBlocks.ROSE_GOLD_BLOCK),
                new MaterialBlock("raw_tin", ModBlocks.RAW_TIN_BLOCK),
                new MaterialBlock("raw_silver", ModBlocks.RAW_SILVER_BLOCK));

        tag(ModTags.Blocks.NEEDS_FLINT_TOOL)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.PLANKS)
                .add(Blocks.CLAY);

        tag(ModTags.Blocks.NEEDS_BRONZE_TOOL)
                .addTag(BlockTags.IRON_ORES);

        // Wooden & Golden : buns
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        // Flint : cant mine wood+
        tag(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        // Bone : cant mine stone+
        tag(ModTags.Blocks.INCORRECT_FOR_BONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        // Stone : cant mine copper+
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES)
                .addTag(BlockTags.IRON_ORES);

        // Copper : can mine tin cant mine silver, iron ores or iron+
        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(ModTags.Blocks.SILVER_ORES)
                .addTag(BlockTags.IRON_ORES)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        // Bronze : everything iron can mine except diamond and gold ores
        tag(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL)
                .addTag(BlockTags.DIAMOND_ORES)
                .addTag(BlockTags.GOLD_ORES)
                .addTag(Tags.Blocks.ORES_DIAMOND)
                .addTag(Tags.Blocks.ORES_GOLD)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        // Rose gold : same as iron
        tag(ModTags.Blocks.INCORRECT_FOR_ROSE_GOLD_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        PostVariant[] posts = {
                new PostVariant(ModTags.Blocks.OAK_POSTS, ModBlocks.OAK_POST, ModBlocks.STRIPPED_OAK_POST),
                new PostVariant(ModTags.Blocks.SPRUCE_POSTS, ModBlocks.SPRUCE_POST, ModBlocks.STRIPPED_SPRUCE_POST),
                new PostVariant(ModTags.Blocks.BIRCH_POSTS, ModBlocks.BIRCH_POST, ModBlocks.STRIPPED_BIRCH_POST),
                new PostVariant(ModTags.Blocks.JUNGLE_POSTS, ModBlocks.JUNGLE_POST, ModBlocks.STRIPPED_JUNGLE_POST),
                new PostVariant(ModTags.Blocks.DARK_OAK_POSTS, ModBlocks.DARK_OAK_POST, ModBlocks.STRIPPED_DARK_OAK_POST),
                new PostVariant(ModTags.Blocks.ACACIA_POSTS, ModBlocks.ACACIA_POST, ModBlocks.STRIPPED_ACACIA_POST),
                new PostVariant(ModTags.Blocks.MANGROVE_POSTS, ModBlocks.MANGROVE_POST, ModBlocks.STRIPPED_MANGROVE_POST),
                new PostVariant(ModTags.Blocks.CHERRY_POSTS, ModBlocks.CHERRY_POST, ModBlocks.STRIPPED_CHERRY_POST),
        };

        var allPosts = tag(ModTags.Blocks.POSTS);
        for (PostVariant variant : posts) {
            tag(variant.tag())
                    .add(variant.post().get())
                    .add(variant.strippedPost().get());
            allPosts.addTag(variant.tag());
        }
    }
}