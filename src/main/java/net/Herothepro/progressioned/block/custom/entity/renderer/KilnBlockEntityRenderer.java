package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.entity.KilnBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KilnBlockEntityRenderer implements BlockEntityRenderer<KilnBlockEntity> {

    public KilnBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(KilnBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        Direction facing = be.getBlockState().getValue(
                net.Herothepro.progressioned.block.custom.KilnBlock.FACING);

        Level level = be.getLevel();
        int light = packedLight;
        if (level != null) {
            light = LightTexture.FULL_BRIGHT;
        }

        ItemStack input = be.getInput();
        if (!input.isEmpty()) {
            poseStack.pushPose();
            translateForFacing(poseStack, facing, 0.15f);
            rotateToFace(poseStack, facing);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    input, ItemDisplayContext.FIXED,
                    light, packedOverlay,
                    poseStack, bufferSource, level, 0);
            poseStack.popPose();
        }

        ItemStack output = be.getOutput();
        if (!output.isEmpty()) {
            poseStack.pushPose();
            translateForFacing(poseStack, facing, -0.15f);
            rotateToFace(poseStack, facing);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    output, ItemDisplayContext.FIXED,
                    light, packedOverlay,
                    poseStack, bufferSource, level, 0);
            poseStack.popPose();
        }
    }

    private void translateForFacing(PoseStack poseStack, Direction facing, float sideOffset) {
        float holeY = 0.65f;
        float holeDepth = 0.156f;

        switch (facing) {
            case NORTH -> poseStack.translate(0.5f + sideOffset, holeY, holeDepth);
            case SOUTH -> poseStack.translate(0.5f - sideOffset, holeY, 1f - holeDepth);
            case WEST  -> poseStack.translate(holeDepth, holeY, 0.5f - sideOffset);
            case EAST  -> poseStack.translate(1f - holeDepth, holeY, 0.5f + sideOffset);
            default    -> poseStack.translate(0.5f, holeY, 0.5f);
        }
    }

    private void rotateToFace(PoseStack poseStack, Direction facing) {
        switch (facing) {
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            case SOUTH -> {}
            case WEST  -> poseStack.mulPose(Axis.YP.rotationDegrees(90f));
            case EAST  -> poseStack.mulPose(Axis.YP.rotationDegrees(-90f));
        }
    }
}