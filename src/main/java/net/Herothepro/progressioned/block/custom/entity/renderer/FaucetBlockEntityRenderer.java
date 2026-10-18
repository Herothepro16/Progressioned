package net.Herothepro.progressioned.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.Herothepro.progressioned.block.custom.FaucetBlock;
import net.Herothepro.progressioned.block.custom.TableBlock;
import net.Herothepro.progressioned.block.custom.entity.FaucetBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class FaucetBlockEntityRenderer implements BlockEntityRenderer<FaucetBlockEntity> {

    private static final float STREAM_END_DEFAULT = -0.75f;
    private static final float STREAM_END_TABLE = -0.065f;

    public FaucetBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FaucetBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!entity.isPouring() || entity.getRenderFluid().isEmpty()) return;

        FluidStack fluidStack = entity.getRenderFluid();
        IClientFluidTypeExtensions clientFluid = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        var atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);

        TextureAtlasSprite flowingSprite = atlas.apply(clientFluid.getFlowingTexture(fluidStack));
        TextureAtlasSprite stillSprite = atlas.apply(clientFluid.getStillTexture(fluidStack));

        int color = clientFluid.getTintColor(fluidStack);
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        Level level = entity.getLevel();
        int fluidLuminosity = fluidStack.getFluid().getFluidType().getLightLevel(fluidStack);
        int blockLight = Math.max(packedLight & 0xFFFF, fluidLuminosity << 4);
        int skyLight = (packedLight >> 16) & 0xFFFF;
        int light = blockLight | (skyLight << 16);

        VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        Direction facing = entity.getBlockState().hasProperty(FaucetBlock.FACING)
                ? entity.getBlockState().getValue(FaucetBlock.FACING)
                : Direction.NORTH;

        float hMinX = 0.375f, hMaxX = 0.625f;
        float hMinY = 0.3125f, hMaxY = 0.500f;
        float hMinZ = 0.375f, hMaxZ = 0.625f;

        switch (facing) {
            case NORTH -> { hMinZ = 0.500f; hMaxZ = 1.000f; }
            case SOUTH -> { hMinZ = 0.000f; hMaxZ = 0.500f; }
            case WEST  -> { hMinX = 0.500f; hMaxX = 1.000f; }
            case EAST  -> { hMinX = 0.000f; hMaxX = 0.500f; }
            default    -> {}
        }

        renderCuboid(builder, matrix, hMinX, hMinY, hMinZ, hMaxX, hMaxY, hMaxZ, flowingSprite, stillSprite, facing, r, g, b, a, light);

        float vMinX = 0.375f, vMaxX = 0.625f;
        float vMinZ = 0.375f, vMaxZ = 0.625f;

        switch (facing) {
            case NORTH -> { vMinZ = 0.500f; vMaxZ = 0.625f; }
            case SOUTH -> { vMinZ = 0.375f; vMaxZ = 0.500f; }
            case WEST  -> { vMinX = 0.500f; vMaxX = 0.625f; }
            case EAST  -> { vMinX = 0.375f; vMaxX = 0.500f; }
            default    -> {}
        }

        float vMaxY = 0.3125f;
        float vMinY = getStreamEndY(level, entity);

        renderCuboid(builder, matrix, vMinX, vMinY, vMinZ, vMaxX, vMaxY, vMaxZ, flowingSprite, stillSprite, Direction.DOWN, r, g, b, a, light);
    }

    private static float getStreamEndY(Level level, FaucetBlockEntity entity) {
        if (level == null) return STREAM_END_DEFAULT;
        Block below = level.getBlockState(entity.getBlockPos().below()).getBlock();
        if (below instanceof TableBlock) return STREAM_END_TABLE;
        return STREAM_END_DEFAULT;
    }

    private static final float[][] FACE_NORMALS = {
            {0, 1, 0}, {0, -1, 0},
            {0, 0, -1}, {0, 0, 1},
            {-1, 0, 0}, {1, 0, 0}
    };
    private static final Direction.Axis[] FACE_AXES = {
            Direction.Axis.Y, Direction.Axis.Y,
            Direction.Axis.Z, Direction.Axis.Z,
            Direction.Axis.X, Direction.Axis.X
    };

    private void renderCuboid(VertexConsumer builder, Matrix4f matrix,
                              float x0, float y0, float z0, float x1, float y1, float z1,
                              TextureAtlasSprite flowing, TextureAtlasSprite still, Direction flow,
                              float r, float g, float b, float a, int light) {
        float[][][] faces = {
                {{x0, y1, z0}, {x0, y1, z1}, {x1, y1, z1}, {x1, y1, z0}},
                {{x0, y0, z1}, {x0, y0, z0}, {x1, y0, z0}, {x1, y0, z1}},
                {{x1, y1, z0}, {x1, y0, z0}, {x0, y0, z0}, {x0, y1, z0}},
                {{x0, y1, z1}, {x0, y0, z1}, {x1, y0, z1}, {x1, y1, z1}},
                {{x0, y1, z0}, {x0, y0, z0}, {x0, y0, z1}, {x0, y1, z1}},
                {{x1, y1, z1}, {x1, y0, z1}, {x1, y0, z0}, {x1, y1, z0}}
        };

        Direction.Axis flowAxis = flow.getAxis();
        boolean positive = flow.getAxisDirection() == Direction.AxisDirection.POSITIVE;

        for (int f = 0; f < faces.length; f++) {
            Direction.Axis normal = FACE_AXES[f];
            float[] n = FACE_NORMALS[f];
            for (float[] p : faces[f]) {
                float u, v;
                if (normal == flowAxis) {
                    float[] uv = inPlane(p, normal);
                    u = lerp(still.getU0(), still.getU1(), uv[0]);
                    v = lerp(still.getV0(), still.getV1(), uv[1]);
                } else {
                    float along = positive ? coord(p, flowAxis) : 1.0f - coord(p, flowAxis);
                    float across = coord(p, otherAxis(normal, flowAxis));
                    u = lerp(flowing.getU0(), flowing.getU1(), across * 0.5f);
                    v = lerp(flowing.getV0(), flowing.getV1(), along * 0.5f);
                }
                addVertex(builder, matrix, p[0], p[1], p[2], u, v, r, g, b, a, light, n[0], n[1], n[2]);
            }
        }
    }

    private static float coord(float[] p, Direction.Axis axis) {
        return switch (axis) {
            case X -> p[0];
            case Y -> p[1];
            case Z -> p[2];
        };
    }

    private static Direction.Axis otherAxis(Direction.Axis a, Direction.Axis b) {
        for (Direction.Axis axis : Direction.Axis.values()) {
            if (axis != a && axis != b) return axis;
        }
        return a;
    }

    private static float[] inPlane(float[] p, Direction.Axis normal) {
        return switch (normal) {
            case X -> new float[]{p[2], 1.0f - p[1]};
            case Y -> new float[]{p[0], p[2]};
            case Z -> new float[]{p[0], 1.0f - p[1]};
        };
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private void addVertex(VertexConsumer builder, Matrix4f matrix, float x, float y, float z, float u, float v, float r, float g, float b, float a, int light, float nx, float ny, float nz) {
        builder.addVertex(matrix, x, y, z)
                .setColor((int)(r * 255f), (int)(g * 255f), (int)(b * 255f), (int)(a * 255f))
                .setUv(u, v)
                .setOverlay(0)
                .setLight(light)
                .setNormal(nx, ny, nz);
    }
}