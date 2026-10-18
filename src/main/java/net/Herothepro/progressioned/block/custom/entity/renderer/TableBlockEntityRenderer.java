package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.TableBlock;
import net.Herothepro.progressioned.block.custom.entity.TableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class TableBlockEntityRenderer implements BlockEntityRenderer<TableBlockEntity> {

    private static final float TABLE_HEIGHT = 0.9375f;
    private static final float ITEM_SCALE = 0.89f;

    private static final float RESULT_HEIGHT = TABLE_HEIGHT + 0.015f;

    private static final float FLUID_MAX_Y = TABLE_HEIGHT + 0.05f;

    public TableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TableBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack mold = entity.inventory.getStackInSlot(0);
        ItemStack output = entity.inventory.getStackInSlot(1);
        ItemStack renderResult = entity.renderResult;

        Direction facing = entity.getBlockState().hasProperty(TableBlock.FACING)
                ? entity.getBlockState().getValue(TableBlock.FACING)
                : Direction.NORTH;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.0D, 0.5D);
        float yRot = switch (facing) {
            case SOUTH -> 180f;
            case WEST -> 90f;
            case EAST -> 270f;
            default -> 0f;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.translate(-0.5D, 0.0D, -0.5D);

        float tableHeight = TABLE_HEIGHT;

        if (!mold.isEmpty()) {
            renderItemFlat(mold, poseStack, bufferSource, packedLight, packedOverlay, tableHeight + 0.002f, ITEM_SCALE, entity);
        }

        FluidStack fluidStack = entity.tank.getFluid();
        if (!fluidStack.isEmpty() && output.isEmpty() && renderResult.isEmpty()) {
            float fillPercentage = Math.min(1.0f, (float) fluidStack.getAmount() / (float) entity.fillTarget);

            boolean cooling = entity.isCooling();
            float coolingProgress = cooling ? entity.getCoolingProgress(partialTick) : 0.0f;

            if (cooling) {
                ItemStack preview = entity.getPreviewResult();
                if (!preview.isEmpty()) {
                    renderItemFlat(preview, poseStack, bufferSource, packedLight, packedOverlay, RESULT_HEIGHT, ITEM_SCALE, entity);
                }
            }

            float fluidMinY = tableHeight + 0.002f;
            float fluidHeight = fluidMinY + (FLUID_MAX_Y - fluidMinY) * fillPercentage;
            float fluidAlpha = cooling ? Math.max(0.0f, 1.0f - coolingProgress) : 1.0f;
            if (fluidAlpha > 0.0f) {
                VertexConsumer builder = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());
                FluidRenderHelper.renderFluidBox(fluidStack, 0.0625f, fluidMinY, 0.0625f, 0.9375f, fluidHeight, 0.9375f, builder, poseStack, packedLight, true, fluidAlpha);
            }
        }

        if (!output.isEmpty() || !renderResult.isEmpty()) {
            ItemStack itemToDraw = !output.isEmpty() ? output : renderResult;
            renderItemFlat(itemToDraw, poseStack, bufferSource, packedLight, packedOverlay, RESULT_HEIGHT, ITEM_SCALE, entity);
        }

        poseStack.popPose();
    }

    private void renderItemFlat(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, float height, float scale, TableBlockEntity entity) {
        poseStack.pushPose();
        poseStack.translate(0.5D, height, 0.5D);
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));
        poseStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, light, overlay, poseStack, buffer, entity.getLevel(), 0
        );
        poseStack.popPose();
    }
}