package net.Herothepro.progressioned.util.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Herothepro.progressioned.Progressioned;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class MelterScreen extends AbstractContainerScreen<MelterMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Progressioned.MOD_ID, "textures/gui/porcelain_melter_gui1.png");

    public MelterScreen(MelterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 8;
        this.titleLabelY = 6;

        this.inventoryLabelX = 8;
        this.inventoryLabelY = 72;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (this.menu.isBurning()) {
            int fireHeight = this.menu.getScaledFuel();
            if (fireHeight > 0) {
                graphics.blit(TEXTURE, x + 152, y + 15 + 14 - fireHeight, 176, 136 + 14 - fireHeight, 14, fireHeight);
            }
        }

        for (int i = 0; i < 3; i++) {
            int state = this.menu.getState(i);
            if (state == 0) continue;

            int progressHeight = this.menu.getScaledProgress(i);
            int texX = 176;

            if (state == 4) {
                texX = 188;
                progressHeight = 16;
            } else if (state == 3) {
                texX = 194;
                progressHeight = 16;
            } else if (state == 2) {
                texX = 182;
                progressHeight = 16;
            } else if (state == 1) {
                texX = 176;
            }

            if (progressHeight > 0) {
                graphics.blit(TEXTURE, x + 18, y + 17 + (i * 18) + 16 - progressHeight, texX, 150 + 16 - progressHeight, 6, progressHeight);
            }
        }

        int tankX = x + 85;
        int tankY = y + 18;
        int tankWidth = 52;
        int tankHeight = 50;

        FluidStack fluid = this.menu.getFluid();

        if (!fluid.isEmpty()) {
            float capacity = this.menu.getBlockEntity().tank.getCapacity();
            int fluidDrawHeight = (int) ((fluid.getAmount() / capacity) * tankHeight);
            if (fluidDrawHeight > 0) {
                drawTiledFluid(graphics, tankX, tankY + tankHeight - fluidDrawHeight, tankWidth, fluidDrawHeight, fluid);
            }
        }

        graphics.blit(TEXTURE, tankX, tankY, 176, 0, 6, tankHeight);

        ItemStack heaterFuel = this.menu.getHeaterFuelStack();
        if (!heaterFuel.isEmpty()) {
            graphics.renderItem(heaterFuel, x + 152, y + 36);
            graphics.renderItemDecorations(this.font, heaterFuel, x + 152, y + 36);
        }
    }

    private void drawTiledFluid(GuiGraphics graphics, int x, int y, int width, int height, FluidStack fluid) {
        if (fluid.isEmpty() || width <= 0 || height <= 0 || minecraft == null) return;
        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid.getFluid());
        ResourceLocation stillTexture = ext.getStillTexture(fluid);
        if (stillTexture == null) return;

        TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);

        int color = ext.getTintColor(fluid);
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        graphics.flush();
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.setShader(net.minecraft.client.renderer.GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(r, g, b, a);
        RenderSystem.enableBlend();

        Matrix4f matrix = graphics.pose().last().pose();
        com.mojang.blaze3d.vertex.Tesselator tesselator = com.mojang.blaze3d.vertex.Tesselator.getInstance();
        com.mojang.blaze3d.vertex.BufferBuilder builder = tesselator.begin(com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS, com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX);

        for (int i = 0; i < width; i += 16) {
            int drawWidth = Math.min(width - i, 16);
            for (int j = 0; j < height; j += 16) {
                int drawHeight = Math.min(height - j, 16);
                int drawX = x + i;
                int drawY = y + height - j - drawHeight;

                float minU = sprite.getU0();
                float maxU = sprite.getU0() + (sprite.getU1() - sprite.getU0()) * ((float) drawWidth / 16.0F);
                float minV = sprite.getV0() + (sprite.getV1() - sprite.getV0()) * ((16.0F - drawHeight) / 16.0F);
                float maxV = sprite.getV1();

                builder.addVertex(matrix, drawX, drawY + drawHeight, 0).setUv(minU, maxV);
                builder.addVertex(matrix, drawX + drawWidth, drawY + drawHeight, 0).setUv(maxU, maxV);
                builder.addVertex(matrix, drawX + drawWidth, drawY, 0).setUv(maxU, minV);
                builder.addVertex(matrix, drawX, drawY, 0).setUv(minU, minV);
            }
        }
        com.mojang.blaze3d.vertex.BufferUploader.drawWithShader(builder.buildOrThrow());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        int tankX = leftPos + 85;
        int tankY = topPos + 18;

        if (mouseX >= tankX && mouseX < tankX + 52 && mouseY >= tankY && mouseY < tankY + 50) {
            FluidStack fluid = this.menu.getFluid();
            int cap = this.menu.getBlockEntity().tank.getCapacity();

            if (!fluid.isEmpty()) {
                int amount = fluid.getAmount();
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(fluid.getHoverName().copy().withStyle(ChatFormatting.WHITE));
                tooltip.add(FluidUnits.breakdown(amount).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal(amount + " / " + cap + " mB").withStyle(ChatFormatting.DARK_GRAY));
                graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
            } else {
                graphics.renderTooltip(font, Component.literal("Empty (" + cap + " mB)").withStyle(ChatFormatting.GRAY), mouseX, mouseY);
            }
        }

        for (int i = 0; i < 3; i++) {
            int barX = leftPos + 18;
            int barY = topPos + 17 + (i * 18);
            if (mouseX >= barX && mouseX < barX + 6 && mouseY >= barY && mouseY < barY + 16) {
                int state = this.menu.getState(i);
                List<Component> tooltip = new ArrayList<>();
                switch (state) {
                    case 1 -> {
                        int pct = this.menu.getScaledProgress(i) * 100 / 16;
                        tooltip.add(Component.literal("Melting (" + pct + "%)").withStyle(ChatFormatting.WHITE));
                    }
                    case 2 -> {
                        tooltip.add(Component.literal("Melting Paused : Tank Full").withStyle(ChatFormatting.BLUE));
                    }
                    case 3 -> {
                        tooltip.add(Component.literal("Invalid Recipe").withStyle(ChatFormatting.DARK_RED));
                        tooltip.add(Component.literal("Item cannot be melted").withStyle(ChatFormatting.GRAY));
                    }
                    case 4 -> {
                        tooltip.add(Component.literal("No Heat Source").withStyle(ChatFormatting.DARK_GRAY));
                        tooltip.add(Component.literal("Requires a Porcelain Heater underneath").withStyle(ChatFormatting.GRAY));
                    }
                    default -> tooltip.add(Component.literal("Slot Empty").withStyle(ChatFormatting.GRAY));
                }
                graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
            }
        }

        int fuelX = leftPos + 150;
        int fuelY = topPos + 15;
        if (mouseX >= fuelX && mouseX < fuelX + 18 && mouseY >= fuelY && mouseY < fuelY + 38) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.literal("Porcelain Heater").withStyle(ChatFormatting.WHITE));
            if (this.menu.isBurning()) {
                tooltip.add(Component.literal("Status : Heating").withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.literal("Status : Inactive").withStyle(ChatFormatting.DARK_GRAY));
            }

            ItemStack fuelStack = this.menu.getHeaterFuelStack();
            if (!fuelStack.isEmpty()) {
                tooltip.add(Component.literal("Fuel : " + fuelStack.getHoverName().getString() + " x" + fuelStack.getCount()).withStyle(ChatFormatting.DARK_GRAY));
            } else {
                tooltip.add(Component.literal("No fuel").withStyle(ChatFormatting.DARK_GRAY));
            }
            graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}