package net.Herothepro.progressioned.block.custom.entity;

import net.Herothepro.progressioned.ModConfig;
import net.Herothepro.progressioned.block.custom.KilnBlock;
import net.Herothepro.progressioned.recipe.KilnRecipe;
import net.Herothepro.progressioned.recipe.ModRecipes;
import net.Herothepro.progressioned.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class KilnBlockEntity extends BlockEntity {

    private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    private int cookTime = 0;
    public static final int COOK_TIME_TOTAL = 300;

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BE.get(), pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public static boolean isAboveFire(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        boolean isFire = below.is(ModTags.Blocks.KILN_HEATING_BLOCK);

        if (ModConfig.ALLOW_CAMPFIRE_HEATING.get()) {
            boolean isLitCampfire = below.is(BlockTags.CAMPFIRES)
                    && below.hasProperty(BlockStateProperties.LIT)
                    && below.getValue(BlockStateProperties.LIT);

            return isFire || isLitCampfire;
        }

        return isFire;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, KilnBlockEntity be) {
        if (level.isClientSide) return;

        boolean hasFire = isAboveFire(level, pos);

        boolean wasLit = state.getValue(KilnBlock.LIT);
        if (hasFire != wasLit) {
            state = state.setValue(KilnBlock.LIT, hasFire);
            level.setBlock(pos, state, 3);
        }

        if (!hasFire) {
            be.cookTime = 0;
            return;
        }
        if (be.input.isEmpty()) return;

        Optional<RecipeHolder<KilnRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KILN_TYPE.get(),
                        new SingleRecipeInput(be.input.copyWithCount(1)), level);

        if (recipe.isEmpty()) {
            be.cookTime = 0;
            return;
        }

        be.cookTime++;

        if (be.cookTime >= recipe.get().value().getCookTime()) {
            be.cookTime = 0;

            ItemStack result = recipe.get().value()
                    .getResultItem(level.registryAccess()).copy();

            if (be.output.isEmpty()) {
                be.output = result;
            } else if (ItemStack.isSameItemSameComponents(be.output, result)
                    && be.output.getCount() < be.output.getMaxStackSize()) {
                be.output.grow(result.getCount());
            } else {
                be.cookTime = COOK_TIME_TOTAL;
                return;
            }

            be.input.shrink(1);
            if (be.input.isEmpty()) be.input = ItemStack.EMPTY;

            be.sync();
        }
    }

    public int addInput(ItemStack stack, Level level) {
        if (stack.isEmpty()) return 0;

        boolean hasRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KILN_TYPE.get(),
                        new SingleRecipeInput(stack.copyWithCount(1)), level)
                .isPresent();

        if (!hasRecipe) return -1;

        if (input.isEmpty()) {
            int toAdd = Math.min(stack.getCount(), 16);
            input = stack.copyWithCount(toAdd);
            sync();
            return toAdd;
        }

        if (ItemStack.isSameItemSameComponents(input, stack)) {
            int canAdd = 16 - input.getCount();
            if (canAdd <= 0) return 0;
            int toAdd = Math.min(canAdd, stack.getCount());
            input.grow(toAdd);
            sync();
            return toAdd;
        }

        return 0;
    }

    public ItemStack extractOutput() {
        ItemStack out = output.copy();
        output = ItemStack.EMPTY;
        sync();
        return out;
    }

    public ItemStack extractInput() {
        ItemStack out = input.copy();
        input = ItemStack.EMPTY;
        cookTime = 0;
        sync();
        return out;
    }

    public void dropContents(Level level, BlockPos pos) {
        if (!input.isEmpty())
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), input);
        if (!output.isEmpty())
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), output);
        input  = ItemStack.EMPTY;
        output = ItemStack.EMPTY;
    }

    public ItemStack getInput()    { return input;    }
    public ItemStack getOutput()   { return output;   }
    public int       getCookTime() { return cookTime; }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!input.isEmpty())  tag.put("Input",  input.save(registries));
        if (!output.isEmpty()) tag.put("Output", output.save(registries));
        tag.putInt("CookTime", cookTime);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        input  = tag.contains("Input")
                ? ItemStack.parse(registries, tag.getCompound("Input")).orElse(ItemStack.EMPTY)
                : ItemStack.EMPTY;
        output = tag.contains("Output")
                ? ItemStack.parse(registries, tag.getCompound("Output")).orElse(ItemStack.EMPTY)
                : ItemStack.EMPTY;
        cookTime = tag.getInt("CookTime");
    }
}