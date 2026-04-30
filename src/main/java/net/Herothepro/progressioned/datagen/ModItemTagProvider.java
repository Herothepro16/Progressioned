package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Progressioned.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.PICKAXES)
                .add(ModItems.FLINT_PICK.get())
                .add(ModItems.COPPER_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.FLINT_AXE.get())
                .add(ModItems.COPPER_AXE.get())

                .add(ModItems.FLINT_SAW.get())
                .add(ModItems.IRON_SAW.get())
                .add(ModItems.DIAMOND_SAW.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.FLINT_SHOVEL.get())
                .add(ModItems.COPPER_SHOVEL.get());
        tag(ItemTags.SWORDS)
                .add(ModItems.FLINT_SPEAR.get())
                .add(ModItems.FLINT_KNIFE.get())
                .add(ModItems.BONE_SWORD.get())
                .add(ModItems.BONE_SPEAR.get())
                .add(ModItems.AMETHYST_BONE_SWORD.get())
                .add(ModItems.AMETHYST_BONE_SPEAR.get())
                .add(ModItems.GOLDEN_BONE_SWORD.get())
                .add(ModItems.GOLDEN_BONE_SPEAR.get())
                .add(ModItems.BONE_CLUB.get())
                .add(ModItems.COPPER_SPEAR.get())
                .add(ModItems.COPPER_SWORD.get());
        tag(ItemTags.HOES)
                .add(ModItems.COPPER_HOE.get());
        tag(ModTags.Items.SAWS)
                .add(ModItems.FLINT_SAW.get())
                .add(ModItems.IRON_SAW.get())
                .add(ModItems.DIAMOND_SAW.get())
                .addOptionalTag(ResourceLocation.fromNamespaceAndPath("c", "saws"));
    }
}
