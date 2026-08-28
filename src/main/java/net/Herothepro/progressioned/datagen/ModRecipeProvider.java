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
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.FalseCondition;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Flint tool recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_PICK.get())
                .pattern("CFC").pattern(" SP").pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get()).define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK).define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_AXE.get())
                .pattern("FC ").pattern("PS ").pattern("   ")
                .define('F', ModItems.FLINT_SHARD.get()).define('C', ModItems.FLINT_CHIP.get())
                .define('S', Items.STICK).define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SHOVEL.get())
                .pattern(" F ").pattern(" SP").pattern(" S ")
                .define('F', ModItems.FLINT_SHARD.get()).define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_KNIFE.get())
                .pattern(" C ").pattern("S  ").pattern("   ")
                .define('C', ModItems.FLINT_CHIP.get()).define('S', Items.STICK)
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLINT_SPEAR.get())
                .pattern("  C").pattern(" SP").pattern("S  ")
                .define('C', ModItems.FLINT_CHIP.get()).define('S', Items.STICK)
                .define('P', ModItems.PLANT_STRING.get())
                .unlockedBy("has_flint_chip", has(ModItems.FLINT_CHIP.get())).save(recipeOutput);

        // Random stuff
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIBER_MESH.get())
                .pattern("SF ").pattern("FS ").pattern("   ")
                .define('S', Items.STICK).define('F', ModItems.PLANT_STRING.get())
                .unlockedBy("has_plant_string", has(ModItems.PLANT_STRING.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PLANT_STRING.get())
                .requires(ModItems.PLANT_FIBER.get(), 3)
                .unlockedBy("has_plant_fiber", has(ModItems.PLANT_FIBER.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FIRE_STICK.get())
                .requires(Items.STICK, 2)
                .unlockedBy("has_sticks", has(Items.STICK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.CHARCOAL_BLOCK.get())
                .requires(Items.CHARCOAL, 9)
                .unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL , 9)
                .requires(ModItems.CHARCOAL_BLOCK, 1)
                .unlockedBy("has_charcoal_block", has(ModBlocks.CHARCOAL_BLOCK.get())).save(recipeOutput);

        damagingShapelessRecipe(recipeOutput, "straw_cord",
                new ItemStack(ModItems.STRAW_CORD.get(), 1),
                List.of(Ingredient.of(ModItems.STRAW.get()),
                        Ingredient.of(ModItems.STRAW.get())),
                Ingredient.of(ModTags.Items.KNIVES), 1);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUNDLE)
                .pattern(" S ").pattern(" L ").pattern("   ")
                .define('S', ModItems.STRAW_CORD.get()).define('L', Items.LEATHER)
                .unlockedBy("has_straw_cord", has(ModItems.STRAW_CORD.get())).save(recipeOutput);

        // Clay Stuff
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PORCELAIN_CLAY_BALL.get())
                .requires(Items.CLAY_BALL, 1)
                .requires(Items.BONE_MEAL, 2)
                .unlockedBy("has_bone_meal", has(Items.BONE_MEAL)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNFIRED_CLAY_BUCKET.get())
                .pattern("   ").pattern("C C").pattern(" C ")
                .define('C', Items.CLAY_BALL)
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PORCELAIN_BRICKS.get())
                .pattern("PP ").pattern("PP ").pattern("   ")
                .define('P', ModItems.PORCELAIN_CLAY_BRICK.get())
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PORCELAIN_BRICK_SLAB.get(), 6)
                .pattern("   ").pattern("PPP").pattern("   ")
                .define('P', ModItems.PORCELAIN_BRICKS.get())
                .unlockedBy("has_porcelain_clay_bricks", has(ModItems.PORCELAIN_BRICKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PORCELAIN_BRICK_STAIRS.get(), 4)
                .pattern("P  ").pattern("PP ").pattern("PPP")
                .define('P', ModItems.PORCELAIN_BRICKS.get())
                .unlockedBy("has_porcelain_clay_bricks", has(ModItems.PORCELAIN_BRICKS.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PORCELAIN_BRICK_WALL.get(), 6)
                .pattern("   ").pattern("PPP").pattern("PPP")
                .define('P', ModItems.PORCELAIN_BRICKS.get())
                .unlockedBy("has_porcelain_clay_bricks", has(ModItems.PORCELAIN_BRICKS.get())).save(recipeOutput);

        // Copper Tool Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SWORD.get())
                .pattern(" P ").pattern(" P ").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.COPPER_PLATE.get())
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_PICKAXE.get())
                .pattern("PCP").pattern(" S ").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT).define('P', ModItems.COPPER_PLATE.get())
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_AXE.get())
                .pattern("CP ").pattern("PS ").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT).define('P', ModItems.COPPER_PLATE.get())
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SHOVEL.get())
                .pattern(" P ").pattern(" S ").pattern(" S ")
                .define('S', Items.STICK).define('P', ModItems.COPPER_PLATE)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HOE.get())
                .pattern("PC ").pattern(" S ").pattern(" S ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT).define('P', ModItems.COPPER_PLATE.get())
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SPEAR.get())
                .pattern("  C").pattern(" S ").pattern("S  ")
                .define('S', Items.STICK).define('C', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);

        // Bone tool Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SPEAR.get())
                .pattern("  C").pattern(" S ").pattern("S  ")
                .define('S', Items.STICK).define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_SWORD.get())
                .pattern(" C ").pattern(" B ").pattern(" S ")
                .define('B', Items.BONE).define('S', Items.STICK)
                .define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BONE_CLUB.get())
                .pattern(" B ").pattern(" C ").pattern("   ")
                .define('B', Items.BONE).define('C', ModItems.SHARP_BONE)
                .unlockedBy("has_sharp_bone", has(ModItems.SHARP_BONE)).save(recipeOutput);
        // Amethyst Bone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SWORD.get())
                .pattern(" A ").pattern("ABA").pattern(" S ")
                .define('B', ModItems.BONE_SWORD).define('A', Items.AMETHYST_SHARD)
                .define('S', Items.STICK)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_BONE_SPEAR.get())
                .pattern(" AA").pattern(" BA").pattern("A  ")
                .define('B', ModItems.BONE_SPEAR).define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);
        // Golden Bone
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SWORD.get())
                .pattern(" A ").pattern("ABA").pattern(" S ")
                .define('B', ModItems.BONE_SWORD).define('A', ModItems.GOLD_PLATE)
                .define('S', Items.STICK)
                .unlockedBy("has_bone_sword", has(ModItems.BONE_SWORD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_BONE_SPEAR.get())
                .pattern(" AA").pattern(" BA").pattern("A  ")
                .define('B', ModItems.BONE_SPEAR).define('A', ModItems.GOLD_PLATE)
                .unlockedBy("has_bone_spear", has(ModItems.BONE_SPEAR)).save(recipeOutput);

        // Workstation Stuff
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FURNACE)
                .pattern("SSS").pattern("SCS").pattern("MMM")
                .define('S', Items.STONE).define('M', Items.SMOOTH_STONE).define('C', Items.COAL_BLOCK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath("minecraft", "furnace"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHOPPING_BLOCK.get())
                .pattern(" L ").pattern(" F ").pattern("   ")
                .define('L', ItemTags.LOGS).define('F', ModItems.FLINT_SHARD)
                .unlockedBy("has_flint_shard", has(ModItems.FLINT_SHARD)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PORCELAIN_KILN.get())
                .pattern("PPP").pattern("P P").pattern("BBB")
                .define('P', ModItems.PORCELAIN_CLAY_BRICK).define('B', Items.CLAY_BALL)
                .unlockedBy("has_porcelain_clay_brick", has(ModItems.PORCELAIN_CLAY_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WORKSTONE.get())
                .pattern(" S ").pattern(" L ").pattern("   ")
                .define('S', Blocks.SMOOTH_STONE).define('L', ItemTags.LOGS)
                .unlockedBy("has_smooth_stone", has(Blocks.SMOOTH_STONE)).save(recipeOutput);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.PORCELAIN_CLAY_BALL.get()), RecipeCategory.MISC,
                 ModItems.PORCELAIN_CLAY_BRICK.get(), 0.35f, 600                                        )
                .unlockedBy("has_porcelain_clay_ball", has(ModItems.PORCELAIN_CLAY_BALL.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CAMPFIRE_BASE.get())
                .pattern("PSP").pattern("S S").pattern("PSP")
                .define('P', ModTags.Items.POSTS).define('S', Items.STICK)
                .unlockedBy("has_any_post", has(ModTags.Items.POSTS))
                .save(recipeOutput);

        //Custom tools
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_KNIFE.get())
                .pattern(" C ").pattern("S  ").pattern("   ")
                .define('C', ModItems.COPPER_PLATE).define('S', Items.STICK)
                .unlockedBy("has_copper_plate", has(ModItems.COPPER_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_KNIFE.get())
                .pattern(" I ").pattern("S  ").pattern("   ")
                .define('S', Items.STICK).define('I', ModItems.IRON_PLATE)
                .unlockedBy("has_iron_plate", has(ModItems.IRON_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_KNIFE.get())
                .pattern(" D ").pattern("S  ").pattern("   ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_SAW.get())
                .pattern("SSS").pattern("LFC").pattern("   ")
                .define('C', ModItems.COPPER_PLATE ).define('S', Items.STICK)
                .define('F', Items.COPPER_INGOT).define('L', ItemTags.LOGS)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_SAW.get())
                .pattern("SSS").pattern("LII").pattern("   ")
                .define('S', Items.STICK).define('I', Items.IRON_INGOT)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_SAW.get())
                .pattern("SSS").pattern("LDD").pattern("   ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .define('L', ItemTags.LOGS)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_HAMMER.get())
                .pattern("BCC").pattern("CS ").pattern(" S ")
                .define('C', Items.COPPER_INGOT).define('S', Items.STICK)
                .define('B', Blocks.COPPER_BLOCK)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_HAMMER.get())
                .pattern("BII").pattern("IS ").pattern(" S ")
                .define('S', Items.STICK).define('I', ModItems.IRON_PLATE)
                .define('B', Blocks.IRON_BLOCK)
                .unlockedBy("has_iron_plate", has(ModItems.IRON_PLATE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_HAMMER.get())
                .pattern("BDD").pattern("DS ").pattern(" S ")
                .define('S', Items.STICK).define('D', Items.DIAMOND)
                .define('B', Blocks.DIAMOND_BLOCK)
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