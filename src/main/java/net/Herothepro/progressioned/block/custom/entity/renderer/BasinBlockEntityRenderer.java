package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.entity.BasinBlockEntity;
import net.Herothepro.progressioned.recipe.CastingBasinRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class BasinBlockEntityRenderer implements BlockEntityRenderer<BasinBlockEntity> {

    private static final float CAVITY_MIN = 2f / 16f;
    private static final float CAVITY_MAX = 14f / 16f;
    private static final float FLOOR_Y = 2f / 16f;

    private static final float BLOCK_SIZE = 0.73f;

    private static final float FLUID_INSET = 0.004f;
    private static final float FLUID_MAX_Y = 0.92f;

    public BasinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BasinBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack output = entity.inventory.getStackInSlot(0);
        FluidStack fluidStack = entity.tank.getFluid();

        CastingBasinRecipe matchedRecipe = entity.findMatchingRecipe(fluidStack);

        boolean hasOutput = !output.isEmpty();
        boolean cooling = !hasOutput && !fluidStack.isEmpty() && entity.isCooling();

        float progress = 0.0f;
        if (cooling) {
            progress = entity.getCoolingProgress(partialTick);
        }

        float fluidAlpha = hasOutput ? 0.0f : 1.0f - progress;

        poseStack.pushPose();

        ItemStack renderItem = ItemStack.EMPTY;
        if (hasOutput) {
            renderItem = output;
        } else if (cooling) {
            renderItem = entity.cachedRecipeOutput;
            if (renderItem.isEmpty() && matchedRecipe != null) {
                renderItem = matchedRecipe.output();
            }
        }

        if (!renderItem.isEmpty()) {
            poseStack.pushPose();

            if (renderItem.getItem() instanceof BlockItem blockItem) {
                poseStack.translate(0.5D, FLOOR_Y + 0.002D, 0.5D);
                poseStack.scale(BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                poseStack.translate(-0.5D, 0.0D, -0.5D);

                BlockState blockState = blockItem.getBlock().defaultBlockState();
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                        blockState, poseStack, bufferSource, packedLight, packedOverlay,
                        net.neoforged.neoforge.client.model.data.ModelData.EMPTY, null
                );
            } else {
                poseStack.translate(0.5D, FLOOR_Y + 0.01D, 0.5D);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.mulPose(Axis.XP.rotationDegrees(90f));

                Minecraft.getInstance().getItemRenderer().renderStatic(
                        renderItem, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, entity.getLevel(), 0
                );
            }

            poseStack.popPose();
        }

        if (!fluidStack.isEmpty() && fluidAlpha > 0.0f) {
            int recipeAmount = (matchedRecipe != null) ? matchedRecipe.input().getAmount() : 1000;
            float fillPercentage = Math.min(1f, (float) fluidStack.getAmount() / recipeAmount);

            float minX = CAVITY_MIN + FLUID_INSET, maxX = CAVITY_MAX - FLUID_INSET;
            float minZ = CAVITY_MIN + FLUID_INSET, maxZ = CAVITY_MAX - FLUID_INSET;
            float minY = FLOOR_Y + FLUID_INSET;
            float currentMaxY = minY + ((FLUID_MAX_Y - minY) * fillPercentage);

            VertexConsumer builder = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());

            FluidRenderHelper.renderFluidBox(
                    fluidStack,
                    minX, minY, minZ,
                    maxX, currentMaxY, maxZ,
                    builder, poseStack, packedLight, false, fluidAlpha
            );
        }

        poseStack.popPose();
    }
}