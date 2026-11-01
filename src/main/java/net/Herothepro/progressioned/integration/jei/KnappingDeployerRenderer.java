package net.Herothepro.progressioned.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.foundation.gui.CustomLightingSettings;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.gui.ILightingSettings;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class KnappingDeployerRenderer {

    private static final ILightingSettings LIGHTING = CustomLightingSettings.builder()
            .firstLightRotation(12.5f, -45.0f)
            .secondLightRotation(-20.0f, -50.0f)
            .build();

    private static final int SCALE = 20;

    private static final float HAND_TRAVEL = 17f - 3.75f;

    private KnappingDeployerRenderer() {}

    private static GuiGameElement.GuiRenderBuilder element(BlockState state) {
        return GuiGameElement.of(state).lighting(LIGHTING);
    }

    private static GuiGameElement.GuiRenderBuilder element(PartialModel partial) {
        return GuiGameElement.of(partial).lighting(LIGHTING);
    }

    public static void draw(GuiGraphics graphics, int xOffset, int yOffset, BlockState target) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(xOffset, yOffset, 100);
        pose.mulPose(Axis.XP.rotationDegrees(-15.5f));
        pose.mulPose(Axis.YP.rotationDegrees(22.5f));

        float angle = (AnimationTickHolder.getRenderTime() * 4f) % 360;
        element(AllBlocks.SHAFT.getDefaultState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z))
                .rotateBlock(0, 0, angle)
                .scale(SCALE)
                .render(graphics);

        element(AllBlocks.DEPLOYER.getDefaultState()
                .setValue(DeployerBlock.FACING, Direction.DOWN)
                .setValue(DeployerBlock.AXIS_ALONG_FIRST_COORDINATE, false))
                .scale(SCALE)
                .render(graphics);

        float cycle = AnimationTickHolder.getRenderTime() % 30;
        float handOffset = cycle < 10 ? cycle / 10f : cycle < 20 ? (20 - cycle) / 10f : 0;

        pose.pushPose();
        pose.translate(0, handOffset * HAND_TRAVEL, 0);
        element(AllPartialModels.DEPLOYER_POLE)
                .rotateBlock(90, 0, 0)
                .scale(SCALE)
                .render(graphics);
        element(AllPartialModels.DEPLOYER_HAND_HOLDING)
                .rotateBlock(90, 0, 0)
                .scale(SCALE)
                .render(graphics);
        pose.popPose();

        element(target)
                .atLocal(0, 2, 0)
                .scale(SCALE)
                .render(graphics);

        pose.popPose();
    }
}