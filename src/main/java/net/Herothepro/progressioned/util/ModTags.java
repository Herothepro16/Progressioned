package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.Progressioned;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> NEEDS_FLINT_TOOL = createTag("needs_flint_tool");
        public static final TagKey<Block> INCORRECT_FOR_FLINT_TOOL = createTag("incorrect_for_flint_tool");

        public static final TagKey<Block> NEEDS_BONE_TOOL = createTag("needs_bone_tool");
        public static final TagKey<Block> INCORRECT_FOR_BONE_TOOL = createTag("incorrect_for_bone_tool");

        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag("needs_copper_tool");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createTag("incorrect_for_copper_tool");

        public static final TagKey<Block> NEEDS_BRONZE_TOOL = createTag("needs_bronze_tool");
        public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = createTag("incorrect_for_bronze_tool");

        public static final TagKey<Block> NEEDS_ROSE_GOLD_TOOL = createTag("needs_rose_gold_tool");
        public static final TagKey<Block> INCORRECT_FOR_ROSE_GOLD_TOOL = createTag("incorrect_for_rose_gold_tool");

        public static final TagKey<Block> TIN_ORES = createTag("tin_ores");
        public static final TagKey<Block> SILVER_ORES = createTag("silver_ores");

        public static final TagKey<Block> OAK_POSTS = createTag("oak_posts");
        public static final TagKey<Block> SPRUCE_POSTS = createTag("spruce_posts");
        public static final TagKey<Block> BIRCH_POSTS = createTag("birch_posts");
        public static final TagKey<Block> JUNGLE_POSTS = createTag("jungle_posts");
        public static final TagKey<Block> ACACIA_POSTS = createTag("acacia_posts");
        public static final TagKey<Block> DARK_OAK_POSTS = createTag("dark_oak_posts");
        public static final TagKey<Block> MANGROVE_POSTS = createTag("mangrove_posts");
        public static final TagKey<Block> CHERRY_POSTS = createTag("cherry_posts");

        public static final TagKey<Block> POSTS = createTag("posts");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> KNIVES = createTag("knives");

        public static final TagKey<Item> SAWS = createTag("saws");

        public static final TagKey<Item> HAMMERS = createTag("hammers");

        public static final TagKey<Item> POSTS = createTag("posts");

        public static final TagKey<Item> COMMON_KNIVES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/knife"));

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, name));
        }

        private static TagKey<Item> createCommonTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}