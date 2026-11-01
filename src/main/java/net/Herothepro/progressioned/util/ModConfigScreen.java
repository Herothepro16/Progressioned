package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModConfigScreen extends Screen {
    private final Screen parent;

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("progressioned", "textures/gui/config_background.png");

    private boolean originalCanFlintMineCopper;
    private boolean originalAllowCampfireHeating;
    private boolean originalKnifeCompat;
    private boolean originalOptionalMelting;
    private boolean originalAllowHandWoodMining;
    private boolean originalOptionalChoppingBlock;
    private boolean originalOptionalKiln;
    private int originalPlantFiberChance;
    private int originalStrawChance;

    private boolean canFlintMineCopper;
    private boolean allowCampfireHeating;
    private boolean knifeCompat;
    private boolean optionalMelting;
    private boolean allowHandWoodMining;
    private boolean optionalChoppingBlock;
    private boolean optionalKiln;
    private int plantFiberChance;
    private int strawChance;

    private Button flintCopperButton;
    private Button campfireHeatingButton;
    private Button knifeCompatButton;
    private Button optionalMeltingButton;
    private Button allowHandWoodMiningButton;
    private Button optionalChoppingBlockButton;
    private Button optionalKilnButton;
    private Button resetButton;

    public ModConfigScreen(Screen parent) {
        super(Component.literal("Progressioned Config"));
        this.parent = parent;

        this.originalCanFlintMineCopper = ModConfig.CAN_FLINT_MINE_COPPER.get();
        this.canFlintMineCopper = this.originalCanFlintMineCopper;

        this.originalAllowCampfireHeating = ModConfig.ALLOW_CAMPFIRE_HEATING.get();
        this.allowCampfireHeating = this.originalAllowCampfireHeating;

        this.originalKnifeCompat = ModConfig.KNIFE_COMPAT.get();
        this.knifeCompat = this.originalKnifeCompat;

        this.originalOptionalMelting = ModConfig.OPTIONAL_MELTING.get();
        this.optionalMelting = this.originalOptionalMelting;

        this.originalAllowHandWoodMining = ModConfig.ALLOW_HAND_WOOD_MINING.get();
        this.allowHandWoodMining = this.originalAllowHandWoodMining;

        this.originalOptionalChoppingBlock = ModConfig.OPTIONAL_CHOPPING_BLOCK.get();
        this.optionalChoppingBlock = this.originalOptionalChoppingBlock;

        this.originalOptionalKiln = ModConfig.OPTIONAL_KILN.get();
        this.optionalKiln = this.originalOptionalKiln;

        this.originalPlantFiberChance = ModConfig.PLANT_FIBER_DROP_CHANCE.get();
        this.plantFiberChance = this.originalPlantFiberChance;

        this.originalStrawChance = ModConfig.STRAW_DROP_CHANCE.get();
        this.strawChance = this.originalStrawChance;
    }

    @Override
    protected void init() {
        super.init();

        int buttonX = this.width / 2 + 50;
        int startY = 32;
        int spacing = 24;

        Component flintCopperTooltip = Component.literal("Allows flint tools to mine copper ore")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.CAN_FLINT_MINE_COPPER.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.flintCopperButton = this.addRenderableWidget(Button.builder(Component.literal(canFlintMineCopper ? "True" : "False"), button -> {
                    canFlintMineCopper = !canFlintMineCopper;
                    updateButtonStates();
                })
                .bounds(buttonX, startY, 90, 20)
                .tooltip(Tooltip.create(flintCopperTooltip))
                .build());

        Component campfireTooltip = Component.literal("Allows lit campfires to be used as a heating block under kilns")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.campfireHeatingButton = this.addRenderableWidget(Button.builder(Component.literal(allowCampfireHeating ? "True" : "False"), button -> {
                    allowCampfireHeating = !allowCampfireHeating;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + spacing, 90, 20)
                .tooltip(Tooltip.create(campfireTooltip))
                .build());

        Component knifeTooltip = Component.literal("Allows knives from other mods to drop Plant Fiber & Straw")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.KNIFE_COMPAT.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.knifeCompatButton = this.addRenderableWidget(Button.builder(Component.literal(knifeCompat ? "True" : "False"), button -> {
                    knifeCompat = !knifeCompat;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + (spacing * 2), 90, 20)
                .tooltip(Tooltip.create(knifeTooltip))
                .build());

        Component meltingTooltip = Component.literal("Allows raw ores to be smelted or blasted into ingots")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.OPTIONAL_MELTING.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.optionalMeltingButton = this.addRenderableWidget(Button.builder(Component.literal(optionalMelting ? "True" : "False"), button -> {
                    optionalMelting = !optionalMelting;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + (spacing * 3), 90, 20)
                .tooltip(Tooltip.create(meltingTooltip))
                .build());

        Component woodMiningTooltip = Component.literal("Allows logs and planks to be mined by hand without a flint tool")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.ALLOW_HAND_WOOD_MINING.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.allowHandWoodMiningButton = this.addRenderableWidget(Button.builder(Component.literal(allowHandWoodMining ? "True" : "False"), button -> {
                    allowHandWoodMining = !allowHandWoodMining;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + (spacing * 4), 90, 20)
                .tooltip(Tooltip.create(woodMiningTooltip))
                .build());

        Component choppingTooltip = Component.literal("Disables the recipe that forces logs to be sawed into planks")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.OPTIONAL_CHOPPING_BLOCK.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.optionalChoppingBlockButton = this.addRenderableWidget(Button.builder(Component.literal(optionalChoppingBlock ? "True" : "False"), button -> {
                    optionalChoppingBlock = !optionalChoppingBlock;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + (spacing * 5), 90, 20)
                .tooltip(Tooltip.create(choppingTooltip))
                .build());

        Component kilnTooltip = Component.literal("Disables custom stone tool recipes and the custom furnace recipe")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.OPTIONAL_KILN.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.optionalKilnButton = this.addRenderableWidget(Button.builder(Component.literal(optionalKiln ? "True" : "False"), button -> {
                    optionalKiln = !optionalKiln;
                    updateButtonStates();
                })
                .bounds(buttonX, startY + (spacing * 6), 90, 20)
                .tooltip(Tooltip.create(kilnTooltip))
                .build());

        Component fiberTooltip = Component.literal("Chance to drop Plant Fiber when breaking grass with a knife")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault() + "%").withStyle(ChatFormatting.DARK_GRAY));

        AbstractSliderButton fiberSlider = new AbstractSliderButton(buttonX, startY + (spacing * 7), 90, 20, Component.literal(plantFiberChance + "%"), plantFiberChance / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(plantFiberChance + "%"));
            }
            @Override
            protected void applyValue() {
                plantFiberChance = (int) Math.round(this.value * 100);
                updateButtonStates();
            }
        };
        fiberSlider.setTooltip(Tooltip.create(fiberTooltip));
        this.addRenderableWidget(fiberSlider);

        Component strawTooltip = Component.literal("Chance to drop Straw when harvesting mature wheat with a knife")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.STRAW_DROP_CHANCE.getDefault() + "%").withStyle(ChatFormatting.DARK_GRAY));

        AbstractSliderButton strawSlider = new AbstractSliderButton(buttonX, startY + (spacing * 8), 90, 20, Component.literal(strawChance + "%"), strawChance / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(strawChance + "%"));
            }
            @Override
            protected void applyValue() {
                strawChance = (int) Math.round(this.value * 100);
                updateButtonStates();
            }
        };
        strawSlider.setTooltip(Tooltip.create(strawTooltip));
        this.addRenderableWidget(strawSlider);

        int footerY = this.height - 27;

        this.resetButton = this.addRenderableWidget(Button.builder(Component.literal("Reset").withStyle(ChatFormatting.RED), button -> {
            canFlintMineCopper = (boolean) ModConfig.CAN_FLINT_MINE_COPPER.getDefault();
            allowCampfireHeating = (boolean) ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault();
            knifeCompat = (boolean) ModConfig.KNIFE_COMPAT.getDefault();
            optionalMelting = (boolean) ModConfig.OPTIONAL_MELTING.getDefault();
            allowHandWoodMining = (boolean) ModConfig.ALLOW_HAND_WOOD_MINING.getDefault();
            optionalChoppingBlock = (boolean) ModConfig.OPTIONAL_CHOPPING_BLOCK.getDefault();
            optionalKiln = (boolean) ModConfig.OPTIONAL_KILN.getDefault();
            plantFiberChance = (int) ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault();
            strawChance = (int) ModConfig.STRAW_DROP_CHANCE.getDefault();
            this.clearWidgets();
            this.init();
        }).bounds(this.width / 2 - 100, footerY, 98, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Save & Quit").withStyle(ChatFormatting.GREEN), button -> {
            ModConfig.CAN_FLINT_MINE_COPPER.set(this.canFlintMineCopper);
            ModConfig.ALLOW_CAMPFIRE_HEATING.set(this.allowCampfireHeating);
            ModConfig.KNIFE_COMPAT.set(this.knifeCompat);
            ModConfig.OPTIONAL_MELTING.set(this.optionalMelting);
            ModConfig.ALLOW_HAND_WOOD_MINING.set(this.allowHandWoodMining);
            ModConfig.OPTIONAL_CHOPPING_BLOCK.set(this.optionalChoppingBlock);
            ModConfig.OPTIONAL_KILN.set(this.optionalKiln);
            ModConfig.PLANT_FIBER_DROP_CHANCE.set(this.plantFiberChance);
            ModConfig.STRAW_DROP_CHANCE.set(this.strawChance);

            ModConfig.SPEC.save();

            this.minecraft.setScreen(this.parent);
        }).bounds(this.width / 2 + 2, footerY, 98, 20).build());

        updateButtonStates();
    }

    private void updateButtonStates() {
        if (this.flintCopperButton != null) {
            this.flintCopperButton.setMessage(Component.literal(canFlintMineCopper ? "True" : "False"));
        }
        if (this.campfireHeatingButton != null) {
            this.campfireHeatingButton.setMessage(Component.literal(allowCampfireHeating ? "True" : "False"));
        }
        if (this.knifeCompatButton != null) {
            this.knifeCompatButton.setMessage(Component.literal(knifeCompat ? "True" : "False"));
        }
        if (this.optionalMeltingButton != null) {
            this.optionalMeltingButton.setMessage(Component.literal(optionalMelting ? "True" : "False"));
        }
        if (this.allowHandWoodMiningButton != null) {
            this.allowHandWoodMiningButton.setMessage(Component.literal(allowHandWoodMining ? "True" : "False"));
        }
        if (this.optionalChoppingBlockButton != null) {
            this.optionalChoppingBlockButton.setMessage(Component.literal(optionalChoppingBlock ? "True" : "False"));
        }
        if (this.optionalKilnButton != null) {
            this.optionalKilnButton.setMessage(Component.literal(optionalKiln ? "True" : "False"));
        }

        boolean isAtDefault = (canFlintMineCopper == (boolean) ModConfig.CAN_FLINT_MINE_COPPER.getDefault()) &&
                (allowCampfireHeating == (boolean) ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault()) &&
                (knifeCompat == (boolean) ModConfig.KNIFE_COMPAT.getDefault()) &&
                (optionalMelting == (boolean) ModConfig.OPTIONAL_MELTING.getDefault()) &&
                (allowHandWoodMining == (boolean) ModConfig.ALLOW_HAND_WOOD_MINING.getDefault()) &&
                (optionalChoppingBlock == (boolean) ModConfig.OPTIONAL_CHOPPING_BLOCK.getDefault()) &&
                (optionalKiln == (boolean) ModConfig.OPTIONAL_KILN.getDefault()) &&
                (plantFiberChance == (int) ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault()) &&
                (strawChance == (int) ModConfig.STRAW_DROP_CHANCE.getDefault());
        if (this.resetButton != null) this.resetButton.active = !isAtDefault;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, this.width, this.height, this.width, this.height);

        guiGraphics.fill(0, 28, this.width, this.height - 36, 0x66000000);
        guiGraphics.fill(0, 28, this.width, 29, 0xFF000000);
        guiGraphics.fill(0, this.height - 37, this.width, this.height - 36, 0xFF000000);

        for (net.minecraft.client.gui.components.Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        guiGraphics.pose().pushPose();
        float scale = 1.25f;
        guiGraphics.pose().scale(scale, scale, 1.0f);
        guiGraphics.drawCenteredString(this.font, this.title, (int)((this.width / 2) / scale), (int)(7 / scale), 0xFFFFFF);
        guiGraphics.pose().popPose();

        int textX = this.width / 2 - 140;

        if (this.flintCopperButton != null) {
            int textY = this.flintCopperButton.getY() + (this.flintCopperButton.getHeight() - this.font.lineHeight) / 2;
            guiGraphics.drawString(this.font, Component.literal("Can Flint Mine Copper"), textX, textY, 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Allow Campfire Heating"), textX, textY + 24, 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Knife Compatibility"), textX, textY + (24 * 2), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Optional Melting"), textX, textY + (24 * 3), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Allow Wood to be mined by hand"), textX, textY + (24 * 4), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Optional Chopping Block"), textX, textY + (24 * 5), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Optional Kiln"), textX, textY + (24 * 6), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Plant Fiber Drop Chance"), textX, textY + (24 * 7), 0xFFFFFF);
            guiGraphics.drawString(this.font, Component.literal("Straw Cord Drop Chance"), textX, textY + (24 * 8), 0xFFFFFF);
        }
    }
}