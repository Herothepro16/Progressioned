package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CHARCOAL_BLOCK);

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

        postBlock(ModBlocks.OAK_POST);
        postBlock(ModBlocks.SPRUCE_POST);
        postBlock(ModBlocks.BIRCH_POST);
        postBlock(ModBlocks.JUNGLE_POST);
        postBlock(ModBlocks.ACACIA_POST);
        postBlock(ModBlocks.DARK_OAK_POST);
        postBlock(ModBlocks.MANGROVE_POST);
        postBlock(ModBlocks.CHERRY_POST);
        postBlock(ModBlocks.STRIPPED_OAK_POST);
        postBlock(ModBlocks.STRIPPED_SPRUCE_POST);
        postBlock(ModBlocks.STRIPPED_BIRCH_POST);
        postBlock(ModBlocks.STRIPPED_JUNGLE_POST);
        postBlock(ModBlocks.STRIPPED_ACACIA_POST);
        postBlock(ModBlocks.STRIPPED_DARK_OAK_POST);
        postBlock(ModBlocks.STRIPPED_MANGROVE_POST);
        postBlock(ModBlocks.STRIPPED_CHERRY_POST);

        horizontalBlock(ModBlocks.CAMPFIRE_BASE.get(),
                models().getExistingFile(modLoc("block/campfire_base")));

        getVariantBuilder(ModBlocks.PORCELAIN_KILN.get())
                .forAllStates(state -> {
                    net.minecraft.core.Direction facing = state.getValue(net.Herothepro.progressioned.block.custom.KilnBlock.FACING);
                    boolean lit = state.getValue(net.Herothepro.progressioned.block.custom.KilnBlock.LIT);

                    int yRot = switch (facing) {case SOUTH -> 180;case EAST  -> 90;case WEST  -> 270;default    -> 0;};

                    String modelName = lit ? "porcelain_kiln_on" : "porcelain_kiln";

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/" + modelName)))
                            .rotationY(yRot)
                            .build();
                });
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("progressioned:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("progressioned:block/" + deferredBlock.getId().getPath() + appendix));
    }
    private void postBlock(DeferredBlock<PostBlock> block) {
        String name = block.getId().getPath();
        this.axisBlock((RotatedPillarBlock) block.get(),
                models().getExistingFile(modLoc("block/" + name)),
                models().getExistingFile(modLoc("block/" + name)));
    }
}
