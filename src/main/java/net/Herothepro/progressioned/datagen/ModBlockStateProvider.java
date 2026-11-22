package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CHARCOAL_BLOCK);
        blockWithItem(ModBlocks.SALT_BLOCK);
        blockWithItem(ModBlocks.PORCELAIN_BRICKS);

        slabBlock(ModBlocks.PORCELAIN_BRICK_SLAB.get(),
                blockTexture(ModBlocks.PORCELAIN_BRICKS.get()), blockTexture(ModBlocks.PORCELAIN_BRICKS.get()));
        blockItem(ModBlocks.PORCELAIN_BRICK_SLAB);

        stairsBlock(ModBlocks.PORCELAIN_BRICK_STAIRS.get(), blockTexture(ModBlocks.PORCELAIN_BRICKS.get()));
        blockItem(ModBlocks.PORCELAIN_BRICK_STAIRS);

        wallBlock(ModBlocks.PORCELAIN_BRICK_WALL.get(), blockTexture(ModBlocks.PORCELAIN_BRICKS.get()));

        blockWithItem(ModBlocks.TIN_BLOCK);
        blockWithItem(ModBlocks.SILVER_BLOCK);
        blockWithItem(ModBlocks.BRONZE_BLOCK);
        blockWithItem(ModBlocks.ROSE_GOLD_BLOCK);

        blockWithItem(ModBlocks.TIN_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_TIN_ORE);
        blockWithItem(ModBlocks.RAW_TIN_BLOCK);

        blockWithItem(ModBlocks.SILVER_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_SILVER_ORE);
        blockWithItem(ModBlocks.RAW_SILVER_BLOCK);

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if (entry.get() instanceof PostBlock postBlock) {
                String path = entry.getId().getPath();

                boolean isStripped = path.startsWith("stripped_") || path.contains("_stripped_");
                String logNamespace = Progressioned.MOD_ID;
                String cleanName;

                if (path.startsWith("biomesoplenty_") || path.contains("_biomesoplenty_")) {
                    logNamespace = "biomesoplenty";
                    cleanName = path.replace("biomesoplenty_", "").replace("stripped_", "").replace("_stripped_", "").replace("_post", "");
                } else if (path.startsWith("regions_unexplored_") || path.contains("_regions_unexplored_")) {
                    logNamespace = "regions_unexplored";
                    cleanName = path.replace("regions_unexplored_", "").replace("stripped_", "").replace("_stripped_", "").replace("_post", "");
                } else if (List.of("oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry").stream().anyMatch(path::contains)) {
                    logNamespace = "minecraft";
                    cleanName = path.replace("stripped_", "").replace("_post", "");
                } else {
                    cleanName = path.replace("stripped_", "").replace("_post", "");
                }

                String sideSuffix = "_log";
                if (cleanName.equals("alpha")) {
                    sideSuffix = "_log_side";
                }

                String logName = (isStripped ? "stripped_" : "") + cleanName + sideSuffix;
                String topLogName = (isStripped ? "stripped_" : "") + cleanName + "_log_top";

                ResourceLocation sideTex = ResourceLocation.fromNamespaceAndPath(logNamespace, "block/" + logName);
                ResourceLocation topTex = ResourceLocation.fromNamespaceAndPath(logNamespace, "block/" + topLogName);

                String templatePrefix = isStripped ? "block/templates/stripped_post_template_" : "block/templates/post_template_";

                ModelFile[][] models = new ModelFile[3][3];
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        var builder = models().withExistingParent(path + "_" + a + "_" + b, modLoc(templatePrefix + a + "_" + b))
                                .texture("0", sideTex)
                                .texture("particle", sideTex)
                                .ao(false);
                        if (isStripped) {
                            builder.texture("1", topTex);
                        } else {
                            builder.texture("2", topTex);
                        }
                        models[a][b] = builder;
                    }
                }

                getVariantBuilder(postBlock).forAllStatesExcept(state -> {
                    ModelFile model = models[state.getValue(PostBlock.POS_A)][state.getValue(PostBlock.POS_B)];
                    ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(model);
                    switch (state.getValue(RotatedPillarBlock.AXIS)) {
                        case X -> builder.rotationX(90).rotationY(90);
                        case Z -> builder.rotationX(90);
                        default -> { }
                    }
                    return builder.build();
                }, BlockStateProperties.WATERLOGGED);

                simpleBlockItem(postBlock, models[1][1]);
            }
        }
    }

    private void blockWithItem(DeferredHolder<Block, ? extends Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredHolder<Block, ? extends Block> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("progressioned:block/" + deferredBlock.getId().getPath()));
    }
}