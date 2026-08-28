package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.WorkstoneBlock;
import net.Herothepro.progressioned.block.custom.entity.WorkstoneBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WorkstoneBlockEntityRenderer implements BlockEntityRenderer<WorkstoneBlockEntity> {
    private final ItemRenderer itemRenderer;

    public WorkstoneBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(WorkstoneBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        int hits = entity.getHits();

        Direction facing = Direction.NORTH;
        if (entity.getBlockState().hasProperty(WorkstoneBlock.FACING)) {
            facing = entity.getBlockState().getValue(WorkstoneBlock.FACING);
        }

        float inputRotation = 180.0f;
        float outputRotation = 180.0f;

        ItemStack inputStack = entity.getInput();
        if (!inputStack.isEmpty()) {
            int count = inputStack.getCount();
            int layers = Math.min(4, (count + 3) / 4);

            float hitAnimAngle = (hits % 2 == 1) ? -15.0f : 0.0f;
            double hitYBounce = (hits % 2 == 1) ? 0.015 : 0.0;

            for (int i = 0; i < layers; i++) {
                poseStack.pushPose();

                poseStack.translate(0.5, 0.635 + (i * 0.02) + hitYBounce, 0.5);

                poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

                double xOffset = -0.12 + ((i % 2 == 0) ? 0.0 : 0.02);
                double zOffset = 0.10 + ((i > 1) ? 0.02 : -0.01);
                poseStack.translate(xOffset, 0.0, zOffset);

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));

                poseStack.mulPose(Axis.ZP.rotationDegrees(inputRotation + hitAnimAngle));

                poseStack.scale(0.45f, 0.45f, 0.45f);

                this.itemRenderer.renderStatic(
                        inputStack,
                        ItemDisplayContext.FIXED,
                        packedLight,
                        packedOverlay,
                        poseStack,
                        bufferSource,
                        entity.getLevel(),
                        (int) entity.getBlockPos().asLong() + i
                );

                poseStack.popPose();
            }
        }

        ItemStack outputStack = entity.getOutput();
        if (!outputStack.isEmpty()) {
            int count = outputStack.getCount();
            int layers = Math.min(4, (count + 3) / 4);

            for (int i = 0; i < layers; i++) {
                poseStack.pushPose();

                poseStack.translate(0.5, 0.635 + (i * 0.02), 0.5);

                poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

                poseStack.translate(0.18, 0.0, -0.18);

                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));

                poseStack.mulPose(Axis.ZP.rotationDegrees(outputRotation));

                poseStack.scale(0.40f, 0.40f, 0.40f);

                this.itemRenderer.renderStatic(
                        outputStack,
                        ItemDisplayContext.FIXED,
                        packedLight,
                        packedOverlay,
                        poseStack,
                        bufferSource,
                        entity.getLevel(),
                        (int) entity.getBlockPos().asLong() + 100 + i
                );

                poseStack.popPose();
            }
        }
    }
}