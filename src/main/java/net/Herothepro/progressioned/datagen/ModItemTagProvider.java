package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.block.custom.PostBlock;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Progressioned.MOD_ID, existingFileHelper);
    }

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
        var itemPostsTag = tag(ModTags.Items.POSTS);

        for (DeferredHolder<Block, ? extends Block> entry : ModBlocks.BLOCKS.getEntries()) {
            if (entry.get() instanceof PostBlock postBlock) {
                String path = entry.getId().getPath();
                String woodBaseName = "oak";
                String modNamespace = Progressioned.MOD_ID;
                boolean matched = false;

                for (ModBlocks.WoodDefinition wood : ModBlocks.WOODS) {
                    if (!wood.modId().equals(Progressioned.MOD_ID) && !net.neoforged.fml.ModList.get().isLoaded(wood.modId())) {
                        continue;
                    }

                    String expectedPrefix = wood.modId().equals(Progressioned.MOD_ID) ? "" : wood.modId() + "_";
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
                    itemPostsTag.addOptional(entry.getId());

                    String tagName = modNamespace.equals(Progressioned.MOD_ID)
                            ? woodBaseName + "_posts"
                            : modNamespace + "_" + woodBaseName + "_posts";

                    TagKey<Item> itemWoodPostTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, tagName));
                    tag(itemWoodPostTag).addOptional(entry.getId());
                }
            }
        }

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
                .add(ModItems.STONE_HAMMER.get())
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
    }
}