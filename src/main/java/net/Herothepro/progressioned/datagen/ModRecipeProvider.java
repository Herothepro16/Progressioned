package net.Herothepro.progressioned.datagen;

import net.Herothepro.progressioned.Progressioned;
import net.Herothepro.progressioned.block.ModBlocks;
import net.Herothepro.progressioned.item.ModItems;
import net.Herothepro.progressioned.recipe.ChoppingRecipe;
import net.Herothepro.progressioned.recipe.DamagingShapelessRecipe;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICK.get())
                .pattern("CFC")
                .pattern(" SP")
                .pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE.get())
                .pattern("FC ")
                .pattern("PS ")
                .pattern("   ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL.get())
                .pattern(" F ")
                .pattern(" SP")
                .pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_KNIFE.get())
                .pattern(" C ")
                .pattern("SP ")
                .pattern("   ")
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIBER_MESH.get())
                .pattern("SF ")
                .pattern("FS ")
                .pattern("   ")
                .define('S', Items.STICK)
                .define('F', ModItems.PLANT_STRING.get())
                .unlockedBy("has_plant_string", has(ModItems.PLANT_STRING)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SPEAR.get())
                .pattern("  C")
                .pattern(" SP")
                .pattern("S  ")
                .define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_STRING)
                .requires(ModItems.PLANT_FIBER, 3)
                .unlockedBy("has_plant_fiber", has(ModItems.PLANT_FIBER)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_FIBER)
                .requires(Items.WHEAT_SEEDS, 3)
                .unlockedBy("has_wheat_seeds", has(Items.WHEAT_SEEDS)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FIRE_STICK)
                .requires(Items.STICK, 2)
                .unlockedBy("has_sticks", has(Items.STICK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PORCELAIN_CLAY_BALL)
                .requires(Items.CLAY_BALL, 1)
                .requires(Items.BONE_MEAL, 2)
                .unlockedBy("has_bone_meal", has(Items.BONE_MEAL)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFIRED_CLAY_BUCKET.get())
                .pattern("   ")
                .pattern("C C")
                .pattern(" C ")
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SWORD.get())
                .pattern(" C ")
                .pattern(" C ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_PICKAXE.get())
                .pattern("CCC")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_AXE.get())
                .pattern("CC ")
                .pattern("CS ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SHOVEL.get())
                .pattern(" C ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HOE.get())
                .pattern("CC ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SPEAR.get())
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SPEAR.get())
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SWORD.get())
                .pattern(" C ")
                .pattern(" B ")
                .pattern(" S ")
                .define('B', Items.BONE)
                .define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_CLUB.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern("   ")
                .define('B', Items.BONE)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SWORD.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" S ")
                .define('B', ModItems.BONE_SWORD)
                .define('A', Items.AMETHYST_SHARD)
                .define('S', Items.STICK)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SPEAR.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("A  ")
                .define('B', ModItems.BONE_SPEAR)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SWORD.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" S ")
                .define('B', ModItems.BONE_SWORD)
                .define('A', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SPEAR.get())
                .pattern(" AA")
                .pattern(" BA")
                .pattern("A  ")
                .define('B', ModItems.BONE_SPEAR)
                .define('A', Items.GOLD_INGOT)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CAMPFIRE)
                .pattern(" N ")
                .pattern("NCN")
                .pattern(" F ")
                .define('N', Items.NETHERRACK)
                .define('F', Items.FLINT_AND_STEEL)
                .define('C', ModBlocks.CAMPFIRE_BASE.get())
                .unlockedBy("has_flint_and_steel", has(Items.FLINT_AND_STEEL))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "campfire_override"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FURNACE)
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .define('S', Items.STONE)
                .define('C', Items.COAL_BLOCK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "furnace_override"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHOPPING_BLOCK.get())
                .pattern(" L ")
                .pattern(" F ")
                .pattern("   ")
                .define('L', ItemTags.LOGS)
                .define('F', ModItems.FLINT_SHARD)
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_KILN.get())
                .pattern("PPP")
                .pattern("P P")
                .pattern("BBB")
                .define('P', ModItems.PORCELAIN_CLAY_BRICK)
                .define('B', Items.CLAY)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.PORCELAIN_CLAY_BALL.get()), RecipeCategory.MISC,
                 ModItems.PORCELAIN_CLAY_BRICK.get(), 0.35f, 600                                        )
                .unlockedBy("has_porcelain_clay_ball", has(ModItems.PORCELAIN_CLAY_BALL.get()))
                .save(recipeOutput);

        //wooden tool recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_SWORD)
                .pattern(" T ")
                .pattern("PT ")
                .pattern(" S ")
                .define('T', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "wooden_sword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_PICKAXE)
                .pattern("TTT")
                .pattern(" SP")
                .pattern(" S ")
                .define('T', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "wooden_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_AXE)
                .pattern("TT ")
                .pattern("TSP")
                .pattern(" S ")
                .define('T', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "wooden_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_SHOVEL)
                .pattern(" T ")
                .pattern("PS ")
                .pattern(" S ")
                .define('T', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "wooden_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.WOODEN_HOE)
                .pattern("TTP")
                .pattern(" S ")
                .pattern(" S ")
                .define('T', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "wooden_hoe"));

        //stone tool recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_SWORD)
                .pattern(" T ")
                .pattern("PT ")
                .pattern(" S ")
                .define('T', Items.STONE)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "stone_sword"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_PICKAXE)
                .pattern("TTT")
                .pattern(" SP")
                .pattern(" S ")
                .define('T', Items.STONE)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "stone_pickaxe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_AXE)
                .pattern("TT ")
                .pattern("TSP")
                .pattern(" S ")
                .define('T', Items.STONE)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "stone_axe"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_SHOVEL)
                .pattern(" T ")
                .pattern("PS ")
                .pattern(" S ")
                .define('T', Items.STONE)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "stone_shovel"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_HOE)
                .pattern("TTP")
                .pattern(" S ")
                .pattern(" S ")
                .define('T', Items.STONE)
                .define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("progressioned", "stone_hoe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CAMPFIRE_BASE.get())
                .pattern("PSP")
                .pattern("S S")
                .pattern("PSP")
                .define('P', ModTags.Items.POSTS)
                .define('S', Items.STICK)
                .unlockedBy("has_any_post", has(ModTags.Items.POSTS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SAW.get())
                .pattern("SSS")
                .pattern("LFC")
                .pattern("   ")
                .define('C', ModItems.FLINT_SHARD)
                .define('S', Items.STICK)
                .define('F', Items.FLINT)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_SAW.get())
                .pattern("SSS")
                .pattern("LII")
                .pattern("   ")
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_SAW.get())
                .pattern("SSS")
                .pattern("LDD")
                .pattern("   ")
                .define('S', Items.STICK)
                .define('D', Items.DIAMOND)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        damagingShapelessRecipe(recipeOutput, "gravel_to_flint_with_fiber_mesh",
                new ItemStack(Items.FLINT, 1),
                List.of(Ingredient.of(Items.GRAVEL),
                        Ingredient.of(Items.GRAVEL),
                        Ingredient.of(Items.GRAVEL)),
                Ingredient.of(ModItems.FIBER_MESH), 1);

        damagingShapelessRecipe(recipeOutput, "oak_post_to_plank",
                new ItemStack(Items.OAK_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.OAK_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "spruce_post_to_plank",
                new ItemStack(Items.SPRUCE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.SPRUCE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "birch_post_to_plank",
                new ItemStack(Items.BIRCH_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.BIRCH_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "jungle_post_to_plank",
                new ItemStack(Items.JUNGLE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.JUNGLE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "acacia_post_to_plank",
                new ItemStack(Items.ACACIA_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.ACACIA_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "dark_oak_post_to_plank",
                new ItemStack(Items.DARK_OAK_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.DARK_OAK_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "mangrove_post_to_plank",
                new ItemStack(Items.MANGROVE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.MANGROVE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "cherry_post_to_plank",
                new ItemStack(Items.CHERRY_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.CHERRY_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);

        damagingShapelessRecipe(recipeOutput, "stripped_oak_post_to_plank",
                new ItemStack(Items.OAK_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_OAK_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_spruce_post_to_plank",
                new ItemStack(Items.SPRUCE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_SPRUCE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_birch_post_to_plank",
                new ItemStack(Items.BIRCH_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_BIRCH_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_jungle_post_to_plank",
                new ItemStack(Items.JUNGLE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_JUNGLE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_acacia_post_to_plank",
                new ItemStack(Items.ACACIA_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_ACACIA_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_dark_oak_post_to_plank",
                new ItemStack(Items.DARK_OAK_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_DARK_OAK_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_mangrove_post_to_plank",
                new ItemStack(Items.MANGROVE_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_MANGROVE_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);
        damagingShapelessRecipe(recipeOutput, "stripped_cherry_post_to_plank",
                new ItemStack(Items.CHERRY_PLANKS, 1),
                List.of(Ingredient.of(ModBlocks.STRIPPED_CHERRY_POST.get())),
                Ingredient.of(ItemTags.AXES), 1);

        // Chopping recipes
        choppingRecipe(recipeOutput, "oak_log_to_post", Ingredient.of(Items.OAK_LOG),
                new ItemStack(ModBlocks.OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "oak_wood_to_post", Ingredient.of(Items.OAK_WOOD),
                new ItemStack(ModBlocks.OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "spruce_log_to_post", Ingredient.of(Items.SPRUCE_LOG),
                new ItemStack(ModBlocks.SPRUCE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "spruce_wood_to_post", Ingredient.of(Items.SPRUCE_WOOD),
                new ItemStack(ModBlocks.SPRUCE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "birch_log_to_post", Ingredient.of(Items.BIRCH_LOG),
                new ItemStack(ModBlocks.BIRCH_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "birch_wood_to_post", Ingredient.of(Items.BIRCH_WOOD),
                new ItemStack(ModBlocks.BIRCH_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "jungle_log_to_post", Ingredient.of(Items.JUNGLE_LOG),
                new ItemStack(ModBlocks.JUNGLE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "jungle_wood_to_post", Ingredient.of(Items.JUNGLE_WOOD),
                new ItemStack(ModBlocks.JUNGLE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "acacia_log_to_post", Ingredient.of(Items.ACACIA_LOG),
                new ItemStack(ModBlocks.ACACIA_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "acacia_wood_to_post", Ingredient.of(Items.ACACIA_WOOD),
                new ItemStack(ModBlocks.ACACIA_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "dark_oak_log_to_post", Ingredient.of(Items.DARK_OAK_LOG),
                new ItemStack(ModBlocks.DARK_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "dark_oak_wood_to_post", Ingredient.of(Items.DARK_OAK_WOOD),
                new ItemStack(ModBlocks.DARK_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "mangrove_log_to_post", Ingredient.of(Items.MANGROVE_LOG),
                new ItemStack(ModBlocks.MANGROVE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "mangrove_wood_to_post", Ingredient.of(Items.MANGROVE_WOOD),
                new ItemStack(ModBlocks.MANGROVE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "cherry_log_to_post", Ingredient.of(Items.CHERRY_LOG),
                new ItemStack(ModBlocks.CHERRY_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "cherry_wood_to_post", Ingredient.of(Items.CHERRY_WOOD),
                new ItemStack(ModBlocks.CHERRY_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_oak_log_to_post", Ingredient.of(Items.STRIPPED_OAK_LOG),
                new ItemStack(ModBlocks.STRIPPED_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_oak_wood_to_post", Ingredient.of(Items.STRIPPED_OAK_WOOD),
                new ItemStack(ModBlocks.STRIPPED_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_spruce_log_to_post", Ingredient.of(Items.STRIPPED_SPRUCE_LOG),
                new ItemStack(ModBlocks.STRIPPED_SPRUCE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_spruce_wood_to_post", Ingredient.of(Items.STRIPPED_SPRUCE_WOOD),
                new ItemStack(ModBlocks.STRIPPED_SPRUCE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_birch_log_to_post", Ingredient.of(Items.STRIPPED_BIRCH_LOG),
                new ItemStack(ModBlocks.STRIPPED_BIRCH_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_birch_wood_to_post", Ingredient.of(Items.STRIPPED_BIRCH_WOOD),
                new ItemStack(ModBlocks.STRIPPED_BIRCH_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_jungle_log_to_post", Ingredient.of(Items.STRIPPED_JUNGLE_LOG),
                new ItemStack(ModBlocks.STRIPPED_JUNGLE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_jungle_wood_to_post", Ingredient.of(Items.STRIPPED_JUNGLE_WOOD),
                new ItemStack(ModBlocks.STRIPPED_JUNGLE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_acacia_log_to_post", Ingredient.of(Items.STRIPPED_ACACIA_LOG),
                new ItemStack(ModBlocks.STRIPPED_ACACIA_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_acacia_wood_to_post", Ingredient.of(Items.STRIPPED_ACACIA_WOOD),
                new ItemStack(ModBlocks.STRIPPED_ACACIA_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_dark_oak_log_to_post", Ingredient.of(Items.STRIPPED_DARK_OAK_LOG),
                new ItemStack(ModBlocks.STRIPPED_DARK_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_dark_oak_wood_to_post", Ingredient.of(Items.STRIPPED_DARK_OAK_WOOD),
                new ItemStack(ModBlocks.STRIPPED_DARK_OAK_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_mangrove_log_to_post", Ingredient.of(Items.STRIPPED_MANGROVE_LOG),
                new ItemStack(ModBlocks.STRIPPED_MANGROVE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_mangrove_wood_to_post", Ingredient.of(Items.STRIPPED_MANGROVE_WOOD),
                new ItemStack(ModBlocks.STRIPPED_MANGROVE_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_cherry_log_to_post", Ingredient.of(Items.STRIPPED_CHERRY_LOG),
                new ItemStack(ModBlocks.STRIPPED_CHERRY_POST.asItem(), 2), 5);
        choppingRecipe(recipeOutput, "stripped_cherry_wood_to_post", Ingredient.of(Items.STRIPPED_CHERRY_WOOD),
                new ItemStack(ModBlocks.STRIPPED_CHERRY_POST.asItem(), 2), 5);
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
}