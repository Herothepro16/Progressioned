package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public final class FluidRenderHelper {

    public static int getColor(FluidStack stack) {
        IClientFluidTypeExtensions extension = IClientFluidTypeExtensions.of(stack.getFluid());
        return extension.getTintColor(stack);
    }

    public static int getLuminosity(FluidStack fluid) {
        return fluid.getFluid().getFluidType().getLightLevel(fluid);
    }

    public static TextureAtlasSprite getStillTexture(FluidStack fluid) {
        ResourceLocation id = IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(fluid);
        return id == null ? null : Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(id);
    }

    public static TextureAtlasSprite getStillTextureOrMissing(FluidStack fluid) {
        TextureAtlasSprite texture = getStillTexture(fluid);
        if (texture != null) return texture;
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
    }

    public static void renderFluidBox(FluidStack fluid, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax,
                                      VertexConsumer builder, PoseStack ms, int light, boolean renderBottom) {
        renderFluidBox(fluid, xMin, yMin, zMin, xMax, yMax, zMax, builder, ms, light, renderBottom, 1.0f);
    }

    public static void renderFluidBox(FluidStack fluid, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax,
                                      VertexConsumer builder, PoseStack ms, int light, boolean renderBottom, float alphaMultiplier) {
        TextureAtlasSprite fluidTexture = getStillTextureOrMissing(fluid);
        int color = getColor(fluid);

        int a = (int) (((color >> 24) & 0xFF) * alphaMultiplier);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int modifiedColor = (a << 24) | (r << 16) | (g << 8) | b;

        int blockLightIn = (light >> 4) & 0xF;
        int luminosity = Math.max(blockLightIn, getLuminosity(fluid));
        light = (light & 0xF00000) | luminosity << 4;

        for (Direction side : Direction.values()) {
            if (side == Direction.DOWN && !renderBottom) continue;

            boolean positive = side.getAxisDirection() == Direction.AxisDirection.POSITIVE;
            if (side.getAxis().isHorizontal()) {
                if (side.getAxis() == Direction.Axis.X) {
                    renderStillTiledFace(side, zMin, yMin, zMax, yMax, positive ? xMax : xMin, builder, ms, light, modifiedColor, fluidTexture);
                } else {
                    renderStillTiledFace(side, xMin, yMin, xMax, yMax, positive ? zMax : zMin, builder, ms, light, modifiedColor, fluidTexture);
                }
            } else {
                renderStillTiledFace(side, xMin, zMin, xMax, zMax, positive ? yMax : yMin, builder, ms, light, modifiedColor, fluidTexture);
            }
        }
    }

    public static void renderStillTiledFace(Direction dir, float left, float down, float right, float up,
                                            float depth, VertexConsumer builder, PoseStack ms, int light, int color, TextureAtlasSprite texture) {
        boolean positive = dir.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        boolean horizontal = dir.getAxis().isHorizontal();
        boolean x = dir.getAxis() == Direction.Axis.X;

        float shrink = texture.uvShrinkRatio() * 0.25f;
        float centerU = texture.getU0() + (texture.getU1() - texture.getU0()) * 0.5f;
        float centerV = texture.getV0() + (texture.getV1() - texture.getV0()) * 0.5f;

        float f;
        float x2 = 0;
        float y2 = 0;
        float u1, u2;
        float v1, v2;
        for (float x1 = left; x1 < right; x1 = x2) {
            f = Mth.floor(x1);
            x2 = Math.min(f + 1, right);
            if (dir == Direction.NORTH || dir == Direction.EAST) {
                f = Mth.ceil(x2);
                u1 = texture.getU((f - x2));
                u2 = texture.getU((f - x1));
            } else {
                u1 = texture.getU((x1 - f));
                u2 = texture.getU((x2 - f));
            }
            u1 = Mth.lerp(shrink, u1, centerU);
            u2 = Mth.lerp(shrink, u2, centerU);
            for (float y1 = down; y1 < up; y1 = y2) {
                f = Mth.floor(y1);
                y2 = Math.min(f + 1, up);
                if (dir == Direction.UP) {
                    v1 = texture.getV((y1 - f));
                    v2 = texture.getV((y2 - f));
                } else {
                    f = Mth.ceil(y2);
                    v1 = texture.getV((f - y2));
                    v2 = texture.getV((f - y1));
                }
                v1 = Mth.lerp(shrink, v1, centerV);
                v2 = Mth.lerp(shrink, v2, centerV);

                if (horizontal) {
                    if (x) {
                        putVertex(builder, ms, depth, y2, positive ? x2 : x1, color, u1, v1, dir, light);
                        putVertex(builder, ms, depth, y1, positive ? x2 : x1, color, u1, v2, dir, light);
                        putVertex(builder, ms, depth, y1, positive ? x1 : x2, color, u2, v2, dir, light);
                        putVertex(builder, ms, depth, y2, positive ? x1 : x2, color, u2, v1, dir, light);
                    } else {
                        putVertex(builder, ms, positive ? x1 : x2, y2, depth, color, u1, v1, dir, light);
                        putVertex(builder, ms, positive ? x1 : x2, y1, depth, color, u1, v2, dir, light);
                        putVertex(builder, ms, positive ? x2 : x1, y1, depth, color, u2, v2, dir, light);
                        putVertex(builder, ms, positive ? x2 : x1, y2, depth, color, u2, v1, dir, light);
                    }
                } else {
                    putVertex(builder, ms, x1, depth, positive ? y1 : y2, color, u1, v1, dir, light);
                    putVertex(builder, ms, x1, depth, positive ? y2 : y1, color, u1, v2, dir, light);
                    putVertex(builder, ms, x2, depth, positive ? y2 : y1, color, u2, v2, dir, light);
                    putVertex(builder, ms, x2, depth, positive ? y1 : y2, color, u2, v1, dir, light);
                }
            }
        }
    }

    private static void putVertex(VertexConsumer builder, PoseStack ms, float x, float y, float z, int color, float u,
                                  float v, Direction face, int light) {
        Vec3i normal = face.getNormal();
        PoseStack.Pose peek = ms.last();
        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        builder.addVertex(peek.pose(), x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setOverlay(0)
                .setLight(light)
                .setNormal(peek.copy(), normal.getX(), normal.getY(), normal.getZ());
    }
}