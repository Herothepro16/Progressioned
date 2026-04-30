package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
    // helper method
    private void postBlock(DeferredBlock<PostBlock> block) {
        String name = block.getId().getPath();
        this.axisBlock((RotatedPillarBlock) block.get(),
                models().getExistingFile(modLoc("block/" + name)),
                models().getExistingFile(modLoc("block/" + name)));
    }
}
