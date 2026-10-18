package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Herothepro.progressioned.block.custom.entity.MelterBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class MelterBlockEntityRenderer implements BlockEntityRenderer<MelterBlockEntity> {

    public MelterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(MelterBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        FluidStack fluidStack = blockEntity.tank.getFluid();
        if (fluidStack.isEmpty()) return;

        IClientFluidTypeExtensions clientFluid = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = clientFluid.getStillTexture(fluidStack);
        if (stillTexture == null) return;

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int color = clientFluid.getTintColor(fluidStack);

        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float minX = 0.04f;
        float maxX = 0.96f;
        float minZ = 0.04f;
        float maxZ = 0.96f;

        float minY = 0.5f;
        float maxPossibleY = 0.99f;

        float fillRatio = Math.min(1.0f, (float) fluidStack.getAmount() / blockEntity.tank.getCapacity());
        float maxY = minY + (maxPossibleY - minY) * fillRatio;
        float height = maxY - minY;

        VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        float wallV0 = v1 - (v1 - v0) * height;

        addVertex(builder, matrix, minX, maxY, minZ, r, g, b, a, u0, v0, packedLight);
        addVertex(builder, matrix, minX, maxY, maxZ, r, g, b, a, u0, v1, packedLight);
        addVertex(builder, matrix, maxX, maxY, maxZ, r, g, b, a, u1, v1, packedLight);
        addVertex(builder, matrix, maxX, maxY, minZ, r, g, b, a, u1, v0, packedLight);

        addVertex(builder, matrix, minX, minY, minZ, r, g, b, a, u0, v1, packedLight);
        addVertex(builder, matrix, minX, maxY, minZ, r, g, b, a, u0, wallV0, packedLight);
        addVertex(builder, matrix, maxX, maxY, minZ, r, g, b, a, u1, wallV0, packedLight);
        addVertex(builder, matrix, maxX, minY, minZ, r, g, b, a, u1, v1, packedLight);

        addVertex(builder, matrix, maxX, minY, maxZ, r, g, b, a, u0, v1, packedLight);
        addVertex(builder, matrix, maxX, maxY, maxZ, r, g, b, a, u0, wallV0, packedLight);
        addVertex(builder, matrix, minX, maxY, maxZ, r, g, b, a, u1, wallV0, packedLight);
        addVertex(builder, matrix, minX, minY, maxZ, r, g, b, a, u1, v1, packedLight);

        addVertex(builder, matrix, minX, minY, maxZ, r, g, b, a, u0, v1, packedLight);
        addVertex(builder, matrix, minX, maxY, maxZ, r, g, b, a, u0, wallV0, packedLight);
        addVertex(builder, matrix, minX, maxY, minZ, r, g, b, a, u1, wallV0, packedLight);
        addVertex(builder, matrix, minX, minY, minZ, r, g, b, a, u1, v1, packedLight);

        addVertex(builder, matrix, maxX, minY, minZ, r, g, b, a, u0, v1, packedLight);
        addVertex(builder, matrix, maxX, maxY, minZ, r, g, b, a, u0, wallV0, packedLight);
        addVertex(builder, matrix, maxX, maxY, maxZ, r, g, b, a, u1, wallV0, packedLight);
        addVertex(builder, matrix, maxX, minY, maxZ, r, g, b, a, u1, v1, packedLight);
    }

    private void addVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z,
                           float r, float g, float b, float a, float u, float v, int light) {
        builder.addVertex(matrix, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setOverlay(0)
                .setLight(light)
                .setNormal(0, 1, 0);
    }
}