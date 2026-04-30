package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.entity.ChoppingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ChoppingBlockRenderer implements BlockEntityRenderer<ChoppingBlockEntity> {
    public ChoppingBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ChoppingBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack log = be.getStoredLog();
        if (log.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.60, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(0)); // lay log on its side
        poseStack.scale(1f, 1f, 1f);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                log, ItemDisplayContext.FIXED,
                packedLight, packedOverlay,
                poseStack, bufferSource, be.getLevel(), 0);

        poseStack.popPose();
    }
}