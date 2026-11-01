package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.fluid.FluidHolder;
import net.Herothepro.progressioned.fluid.ModFluids;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.recipe.CastingRecipe;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.DamagingShapelessRecipe;
import net.Herothepro.progressioned.recipe.MeltingRecipe;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    record ShapedToolSpec(
            String name,
            ItemLike result,
            ItemLike head,
            ItemLike binding
    ) {}

    record CastingSpec(
            String name,
            ItemLike result,
            ItemLike mold,
            net.Herothepro.progressioned.fluid.FluidHolder<net.neoforged.neoforge.fluids.FluidType> fluid,
            int fluidAmount,
            int coolingTime,
            boolean consumeCast
    ) {}

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Flint tool recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICKAXE)
                .pattern("CFC").pattern(" SP").pattern(" S ")
                .define('F', ModItems.FLINT_SHARD).define('C', ModItems.FLINT_CHIP)
                .define('S', Items.STICK).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE)
                .pattern("FC ").pattern("PS ").pattern("   ")
                .define('F', ModItems.FLINT_SHARD).define('C', ModItems.FLINT_CHIP)
                .define('S', Items.STICK).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL)
                .pattern(" F ").pattern(" SP").pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get()).define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_HOE.get())
                .pattern("CF ").pattern("PS ").pattern(" S ")
                .define('F', ModItems.FLINT_SHARD).define('C', ModItems.FLINT_CHIP)
                .define('S', Items.STICK).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_KNIFE)
                .pattern(" C ").pattern("S  ").pattern("   ")
                .define('C', ModItems.FLINT_CHIP).define('S', Items.STICK)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SPEAR)
                .pattern("  C").pattern(" SP").pattern("S  ")
                .define('C', ModItems.FLINT_CHIP).define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);

        // Random stuff
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIBER_MESH)
                .pattern("SF ").pattern("FS ").pattern("   ")
                .define('S', Items.STICK).define('F', ModItems.PLANT_STRING)
                .unlockedBy("has_plant_string", has(ModItems.PLANT_STRING)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_STRING)
                .requires(ModItems.PLANT_FIBER, 3)
                .unlockedBy("has_plant_fiber", has(ModItems.PLANT_FIBER)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FIRE_STICK)
                .requires(Items.STICK, 2)
                .unlockedBy("has_sticks", has(Items.STICK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_DUST.get(), 4)
                .requires(ModItems.TIN_DUST)
                .requires(ModItems.COPPER_DUST,3)
                .unlockedBy("has_tin_dust", has(ModItems.TIN_DUST.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROSE_GOLD_DUST.get(), 4)
                .requires(ModItems.GOLD_DUST,3)
                .requires(ModItems.COPPER_DUST,1)
                .requires(ModItems.SILVER_DUST,1)
                .unlockedBy("has_silver_dust", has(ModItems.SILVER_DUST.get())).save(recipeOutput);

        // Nuggetifying
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_NUGGET,9)
                .requires(ModItems.TIN_INGOT)
                .unlockedBy("has_tin_ingot", has(ModItems.TIN_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER_NUGGET,9)
                .requires(ModItems.SILVER_INGOT)
                .unlockedBy("has_silver_ingot", has(ModItems.SILVER_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_NUGGET,9)
                .requires(ModItems.BRONZE_INGOT)
                .unlockedBy("has_bronze_ingot", has(ModItems.BRONZE_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROSE_GOLD_NUGGET,9)
                .requires(ModItems.ROSE_GOLD_INGOT)
                .unlockedBy("has_rose_gold_ingot", has(ModItems.ROSE_GOLD_INGOT)).save(recipeOutput);

        // De-Nuggetifying
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_INGOT)
                .requires(ModItems.TIN_NUGGET, 9)
                .unlockedBy("has_tin_nugget", has(ModItems.TIN_NUGGET))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "tin_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER_INGOT)
                .requires(ModItems.SILVER_NUGGET, 9)
                .unlockedBy("has_silver_nugget", has(ModItems.SILVER_NUGGET))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "silver_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_INGOT)
                .requires(ModItems.BRONZE_NUGGET, 9)
                .unlockedBy("has_bronze_nugget", has(ModItems.BRONZE_NUGGET))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "bronze_ingot_from_nuggets"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT)
                .requires(ModItems.ROSE_GOLD_NUGGET, 9)
                .unlockedBy("has_rose_gold_nugget", has(ModItems.ROSE_GOLD_NUGGET))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "rose_gold_ingot_from_nuggets"));

        // Block Crafting
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.CHARCOAL_BLOCK)
                .requires(Items.CHARCOAL, 9)
                .unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL , 9)
                .requires(ModBlocks.CHARCOAL_BLOCK, 1)
                .unlockedBy("has_charcoal_block", has(ModBlocks.CHARCOAL_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.TIN_BLOCK)
                .requires(ModItems.TIN_INGOT, 9)
                .unlockedBy("has_tin_ingot", has(ModItems.TIN_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_INGOT , 9)
                .requires(ModBlocks.TIN_BLOCK, 1)
                .unlockedBy("has_tin_block", has(ModBlocks.TIN_BLOCK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.RAW_TIN_BLOCK)
                .requires(ModItems.RAW_TIN, 9)
                .unlockedBy("has_raw_tin", has(ModItems.RAW_TIN)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_TIN , 9)
                .requires(ModBlocks.RAW_TIN_BLOCK, 1)
                .unlockedBy("has_raw_tin_block", has(ModBlocks.RAW_TIN_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SILVER_BLOCK)
                .requires(ModItems.SILVER_INGOT, 9)
                .unlockedBy("has_silver_ingot", has(ModItems.SILVER_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILVER_INGOT , 9)
                .requires(ModBlocks.SILVER_BLOCK, 1)
                .unlockedBy("has_silver_block", has(ModBlocks.SILVER_BLOCK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.RAW_SILVER_BLOCK)
                .requires(ModItems.RAW_SILVER, 9)
                .unlockedBy("has_raw_silver", has(ModItems.RAW_SILVER)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_SILVER , 9)
                .requires(ModBlocks.RAW_SILVER_BLOCK, 1)
                .unlockedBy("has_raw_silver_block", has(ModBlocks.RAW_SILVER_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.BRONZE_BLOCK)
                .requires(ModItems.BRONZE_INGOT, 9)
                .unlockedBy("has_bronze_ingot", has(ModItems.BRONZE_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_INGOT , 9)
                .requires(ModBlocks.BRONZE_BLOCK, 1)
                .unlockedBy("has_bronze_block", has(ModBlocks.BRONZE_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ROSE_GOLD_BLOCK)
                .requires(ModItems.ROSE_GOLD_INGOT, 9)
                .unlockedBy("has_rose_gold_ingot", has(ModItems.ROSE_GOLD_INGOT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT , 9)
                .requires(ModBlocks.ROSE_GOLD_BLOCK, 1)
                .unlockedBy("has_rose_gold_block", has(ModBlocks.ROSE_GOLD_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.MUD)
                .requires(ModItems.MUD_BALL, 4)
                .unlockedBy("has_mud_ball", has(ModItems.MUD_BALL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MUD_BALL , 4)
                .requires(Blocks.MUD, 1)
                .unlockedBy("has_mud", has(Blocks.MUD)).save(recipeOutput);

        damagingShapelessRecipe(recipeOutput, "straw_cord",
                new ItemStack(ModItems.STRAW_CORD.get(), 1),
                List.of(Ingredient.of(ModItems.STRAW)),
                Ingredient.of(ModTags.Items.KNIVES), 1);

        damagingShapelessRecipe(recipeOutput, "leather_cord",
                new ItemStack(ModItems.LEATHER_CORD.get(), 2),
                List.of(Ingredient.of(Items.LEATHER)),
                Ingredient.of(ModTags.Items.KNIVES), 1);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUNDLE)
                .pattern(" S ").pattern(" L ").pattern("   ")
                .define('S', ModItems.STRAW_CORD).define('L', Items.LEATHER)
                .unlockedBy("has_straw_cord", has(ModItems.STRAW_CORD)).save(recipeOutput);

        // Clay Stuff
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PORCELAIN_CLAY_BALL)
                .requires(Items.CLAY_BALL).requires(Items.BONE_MEAL)
                .unlockedBy("has_bone_meal", has(Items.BONE_MEAL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFIRED_CLAY_BUCKET)
                .pattern("   ").pattern("C C").pattern(" C ")
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_BRICKS)
                .pattern("PP ").pattern("PP ").pattern("   ")
                .define('P', ModItems.PORCELAIN_CLAY_BRICK)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_BRICK_SLAB, 6)
                .pattern("   ").pattern("PPP").pattern("   ")
                .define('P', ModBlocks.PORCELAIN_BRICKS)
                .unlockedBy("has_porcelain_clay_bricks", has(ModBlocks.PORCELAIN_BRICKS)).save(recipeOutput);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.PORCELAIN_BRICKS), RecipeCategory.MISC,
                        ModBlocks.PORCELAIN_BRICK_SLAB, 2)
                .unlockedBy("has_porcelain_bricks", has(ModBlocks.PORCELAIN_BRICKS))
                .save(recipeOutput, "porcelain_brick_slab_from_stonecutting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_BRICK_STAIRS, 4)
                .pattern("P  ").pattern("PP ").pattern("PPP")
                .define('P', ModBlocks.PORCELAIN_BRICKS)
                .unlockedBy("has_porcelain_clay_bricks", has(ModBlocks.PORCELAIN_BRICKS)).save(recipeOutput);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.PORCELAIN_BRICKS), RecipeCategory.MISC,
                        ModBlocks.PORCELAIN_BRICK_STAIRS, 1)
                .unlockedBy("has_porcelain_bricks", has(ModBlocks.PORCELAIN_BRICKS))
                .save(recipeOutput, "porcelain_brick_stairs_from_stonecutting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_BRICK_WALL, 6)
                .pattern("   ").pattern("PPP").pattern("PPP")
                .define('P', ModBlocks.PORCELAIN_BRICKS)
                .unlockedBy("has_porcelain_clay_bricks", has(ModBlocks.PORCELAIN_BRICKS)).save(recipeOutput);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.PORCELAIN_BRICKS), RecipeCategory.MISC,
                        ModBlocks.PORCELAIN_BRICK_WALL, 1)
                .unlockedBy("has_porcelain_bricks", has(ModBlocks.PORCELAIN_BRICKS))
                .save(recipeOutput, "porcelain_brick_wall_from_stonecutting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_MELTER, 1)
                .pattern("B B").pattern("B B").pattern("BPB")
                .define('P', ModBlocks.PORCELAIN_BRICKS).define('B', ModItems.PORCELAIN_CLAY_BRICK)
                .unlockedBy("has_porcelain_clay_bricks", has(ModBlocks.PORCELAIN_BRICKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_HEATER, 1)
                .pattern("BBB").pattern("BCB").pattern("BPB")
                .define('P', ModBlocks.PORCELAIN_BRICKS).define('B', ModItems.PORCELAIN_CLAY_BRICK)
                .define('C', ItemTags.COALS)
                .unlockedBy("has_porcelain_clay_bricks", has(ModBlocks.PORCELAIN_BRICKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_FAUCET, 1)
                .pattern("B B").pattern(" B ").pattern("   ")
                .define('B', ModItems.PORCELAIN_CLAY_BRICK)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_CASTING_TABLE, 1)
                .pattern("BBB").pattern("B B").pattern("B B")
                .define('B', ModItems.PORCELAIN_CLAY_BRICK)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_CASTING_BASIN, 1)
                .pattern("B B").pattern("B B").pattern("BBB")
                .define('B', ModItems.PORCELAIN_CLAY_BRICK)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);

        // Thatch Stuff
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STRAW , 4)
                .requires(ModBlocks.THATCH, 1)
                .unlockedBy("has_thatch", has(ModBlocks.THATCH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THATCH)
                .pattern("SS ").pattern("SS ").pattern("   ")
                .define('S', ModItems.STRAW)
                .unlockedBy("has_straw", has(ModItems.STRAW)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THATCH_SLAB, 6)
                .pattern("   ").pattern("TTT").pattern("   ")
                .define('T', ModBlocks.THATCH)
                .unlockedBy("has_thatch", has(ModBlocks.THATCH)).save(recipeOutput);

        // Copper Tool Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SWORD)
                .pattern(" P ").pattern(" PL").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.COPPER_PLATE)
                .define('L',ModItems.STRAW_CORD)
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_PICKAXE)
                .pattern("PCP").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT)
                .define('P', ModItems.COPPER_PLATE).define('L',ModItems.STRAW_CORD)
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_AXE)
                .pattern("CP ").pattern("PSL").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT)
                .define('L',ModItems.STRAW_CORD).define('P', ModItems.COPPER_PLATE)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SHOVEL)
                .pattern(" P ").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.COPPER_PLATE)
                .define('L',ModItems.STRAW_CORD)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HOE)
                .pattern("PC ").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT)
                .define('L',ModItems.STRAW_CORD).define('P', ModItems.COPPER_PLATE)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SPEAR)
                .pattern("  C").pattern(" SL").pattern("S  ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT)
                .define('L',ModItems.STRAW_CORD)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);

        // Bone tool Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SWORD)
                .pattern(" C ").pattern(" BP").pattern(" S ")
                .define('B', Items.BONE).define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_PICKAXE)
                .pattern("CBC").pattern(" SP").pattern(" S ")
                .define('B', Items.BONE).define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_AXE)
                .pattern("CB ").pattern("CSP").pattern(" S ")
                .define('B', Items.BONE).define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE).define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_CLUB)
                .pattern(" B ").pattern(" C ").pattern("   ")
                .define('B', Items.BONE).define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SPEAR)
                .pattern("  C").pattern(" SP").pattern("S  ")
                .define('S', Items.STICK).define('C', ModItems.SHARP_BONE)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);

        // Amethyst Bone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SWORD)
                .pattern(" A ").pattern("ABA").pattern(" S ")
                .define('B', ModItems.BONE_SWORD).define('A', Items.AMETHYST_SHARD)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" S ")
                .define('B', ModItems.BONE_PICKAXE).define('A', Items.AMETHYST_SHARD)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_pickaxe", has(ModItems.BONE_PICKAXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_AXE)
                .pattern("AA ").pattern("AB ").pattern(" S ")
                .define('B', ModItems.BONE_AXE).define('A', Items.AMETHYST_SHARD)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_axe", has(ModItems.BONE_AXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SPEAR)
                .pattern(" AA").pattern(" BA").pattern("S  ")
                .define('B', ModItems.BONE_SPEAR).define('A', Items.AMETHYST_SHARD)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);

        // Golden Bone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SWORD)
                .pattern(" A ").pattern("ABA").pattern(" S ")
                .define('B', ModItems.BONE_SWORD).define('A', ModItems.GOLD_PLATE)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_PICKAXE)
                .pattern("AAA").pattern(" B ").pattern(" S ")
                .define('B', ModItems.BONE_PICKAXE).define('A', ModItems.GOLD_PLATE)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_pickaxe", has(ModItems.BONE_PICKAXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_AXE)
                .pattern("AA ").pattern("AB ").pattern(" S ")
                .define('B', ModItems.BONE_AXE).define('A', ModItems.GOLD_PLATE)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_axe", has(ModItems.BONE_AXE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SPEAR)
                .pattern(" AA").pattern(" BA").pattern("S  ")
                .define('B', ModItems.BONE_SPEAR).define('A', ModItems.GOLD_PLATE)
                .define('S', Items.IRON_INGOT)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);

        // Bronze Tool Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_SWORD)
                .pattern(" P ").pattern(" PL").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.BRONZE_PLATE)
                .define('L',ModItems.LEATHER_CORD)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_PICKAXE)
                .pattern("PCP").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('C', ModItems.BRONZE_INGOT)
                .define('P', ModItems.BRONZE_PLATE).define('L',ModItems.LEATHER_CORD)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_AXE)
                .pattern("CP ").pattern("PSL").pattern(" S ")
                .define('S', Items.STICK).define('C', ModItems.BRONZE_INGOT)
                .define('P', ModItems.BRONZE_PLATE).define('L',ModItems.LEATHER_CORD)
                .unlockedBy("has_bronze_ingot", has(ModItems.BRONZE_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_SHOVEL)
                .pattern(" P ").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.BRONZE_PLATE)
                .define('L',ModItems.LEATHER_CORD)
                .unlockedBy("has_bronze_ingot", has(ModItems.BRONZE_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_HOE)
                .pattern("PC ").pattern(" SL").pattern(" S ")
                .define('S', Items.STICK).define('C', ModItems.BRONZE_INGOT)
                .define('P', ModItems.BRONZE_PLATE).define('L',ModItems.LEATHER_CORD)
                .unlockedBy("has_bronze_ingot", has(ModItems.BRONZE_INGOT)).save(recipeOutput);

        // Workstation Stuff
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FURNACE)
                .pattern("SSS").pattern("SCS").pattern("MMM")
                .define('S', Items.STONE).define('M', Items.SMOOTH_STONE).define('C', Items.COAL_BLOCK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("minecraft", "furnace"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHOPPING_BLOCK)
                .pattern(" L ").pattern(" F ").pattern("   ")
                .define('L', ItemTags.LOGS).define('F', ModItems.FLINT_SHARD)
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_KILN)
                .pattern("PPP").pattern("P P").pattern("PBP")
                .define('P', ModItems.PORCELAIN_CLAY_BRICK).define('B', Items.CLAY)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WORKSTONE)
                .pattern(" S ").pattern(" L ").pattern("   ")
                .define('S', Blocks.SMOOTH_STONE).define('L', ItemTags.LOGS)
                .unlockedBy("has_smooth_stone", has(Blocks.SMOOTH_STONE)).save(recipeOutput);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.PORCELAIN_CLAY_BALL), RecipeCategory.MISC,
                        ModItems.PORCELAIN_CLAY_BRICK, 0.35f, 600)
                .unlockedBy("has_porcelain_clay_ball", has(ModItems.PORCELAIN_CLAY_BALL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CAMPFIRE_BASE)
                .pattern("PP ").pattern("PP ").pattern("   ")
                .define('P', ModTags.Items.POSTS)
                .unlockedBy("has_any_post", has(ModTags.Items.POSTS)).save(recipeOutput);

        // Custom tools
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_KNIFE)
                .pattern(" C ").pattern("S  ").pattern("   ")
                .define('C', ModItems.COPPER_PLATE).define('S', Items.STICK)
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_KNIFE)
                .pattern(" I ").pattern("S  ").pattern("   ")
                .define('S', Items.STICK).define('I', ModItems.IRON_PLATE)
                .unlockedBy("has_iron_plate", has(ModItems.IRON_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_KNIFE)
                .pattern(" D ").pattern("S  ").pattern("   ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SAW)
                .pattern("SSS").pattern("LFC").pattern("   ")
                .define('C', ModItems.COPPER_PLATE ).define('S', Items.STICK)
                .define('F', Items.COPPER_INGOT).define('L', ItemTags.LOGS)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_SAW)
                .pattern("SSS").pattern("LII").pattern("   ")
                .define('S', Items.STICK).define('I', Items.IRON_INGOT)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_SAW)
                .pattern("SSS").pattern("LDD").pattern("   ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_HAMMER)
                .pattern(" CO").pattern(" SC").pattern("S  ")
                .define('C', Items.STONE).define('S', Items.STICK)
                .define('O', ModItems.STRAW_CORD)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HAMMER)
                .pattern(" BO").pattern(" SC").pattern("S  ")
                .define('C', ModItems.COPPER_PLATE).define('S', Items.STICK)
                .define('B', Blocks.COPPER_BLOCK).define('O', ModItems.STRAW_CORD)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_HAMMER)
                .pattern(" BO").pattern(" SI").pattern("S  ")
                .define('S', Items.STICK).define('I', ModItems.IRON_PLATE)
                .define('B', Blocks.IRON_BLOCK).define('O', Items.STRING)
                .unlockedBy("has_iron_plate", has(ModItems.IRON_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_HAMMER)
                .pattern(" BO").pattern(" SD").pattern("S  ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .define('B', Blocks.DIAMOND_BLOCK).define('O', Items.STRING)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        // Bronze Armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_HELMET)
                .pattern("PPP").pattern("P P").pattern("   ")
                .define('P', ModItems.BRONZE_PLATE)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_CHESTPLATE)
                .pattern("P P").pattern("PPP").pattern("PPP")
                .define('P', ModItems.BRONZE_PLATE)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_LEGGINGS)
                .pattern("PPP").pattern("P P").pattern("P P")
                .define('P', ModItems.BRONZE_PLATE)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_BOOTS)
                .pattern("P P").pattern("P P").pattern("   ")
                .define('P', ModItems.BRONZE_PLATE)
                .unlockedBy("has_bronze_plate", has(ModItems.BRONZE_PLATE)).save(recipeOutput);

        // Rose Gold Armor
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ROSE_GOLD_HELMET)
                .pattern("PPP").pattern("P P").pattern("   ")
                .define('P', ModItems.ROSE_GOLD_PLATE)
                .unlockedBy("has_rose_gold_plate", has(ModItems.ROSE_GOLD_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ROSE_GOLD_CHESTPLATE)
                .pattern("P P").pattern("PPP").pattern("PPP")
                .define('P', ModItems.ROSE_GOLD_PLATE)
                .unlockedBy("has_rose_gold_plate", has(ModItems.ROSE_GOLD_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ROSE_GOLD_LEGGINGS)
                .pattern("PPP").pattern("P P").pattern("P P")
                .define('P', ModItems.ROSE_GOLD_PLATE)
                .unlockedBy("has_rose_gold_plate", has(ModItems.ROSE_GOLD_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ROSE_GOLD_BOOTS)
                .pattern("P P").pattern("P P").pattern("   ")
                .define('P', ModItems.ROSE_GOLD_PLATE)
                .unlockedBy("has_rose_gold_plate", has(ModItems.ROSE_GOLD_PLATE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLANK_MOLD)
                .pattern("BB ").pattern("BB ").pattern("   ")
                .define('B', ModItems.MUD_BRICK)
                .unlockedBy("has_mud_brick", has(ModItems.MUD_BRICK)).save(recipeOutput);

        TagKey<Item> cBricks = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bricks"));
        TagKey<Item> cPlates = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "plates"));
        TagKey<Item> cNuggets = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INGOT_MOLD.get(), 1)
                .requires(ModItems.BLANK_MOLD.get())
                .requires(cBricks)
                .unlockedBy("has_blank_mold", has(ModItems.BLANK_MOLD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLATE_MOLD.get(), 1)
                .requires(ModItems.BLANK_MOLD.get())
                .requires(cPlates)
                .unlockedBy("has_blank_mold", has(ModItems.BLANK_MOLD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NUGGET_MOLD.get(), 1)
                .requires(ModItems.BLANK_MOLD.get())
                .requires(cNuggets)
                .unlockedBy("has_blank_mold", has(ModItems.BLANK_MOLD.get())).save(recipeOutput);

        damagingShapelessRecipe(recipeOutput, "sword_blade_mold",
                new ItemStack(ModItems.SWORD_BLADE_MOLD.get(), 1),
                List.of(Ingredient.of(ModItems.BLANK_MOLD)),
                Ingredient.of(ItemTags.SWORDS), 1);
        damagingShapelessRecipe(recipeOutput, "pickaxe_head_mold",
                new ItemStack(ModItems.PICKAXE_HEAD_MOLD.get(), 1),
                List.of(Ingredient.of(ModItems.BLANK_MOLD)),
                Ingredient.of(ItemTags.PICKAXES), 1);
        damagingShapelessRecipe(recipeOutput, "axe_head_mold",
                new ItemStack(ModItems.AXE_HEAD_MOLD.get(), 1),
                List.of(Ingredient.of(ModItems.BLANK_MOLD)),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "shovel_head_mold",
                new ItemStack(ModItems.SHOVEL_HEAD_MOLD.get(), 1),
                List.of(Ingredient.of(ModItems.BLANK_MOLD)),
                Ingredient.of(ItemTags.SHOVELS), 1);
        damagingShapelessRecipe(recipeOutput, "hoe_head_mold",
                new ItemStack(ModItems.HOE_HEAD_MOLD.get(), 1),
                List.of(Ingredient.of(ModItems.BLANK_MOLD)),
                Ingredient.of(ItemTags.HOES), 1);

        damagingShapelessRecipe(recipeOutput, "copper_ingot_hammering_to_dust",
                new ItemStack(ModItems.COPPER_DUST.get(), 1),
                List.of(Ingredient.of(Items.COPPER_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);
        damagingShapelessRecipe(recipeOutput, "gold_ingot_hammering_to_dust",
                new ItemStack(ModItems.GOLD_DUST.get(), 1),
                List.of(Ingredient.of(Items.GOLD_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);
        damagingShapelessRecipe(recipeOutput, "tin_ingot_hammering_to_dust",
                new ItemStack(ModItems.TIN_DUST.get(), 1),
                List.of(Ingredient.of(ModItems.TIN_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);
        damagingShapelessRecipe(recipeOutput, "silver_ingot_hammering_to_dust",
                new ItemStack(ModItems.SILVER_DUST.get(), 1),
                List.of(Ingredient.of(ModItems.SILVER_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);
        damagingShapelessRecipe(recipeOutput, "bronze_ingot_hammering_to_dust",
                new ItemStack(ModItems.BRONZE_DUST.get(), 1),
                List.of(Ingredient.of(ModItems.BRONZE_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);
        damagingShapelessRecipe(recipeOutput, "rose_gold_ingot_hammering_to_dust",
                new ItemStack(ModItems.ROSE_GOLD_DUST.get(), 1),
                List.of(Ingredient.of(ModItems.ROSE_GOLD_INGOT)),
                Ingredient.of(ModTags.Items.HAMMERS), 1);

        damagingShapelessRecipe(recipeOutput, "gravel_to_flint_with_fiber_mesh",
                new ItemStack(Items.FLINT, 1),
                List.of(Ingredient.of(Items.GRAVEL), Ingredient.of(Items.GRAVEL)),
                Ingredient.of(ModItems.FIBER_MESH), 1);

        ShapedToolSpec[] shapedTools = {
                // Copper
                new ShapedToolSpec("copper_sword", ModItems.COPPER_SWORD, ModItems.COPPER_SWORD_BLADE, ModItems.STRAW_CORD),
                new ShapedToolSpec("copper_pickaxe", ModItems.COPPER_PICKAXE, ModItems.COPPER_PICKAXE_HEAD, ModItems.STRAW_CORD),
                new ShapedToolSpec("copper_axe", ModItems.COPPER_AXE, ModItems.COPPER_AXE_HEAD, ModItems.STRAW_CORD),
                new ShapedToolSpec("copper_shovel", ModItems.COPPER_SHOVEL, ModItems.COPPER_SHOVEL_HEAD, ModItems.STRAW_CORD),
                new ShapedToolSpec("copper_hoe", ModItems.COPPER_HOE, ModItems.COPPER_HOE_HEAD, ModItems.STRAW_CORD),
                // Bronze
                new ShapedToolSpec("bronze_sword", ModItems.BRONZE_SWORD, ModItems.BRONZE_SWORD_BLADE, ModItems.LEATHER_CORD.get()),
                new ShapedToolSpec("bronze_pickaxe", ModItems.BRONZE_PICKAXE, ModItems.BRONZE_PICKAXE_HEAD, ModItems.LEATHER_CORD.get()),
                new ShapedToolSpec("bronze_axe", ModItems.BRONZE_AXE, ModItems.BRONZE_AXE_HEAD, ModItems.LEATHER_CORD.get()),
                new ShapedToolSpec("bronze_shovel", ModItems.BRONZE_SHOVEL, ModItems.BRONZE_SHOVEL_HEAD, ModItems.LEATHER_CORD.get()),
                new ShapedToolSpec("bronze_hoe", ModItems.BRONZE_HOE, ModItems.BRONZE_HOE_HEAD, ModItems.LEATHER_CORD.get()),
                // Iron
                new ShapedToolSpec("iron_sword", Items.IRON_SWORD, ModItems.IRON_SWORD_BLADE, Items.STRING),
                new ShapedToolSpec("iron_pickaxe", Items.IRON_PICKAXE, ModItems.IRON_PICKAXE_HEAD, Items.STRING),
                new ShapedToolSpec("iron_axe", Items.IRON_AXE, ModItems.IRON_AXE_HEAD, Items.STRING),
                new ShapedToolSpec("iron_shovel", Items.IRON_SHOVEL, ModItems.IRON_SHOVEL_HEAD, Items.STRING),
                new ShapedToolSpec("iron_hoe", Items.IRON_HOE, ModItems.IRON_HOE_HEAD, Items.STRING),
                // Gold
                new ShapedToolSpec("golden_sword", Items.GOLDEN_SWORD, ModItems.GOLD_SWORD_BLADE, Items.STRING),
                new ShapedToolSpec("golden_pickaxe", Items.GOLDEN_PICKAXE, ModItems.GOLD_PICKAXE_HEAD, Items.STRING),
                new ShapedToolSpec("golden_axe", Items.GOLDEN_AXE, ModItems.GOLD_AXE_HEAD, Items.STRING),
                new ShapedToolSpec("golden_shovel", Items.GOLDEN_SHOVEL, ModItems.GOLD_SHOVEL_HEAD, Items.STRING),
                new ShapedToolSpec("golden_hoe", Items.GOLDEN_HOE, ModItems.GOLD_HOE_HEAD, Items.STRING),
                // Rose Gold
                new ShapedToolSpec("rose_gold_sword", ModItems.ROSE_GOLD_SWORD, ModItems.ROSE_GOLD_SWORD_BLADE, Items.STRING),
                new ShapedToolSpec("rose_gold_pickaxe", ModItems.ROSE_GOLD_PICKAXE, ModItems.ROSE_GOLD_PICKAXE_HEAD, Items.STRING),
                new ShapedToolSpec("rose_gold_axe", ModItems.ROSE_GOLD_AXE, ModItems.ROSE_GOLD_AXE_HEAD, Items.STRING),
                new ShapedToolSpec("rose_gold_shovel", ModItems.ROSE_GOLD_SHOVEL, ModItems.ROSE_GOLD_SHOVEL_HEAD, Items.STRING),
                new ShapedToolSpec("rose_gold_hoe", ModItems.ROSE_GOLD_HOE, ModItems.ROSE_GOLD_HOE_HEAD, Items.STRING)
        };

        for (ShapedToolSpec tool : shapedTools) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, tool.result())
                    .pattern(" HP")
                    .pattern(" S ")
                    .pattern("   ")
                    .define('H', tool.head())
                    .define('S', Items.STICK)
                    .define('P', Ingredient.of(tool.binding()))
                    .unlockedBy("has_" + tool.name(), has(tool.head()))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, tool.name() + "from_head"));
        }

        CastingSpec[] castingRecipes = {
                // Copper
                new CastingSpec("copper_sword_blade", ModItems.COPPER_SWORD_BLADE.get(), ModItems.SWORD_BLADE_MOLD.get(),
                        ModFluids.MOLTEN_COPPER, 288, 100, false),
                new CastingSpec("copper_pickaxe_head", ModItems.COPPER_PICKAXE_HEAD.get(), ModItems.PICKAXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_COPPER, 432, 100, false),
                new CastingSpec("copper_axe_head", ModItems.COPPER_AXE_HEAD.get(), ModItems.AXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_COPPER, 432, 100, false),
                new CastingSpec("copper_shovel_head", ModItems.COPPER_SHOVEL_HEAD.get(), ModItems.SHOVEL_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_COPPER, 144, 100, false),
                new CastingSpec("copper_hoe_head", ModItems.COPPER_HOE_HEAD, ModItems.HOE_HEAD_MOLD,
                        ModFluids.MOLTEN_COPPER, 288, 100, false),
                // Bronze
                new CastingSpec("bronze_sword_blade", ModItems.BRONZE_SWORD_BLADE.get(), ModItems.SWORD_BLADE_MOLD.get(),
                        ModFluids.MOLTEN_BRONZE, 288, 100, false),
                new CastingSpec("bronze_pickaxe_head", ModItems.BRONZE_PICKAXE_HEAD.get(), ModItems.PICKAXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_BRONZE, 432, 100, false),
                new CastingSpec("bronze_axe_head", ModItems.BRONZE_AXE_HEAD.get(), ModItems.AXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_BRONZE, 432, 100, false),
                new CastingSpec("bronze_shovel_head", ModItems.BRONZE_SHOVEL_HEAD.get(), ModItems.SHOVEL_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_BRONZE, 144, 100, false),
                new CastingSpec("bronze_hoe_head", ModItems.BRONZE_HOE_HEAD, ModItems.HOE_HEAD_MOLD,
                        ModFluids.MOLTEN_BRONZE, 288, 100, false),
                // Iron
                new CastingSpec("iron_sword_blade", ModItems.IRON_SWORD_BLADE.get(), ModItems.SWORD_BLADE_MOLD.get(),
                        ModFluids.MOLTEN_IRON, 288, 100, false),
                new CastingSpec("iron_pickaxe_head", ModItems.IRON_PICKAXE_HEAD.get(), ModItems.PICKAXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_IRON, 432, 100, false),
                new CastingSpec("iron_axe_head", ModItems.IRON_AXE_HEAD.get(), ModItems.AXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_IRON, 432, 100, false),
                new CastingSpec("iron_shovel_head", ModItems.IRON_SHOVEL_HEAD.get(), ModItems.SHOVEL_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_IRON, 144, 100, false),
                new CastingSpec("iron_hoe_head", ModItems.IRON_HOE_HEAD, ModItems.HOE_HEAD_MOLD,
                        ModFluids.MOLTEN_IRON, 288, 100, false),
                // Gold
                new CastingSpec("gold_sword_blade", ModItems.GOLD_SWORD_BLADE.get(), ModItems.SWORD_BLADE_MOLD.get(),
                        ModFluids.MOLTEN_GOLD, 288, 100, false),
                new CastingSpec("gold_pickaxe_head", ModItems.GOLD_PICKAXE_HEAD.get(), ModItems.PICKAXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_GOLD, 432, 100, false),
                new CastingSpec("gold_axe_head", ModItems.GOLD_AXE_HEAD.get(), ModItems.AXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_GOLD, 432, 100, false),
                new CastingSpec("gold_shovel_head", ModItems.GOLD_SHOVEL_HEAD.get(), ModItems.SHOVEL_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_GOLD, 144, 100, false),
                new CastingSpec("gold_hoe_head", ModItems.GOLD_HOE_HEAD, ModItems.HOE_HEAD_MOLD,
                        ModFluids.MOLTEN_GOLD, 288, 100, false),
                // Rose Gold
                new CastingSpec("rose_gold_sword_blade", ModItems.ROSE_GOLD_SWORD_BLADE.get(), ModItems.SWORD_BLADE_MOLD.get(),
                        ModFluids.MOLTEN_ROSE_GOLD, 288, 100, false),
                new CastingSpec("rose_gold_pickaxe_head", ModItems.ROSE_GOLD_PICKAXE_HEAD.get(), ModItems.PICKAXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_ROSE_GOLD, 432, 100, false),
                new CastingSpec("rose_gold_axe_head", ModItems.ROSE_GOLD_AXE_HEAD.get(), ModItems.AXE_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_ROSE_GOLD, 432, 100, false),
                new CastingSpec("rose_gold_shovel_head", ModItems.ROSE_GOLD_SHOVEL_HEAD.get(), ModItems.SHOVEL_HEAD_MOLD.get(),
                        ModFluids.MOLTEN_ROSE_GOLD, 144, 100, false),
                new CastingSpec("rose_gold_hoe_head", ModItems.ROSE_GOLD_HOE_HEAD, ModItems.HOE_HEAD_MOLD,
                        ModFluids.MOLTEN_ROSE_GOLD, 288, 100, false),
        };

        for (CastingSpec spec : castingRecipes) {
            castingRecipe(recipeOutput, spec.name(), spec.result(), spec.mold(),
                    spec.fluid(), spec.fluidAmount(), spec.coolingTime(), spec.consumeCast());

            meltingRecipe(recipeOutput, spec.name() + "_melting", spec.result(),
                    spec.fluid(), spec.fluidAmount(), 400);
        }

        for (ModBlocks.WoodDefinition wood : ModBlocks.WOODS) {
            String expectedPrefix = wood.modId().equals(Progressioned.MOD_ID) || wood.modId().equals("minecraft")
                    ? ""
                    : wood.modId() + "_";

            String postPath = expectedPrefix + wood.woodName() + "_post";

            Block postBlock = ModBlocks.BLOCKS.getEntries().stream()
                    .filter(e -> e.getId().getPath().equals(postPath))
                    .map(e -> (Block) e.get()).findFirst().orElse(null);

            Block strippedPostBlock = null;
            if (wood.hasStripped()) {
                String strippedPostPath = expectedPrefix + wood.strippedWoodName() + "_post";

                strippedPostBlock = ModBlocks.BLOCKS.getEntries().stream()
                        .filter(e -> e.getId().getPath().equals(strippedPostPath))
                        .map(e -> (Block) e.get()).findFirst().orElse(null);
            }

            if (postBlock != null) {
                String itemNamespace = wood.modId().equals(Progressioned.MOD_ID) ? "minecraft" : wood.modId();

                Item planksItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(itemNamespace, wood.planksName()));
                Item logItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(itemNamespace, wood.woodName() + "_log"));
                Item woodItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(itemNamespace, wood.woodName() + "_wood"));

                Item strippedLogItem = null;
                Item strippedWoodItem = null;
                if (wood.hasStripped()) {
                    strippedLogItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(itemNamespace, wood.strippedWoodName() + "_log"));
                    strippedWoodItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(itemNamespace, wood.strippedWoodName() + "_wood"));
                }

                String namePrefix = wood.modId().equals(Progressioned.MOD_ID) || wood.modId().equals("minecraft") ? "" : wood.modId() + "_";

                if (planksItem != null && planksItem != Items.AIR) {
                    String tagName = (wood.modId().equals(Progressioned.MOD_ID) || wood.modId().equals("minecraft"))
                            ? wood.woodName() + "_posts"
                            : wood.modId() + "_" + wood.woodName() + "_posts";

                    TagKey<Item> postTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, tagName));

                    damagingShapelessRecipe(recipeOutput, namePrefix + wood.woodName() + "_posts_to_plank",
                            new ItemStack(planksItem, 1),
                            List.of(Ingredient.of(postTag)),
                            Ingredient.of(ItemTags.AXES), 1);
                }

                if (logItem != null && logItem != Items.AIR) {
                    choppingRecipe(recipeOutput, namePrefix + wood.woodName() + "_log_to_post", Ingredient.of(logItem),
                            new ItemStack(postBlock.asItem(), 2), 5);
                }
                if (woodItem != null && woodItem != Items.AIR) {
                    choppingRecipe(recipeOutput, namePrefix + wood.woodName() + "_wood_to_post", Ingredient.of(woodItem),
                            new ItemStack(postBlock.asItem(), 2), 5);
                }

                if (strippedPostBlock != null) {
                    if (strippedLogItem != null && strippedLogItem != Items.AIR) {
                        choppingRecipe(recipeOutput, namePrefix + wood.strippedWoodName() + "_log_to_post", Ingredient.of(strippedLogItem),
                                new ItemStack(strippedPostBlock.asItem(), 2), 5);
                    }
                    if (strippedWoodItem != null && strippedWoodItem != Items.AIR) {
                        choppingRecipe(recipeOutput, namePrefix + wood.strippedWoodName() + "_wood_to_post", Ingredient.of(strippedWoodItem),
                                new ItemStack(strippedPostBlock.asItem(), 2), 5);
                    }
                }
            }
        }
    }

    private void choppingRecipe(RecipeOutput output, String name, Ingredient input,
                                ItemStack result, int hits) {
        output.accept(
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "chopping/" + name),
                new ChoppingRecipe(input, result, hits),
                null);
    }

    private void damagingShapelessRecipe(RecipeOutput output, String name,
                                         ItemStack result, List<Ingredient> ingredients,
                                         Ingredient tool, int damage) {
        output.accept(
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, name),
                new DamagingShapelessRecipe("", CraftingBookCategory.MISC,
                        result, ingredients, tool, damage),
                null);
    }

    private void castingRecipe(RecipeOutput output, String name, ItemLike result,
                               ItemLike mold, FluidHolder<?> fluid, int fluidAmount,
                               int coolingTime, boolean consumeCast) {
        output.accept(
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "casting/" + name),
                new CastingRecipe(
                        Ingredient.of(mold),
                        SizedFluidIngredient.of(fluid.source().get(), fluidAmount),
                        new ItemStack(result.asItem(), 1),
                        coolingTime,
                        consumeCast
                ),
                null);
    }

    private void meltingRecipe(RecipeOutput output, String name, ItemLike input,
                               FluidHolder<?> fluid, int fluidAmount, int cookingTime) {
        output.accept(
                ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "melting/" + name),
                new MeltingRecipe(
                        Ingredient.of(input),
                        new FluidStack(fluid.source().get(), fluidAmount),
                        cookingTime
                ),
                null);
    }
}