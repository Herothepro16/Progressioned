package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.Herothepro.progressioned.block.custom.DryingRackBlock;
import net.Herothepro.progressioned.block.custom.entity.DryingRackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DryingRackBlockEntityRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

    private static final float BAR_BOTTOM_Y = 10f / 16f;
    private static final float BAR_CENTER_Z = 12f / 16f;

    private static final float ITEM_Y_OFFSET = 0.0625f;

    private static final float ITEM_SCALE = 0.650f;

    public DryingRackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(DryingRackBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity.getLevel() == null) return;

        ItemStack stack = entity.inventory.getStackInSlot(0);
        if (stack.isEmpty()) return;

        Direction facing = entity.getBlockState().hasProperty(DryingRackBlock.FACING)
                ? entity.getBlockState().getValue(DryingRackBlock.FACING)
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

        poseStack.translate(0.5D, BAR_BOTTOM_Y - ITEM_SCALE / 2.0 - 0.01 + ITEM_Y_OFFSET, BAR_CENTER_Z);
        poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, entity.getLevel(), 0
        );

        poseStack.popPose();
    }
}
