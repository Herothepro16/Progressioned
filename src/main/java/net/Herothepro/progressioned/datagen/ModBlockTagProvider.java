package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Progressioned.MOD_ID, existingFileHelper);
    }

    private record MaterialBlock(String name, Supplier<? extends Block> block) {}

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
        var postsTag = tag(ModTags.Blocks.POSTS);

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if (entry.get() instanceof PostBlock postBlock) {
                String path = entry.getId().getPath();
                String woodBaseName = "oak";
                String modNamespace = Progressioned.MOD_ID;
                boolean matched = false;

                for (ModBlocks.WoodDefinition wood : ModBlocks.WOODS) {
                    boolean isSelf = wood.modId().equals(Progressioned.MOD_ID);
                    if (!isSelf && (ModList.get() != null && !ModList.get().isLoaded(wood.modId()))) {
                        continue;
                    }

                    String expectedPrefix = isSelf ? "" : wood.modId() + "_";
                    if (path.startsWith(expectedPrefix)) {
                        String namePart = path.substring(expectedPrefix.length());
                        if (namePart.equals(wood.woodName() + "_post") ||
                                (wood.hasStripped() && namePart.equals(wood.strippedWoodName() + "_post"))) {
                            modNamespace = wood.modId();
                            woodBaseName = wood.woodName();
                            matched = true;
                            break;
                        }
                    }
                }

                if (matched || modNamespace.equals(Progressioned.MOD_ID)) {
                    postsTag.addOptional(entry.getId());

                    String tagName = modNamespace.equals(Progressioned.MOD_ID)
                            ? woodBaseName + "_posts"
                            : modNamespace + "_" + woodBaseName + "_posts";

                    TagKey<Block> woodPostTag = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, tagName));
                    tag(woodPostTag).addOptional(entry.getId());
                }
            }
        }

        tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(ModTags.Blocks.POSTS)
                .addTag(BlockTags.LOGS)
                .add(ModBlocks.CHOPPING_BLOCK.get())
                .add(ModBlocks.CAMPFIRE_BASE.get())
                .add(ModBlocks.WORKSTONE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PORCELAIN_KILN.get())
                .add(ModBlocks.PORCELAIN_MELTER.get())
                .add(ModBlocks.PORCELAIN_HEATER.get())
                .add(ModBlocks.PORCELAIN_FAUCET.get())
                .add(ModBlocks.PORCELAIN_CASTING_TABLE.get())
                .add(ModBlocks.PORCELAIN_CASTING_BASIN.get())
                .add(ModBlocks.WORKSTONE.get())
                .add(ModBlocks.PORCELAIN_BRICKS.get())
                .add(ModBlocks.PORCELAIN_BRICK_SLAB.get())
                .add(ModBlocks.PORCELAIN_BRICK_STAIRS.get())
                .add(ModBlocks.PORCELAIN_BRICK_WALL.get())
                .add(ModBlocks.CHARCOAL_BLOCK.get())
                .add(ModBlocks.TIN_BLOCK.get())
                .add(ModBlocks.SILVER_BLOCK.get())
                .add(ModBlocks.BRONZE_BLOCK.get())
                .add(ModBlocks.ROSE_GOLD_BLOCK.get())
                .add(ModBlocks.RAW_TIN_BLOCK.get())
                .add(ModBlocks.RAW_SILVER_BLOCK.get())
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

        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        tag(ModTags.Blocks.INCORRECT_FOR_FLINT_TOOL)
                .addTag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        tag(ModTags.Blocks.INCORRECT_FOR_BONE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES);

        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.Blocks.TIN_ORES)
                .addTag(ModTags.Blocks.SILVER_ORES)
                .addTag(BlockTags.IRON_ORES);

        tag(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL)
                .addTag(ModTags.Blocks.SILVER_ORES)
                .addTag(BlockTags.IRON_ORES)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL)
                .addTag(BlockTags.DIAMOND_ORES)
                .addTag(BlockTags.GOLD_ORES)
                .addTag(Tags.Blocks.ORES_DIAMOND)
                .addTag(Tags.Blocks.ORES_GOLD)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_ROSE_GOLD_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.KILN_HEATING_BLOCK)
                .add(Blocks.FIRE);
    }
}