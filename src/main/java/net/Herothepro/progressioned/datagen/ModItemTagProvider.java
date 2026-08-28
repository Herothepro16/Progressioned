package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
                .add(ModItems.COPPER_SAW.get())
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
                .add(ModItems.COPPER_SWORD.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.IRON_KNIFE.get())
                .add(ModItems.DIAMOND_KNIFE.get());

        tag(ItemTags.HOES)
                .add(ModItems.COPPER_HOE.get());

        tag(ModTags.Items.KNIVES)
                .add(ModItems.FLINT_KNIFE.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.IRON_KNIFE.get())
                .add(ModItems.DIAMOND_KNIFE.get());

        tag(ModTags.Items.SAWS)
                .add(ModItems.COPPER_SAW.get())
                .add(ModItems.IRON_SAW.get())
                .add(ModItems.DIAMOND_SAW.get());

        tag(ModTags.Items.HAMMERS)
                .add(ModItems.COPPER_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get());

        tag(ModTags.Items.POSTS)
                .add(ModItems.OAK_POST.get())
                .add(ModItems.SPRUCE_POST.get())
                .add(ModItems.BIRCH_POST.get())
                .add(ModItems.JUNGLE_POST.get())
                .add(ModItems.DARK_OAK_POST.get())
                .add(ModItems.CHERRY_POST.get())
                .add(ModItems.MANGROVE_POST.get())
                .add(ModItems.ACACIA_POST.get())
                .add(ModItems.STRIPPED_OAK_POST.get())
                .add(ModItems.STRIPPED_SPRUCE_POST.get())
                .add(ModItems.STRIPPED_BIRCH_POST.get())
                .add(ModItems.STRIPPED_JUNGLE_POST.get())
                .add(ModItems.STRIPPED_DARK_OAK_POST.get())
                .add(ModItems.STRIPPED_CHERRY_POST.get())
                .add(ModItems.STRIPPED_MANGROVE_POST.get())
                .add(ModItems.STRIPPED_ACACIA_POST.get());

        TagKey<net.minecraft.world.item.Item> cToolsKnives = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/knife"));
        TagKey<net.minecraft.world.item.Item> cToolsSaws = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/saw"));
        TagKey<net.minecraft.world.item.Item> cToolsHammers = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/hammer"));
        TagKey<net.minecraft.world.item.Item> cPlatesCopper = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plates/copper"));
        TagKey<net.minecraft.world.item.Item> cPlatesIron = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plates/iron"));
        TagKey<net.minecraft.world.item.Item> cPlatesGold = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plates/gold"));

        tag(cToolsKnives).addTag(ModTags.Items.KNIVES);
        tag(cToolsSaws).addTag(ModTags.Items.SAWS);
        tag(cToolsHammers).addTag(ModTags.Items.HAMMERS);

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "buckets")))
                .add(ModItems.CLAY_BUCKET.get())
                .add(ModItems.CLAY_WATER_BUCKET.get())
                .add(ModItems.CLAY_LAVA_BUCKET.get());

        tag(cPlatesCopper).add(ModItems.COPPER_PLATE.get());
        tag(cPlatesIron).add(ModItems.IRON_PLATE.get());
        tag(cPlatesGold).add(ModItems.GOLD_PLATE.get());

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plates")))
                .addTag(cPlatesCopper)
                .addTag(cPlatesIron)
                .addTag(cPlatesGold);
    }
}