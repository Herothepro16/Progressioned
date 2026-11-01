package net.Herothepro.progressioned.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class FireStickClientExtensions implements IClientItemExtensions {
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
                                           ItemStack itemInHand, float partialTick,
                                           float equipProcess, float swingProcess) {
        if (!player.isUsingItem() || !(player.getUseItem().getItem() instanceof FireStickItem)) {
            return false;
        }

        int side = arm == HumanoidArm.RIGHT ? 1 : -1;
        float time = player.getTicksUsingItem() + partialTick;

        float t = time + 3.0f;
        if (arm != player.getMainArm()) t += 6.0f;
        t = t % 12.0f;
        float swing = t < 6.0f ? t / 6.0f : 0.0f;

        float sqrtSwing = Mth.sqrt(swing);
        float x = -0.4f * Mth.sin(sqrtSwing * (float) Math.PI);
        float y = 0.2f * Mth.sin(sqrtSwing * (float) Math.PI * 2.0f);
        float z = -0.2f * Mth.sin(swing * (float) Math.PI);
        poseStack.translate(side * x, y, z);

        poseStack.translate(side * 0.56f, -0.52f + equipProcess * -0.6f, -0.72f);

        float f = Mth.sin(swing * swing * (float) Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(side * (45.0f + f * -20.0f)));
        float f1 = Mth.sin(sqrtSwing * (float) Math.PI);
        poseStack.mulPose(Axis.ZP.rotationDegrees(side * f1 * -20.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -80.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(side * -45.0f));
        return true;
    }
}