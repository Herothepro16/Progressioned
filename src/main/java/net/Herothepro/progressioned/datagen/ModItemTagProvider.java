package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Progressioned.MOD_ID, existingFileHelper);
    }

    record WoodVariant(String name, Block post, Block strippedPost) {}

    private record MaterialItem(String name, Supplier<? extends Item> item) {}

    private static TagKey<Item> commonTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private void materialTags(String category, MaterialItem... materials) {
        var general = tag(commonTag(category));
        for (MaterialItem material : materials) {
            TagKey<Item> specific = commonTag(category + "/" + material.name());
            tag(specific).add(material.item().get());
            general.addTag(specific);
        }
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ItemTags.PICKAXES)
                .add(ModItems.FLINT_PICKAXE.get())
                .add(ModItems.BONE_PICKAXE.get())
                .add(ModItems.AMETHYST_BONE_PICKAXE.get())
                .add(ModItems.GOLDEN_BONE_PICKAXE.get())
                .add(ModItems.COPPER_PICKAXE.get())
                .add(ModItems.BRONZE_PICKAXE.get())
                .add(ModItems.ROSE_GOLD_PICKAXE.get());

        tag(ItemTags.AXES)
                .add(ModItems.FLINT_AXE.get())
                .add(ModItems.BONE_AXE.get())
                .add(ModItems.AMETHYST_BONE_AXE.get())
                .add(ModItems.GOLDEN_BONE_AXE.get())
                .add(ModItems.COPPER_AXE.get())
                .add(ModItems.BRONZE_AXE.get())
                .add(ModItems.ROSE_GOLD_AXE.get())
                .add(ModItems.COPPER_SAW.get())
                .add(ModItems.IRON_SAW.get())
                .add(ModItems.DIAMOND_SAW.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.FLINT_SHOVEL.get())
                .add(ModItems.COPPER_SHOVEL.get())
                .add(ModItems.BRONZE_SHOVEL.get())
                .add(ModItems.ROSE_GOLD_SHOVEL.get());

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
                .add(ModItems.BRONZE_SWORD.get())
                .add(ModItems.ROSE_GOLD_SWORD.get())
                .add(ModItems.COPPER_KNIFE.get())
                .add(ModItems.IRON_KNIFE.get())
                .add(ModItems.DIAMOND_KNIFE.get());

        tag(ItemTags.HOES)
                .add(ModItems.FLINT_HOE.get())
                .add(ModItems.COPPER_HOE.get())
                .add(ModItems.BRONZE_HOE.get())
                .add(ModItems.ROSE_GOLD_HOE.get());

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

        tag(commonTag("tools/knife")).addTag(ModTags.Items.KNIVES);
        tag(commonTag("tools/saw")).addTag(ModTags.Items.SAWS);
        tag(commonTag("tools/hammer")).addTag(ModTags.Items.HAMMERS);

        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.BRONZE_HELMET.get())
                .add(ModItems.ROSE_GOLD_HELMET.get());

        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.BRONZE_CHESTPLATE.get())
                .add(ModItems.ROSE_GOLD_CHESTPLATE.get());

        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.BRONZE_LEGGINGS.get())
                .add(ModItems.ROSE_GOLD_LEGGINGS.get());

        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.BRONZE_BOOTS.get())
                .add(ModItems.ROSE_GOLD_BOOTS.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BRONZE_HELMET.get())
                .add(ModItems.BRONZE_CHESTPLATE.get())
                .add(ModItems.BRONZE_LEGGINGS.get())
                .add(ModItems.BRONZE_BOOTS.get())
                .add(ModItems.ROSE_GOLD_HELMET.get())
                .add(ModItems.ROSE_GOLD_CHESTPLATE.get())
                .add(ModItems.ROSE_GOLD_LEGGINGS.get())
                .add(ModItems.ROSE_GOLD_BOOTS.get());

        materialTags("plates",
                new MaterialItem("copper", ModItems.COPPER_PLATE),
                new MaterialItem("iron", ModItems.IRON_PLATE),
                new MaterialItem("gold", ModItems.GOLD_PLATE),
                new MaterialItem("silver", ModItems.SILVER_PLATE),
                new MaterialItem("bronze", ModItems.BRONZE_PLATE),
                new MaterialItem("rose_gold", ModItems.ROSE_GOLD_PLATE));

        materialTags("dusts",
                new MaterialItem("copper", ModItems.COPPER_DUST),
                new MaterialItem("gold", ModItems.GOLD_DUST),
                new MaterialItem("tin", ModItems.TIN_DUST),
                new MaterialItem("silver", ModItems.SILVER_DUST),
                new MaterialItem("bronze", ModItems.BRONZE_DUST),
                new MaterialItem("rose_gold", ModItems.ROSE_GOLD_DUST));

        materialTags("ingots",
                new MaterialItem("tin", ModItems.TIN_INGOT),
                new MaterialItem("silver", ModItems.SILVER_INGOT),
                new MaterialItem("bronze", ModItems.BRONZE_INGOT),
                new MaterialItem("rose_gold", ModItems.ROSE_GOLD_INGOT));

        materialTags("raw_materials",
                new MaterialItem("tin", ModItems.RAW_TIN),
                new MaterialItem("silver", ModItems.RAW_SILVER));

        tag(commonTag("buckets"))
                .add(ModItems.CLAY_BUCKET.get())
                .add(ModItems.CLAY_WATER_BUCKET.get())
                .add(ModItems.CLAY_LAVA_BUCKET.get());

        tag(commonTag("bricks"))
                .add(ModItems.PORCELAIN_CLAY_BRICK.get())
                .add(ModItems.MUD_BRICK.get());

        WoodVariant[] variants = {
                new WoodVariant("oak", ModBlocks.OAK_POST.get(), ModBlocks.STRIPPED_OAK_POST.get()),
                new WoodVariant("spruce", ModBlocks.SPRUCE_POST.get(), ModBlocks.STRIPPED_SPRUCE_POST.get()),
                new WoodVariant("birch", ModBlocks.BIRCH_POST.get(), ModBlocks.STRIPPED_BIRCH_POST.get()),
                new WoodVariant("jungle", ModBlocks.JUNGLE_POST.get(), ModBlocks.STRIPPED_JUNGLE_POST.get()),
                new WoodVariant("acacia", ModBlocks.ACACIA_POST.get(), ModBlocks.STRIPPED_ACACIA_POST.get()),
                new WoodVariant("dark_oak", ModBlocks.DARK_OAK_POST.get(), ModBlocks.STRIPPED_DARK_OAK_POST.get()),
                new WoodVariant("mangrove", ModBlocks.MANGROVE_POST.get(), ModBlocks.STRIPPED_MANGROVE_POST.get()),
                new WoodVariant("cherry", ModBlocks.CHERRY_POST.get(), ModBlocks.STRIPPED_CHERRY_POST.get()),
        };

        var postsTagBuilder = tag(ModTags.Items.POSTS);

        for (WoodVariant v : variants) {
            TagKey<Item> postTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, v.name() + "_posts"));
            tag(postTag)
                    .add(v.post().asItem())
                    .add(v.strippedPost().asItem());

            postsTagBuilder.addTag(postTag);
        }
    }
}