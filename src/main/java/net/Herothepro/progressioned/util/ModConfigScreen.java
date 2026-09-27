package net.Herothepro.progressioned.util;

import net.Herothepro.progressioned.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen extends Screen {
    private final Screen parent;

    private boolean originalCanFlintMineCopper;
    private boolean originalAllowCampfireHeating;
    private boolean originalKnifeCompat;
    private int originalPlantFiberChance;
    private int originalStrawChance;

    private boolean canFlintMineCopper;
    private boolean allowCampfireHeating;
    private boolean knifeCompat;
    private int plantFiberChance;
    private int strawChance;

    private Button flintCopperButton;
    private Button campfireHeatingButton;
    private Button knifeCompatButton;
    private Button undoButton;
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

        this.originalPlantFiberChance = ModConfig.PLANT_FIBER_DROP_CHANCE.get();
        this.plantFiberChance = this.originalPlantFiberChance;

        this.originalStrawChance = ModConfig.STRAW_DROP_CHANCE.get();
        this.strawChance = this.originalStrawChance;
    }

    @Override
    protected void init() {
        super.init();

        int buttonX = this.width / 2 + 50;

        Component flintCopperTooltip = Component.literal("Allows flint tools to mine copper ore")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.CAN_FLINT_MINE_COPPER.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.flintCopperButton = this.addRenderableWidget(Button.builder(Component.literal(canFlintMineCopper ? "True" : "False"), button -> {
                    canFlintMineCopper = !canFlintMineCopper;
                    updateButtonStates();
                })
                .bounds(buttonX, 50, 90, 20)
                .tooltip(Tooltip.create(flintCopperTooltip))
                .build());

        Component campfireTooltip = Component.literal("Allows lit campfires to be used as a heating block under kilns")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.campfireHeatingButton = this.addRenderableWidget(Button.builder(Component.literal(allowCampfireHeating ? "True" : "False"), button -> {
                    allowCampfireHeating = !allowCampfireHeating;
                    updateButtonStates();
                })
                .bounds(buttonX, 75, 90, 20)
                .tooltip(Tooltip.create(campfireTooltip))
                .build());

        Component knifeTooltip = Component.literal("Allows knives from other mods to drop Plant Fiber & Straw")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.KNIFE_COMPAT.getDefault()).withStyle(ChatFormatting.DARK_GRAY));

        this.knifeCompatButton = this.addRenderableWidget(Button.builder(Component.literal(knifeCompat ? "True" : "False"), button -> {
                    knifeCompat = !knifeCompat;
                    updateButtonStates();
                })
                .bounds(buttonX, 100, 90, 20)
                .tooltip(Tooltip.create(knifeTooltip))
                .build());

        Component fiberTooltip = Component.literal("Chance to drop Plant Fiber when breaking grass with a knife")
                .append("\n")
                .append(Component.literal("Default : " + ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault() + "%").withStyle(ChatFormatting.DARK_GRAY));

        AbstractSliderButton fiberSlider = new AbstractSliderButton(buttonX, 125, 90, 20, Component.literal(plantFiberChance + "%"), plantFiberChance / 100.0) {
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

        AbstractSliderButton strawSlider = new AbstractSliderButton(buttonX, 150, 90, 20, Component.literal(strawChance + "%"), strawChance / 100.0) {
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

        this.undoButton = this.addRenderableWidget(Button.builder(Component.literal("Undo"), button -> {
            canFlintMineCopper = originalCanFlintMineCopper;
            allowCampfireHeating = originalAllowCampfireHeating;
            knifeCompat = originalKnifeCompat;
            plantFiberChance = originalPlantFiberChance;
            strawChance = originalStrawChance;
            this.clearWidgets();
            this.init();
        }).bounds(this.width / 2 - 100, footerY, 64, 20).build());

        this.resetButton = this.addRenderableWidget(Button.builder(Component.literal("Reset"), button -> {
            canFlintMineCopper = (boolean) ModConfig.CAN_FLINT_MINE_COPPER.getDefault();
            allowCampfireHeating = (boolean) ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault();
            knifeCompat = (boolean) ModConfig.KNIFE_COMPAT.getDefault();
            plantFiberChance = (int) ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault();
            strawChance = (int) ModConfig.STRAW_DROP_CHANCE.getDefault();
            this.clearWidgets();
            this.init();
        }).bounds(this.width / 2 - 32, footerY, 64, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), button -> {
            ModConfig.CAN_FLINT_MINE_COPPER.set(this.canFlintMineCopper);
            ModConfig.ALLOW_CAMPFIRE_HEATING.set(this.allowCampfireHeating);
            ModConfig.KNIFE_COMPAT.set(this.knifeCompat);
            ModConfig.PLANT_FIBER_DROP_CHANCE.set(this.plantFiberChance);
            ModConfig.STRAW_DROP_CHANCE.set(this.strawChance);

            ModConfig.SPEC.save();

            this.minecraft.setScreen(this.parent);
        }).bounds(this.width / 2 + 36, footerY, 64, 20).build());

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

        boolean hasChanged = (canFlintMineCopper != originalCanFlintMineCopper) ||
                (allowCampfireHeating != originalAllowCampfireHeating) ||
                (knifeCompat != originalKnifeCompat) ||
                (plantFiberChance != originalPlantFiberChance) ||
                (strawChance != originalStrawChance);
        if (this.undoButton != null) this.undoButton.active = hasChanged;

        boolean isAtDefault = (canFlintMineCopper == (boolean) ModConfig.CAN_FLINT_MINE_COPPER.getDefault()) &&
                (allowCampfireHeating == (boolean) ModConfig.ALLOW_CAMPFIRE_HEATING.getDefault()) &&
                (knifeCompat == (boolean) ModConfig.KNIFE_COMPAT.getDefault()) &&
                (plantFiberChance == (int) ModConfig.PLANT_FIBER_DROP_CHANCE.getDefault()) &&
                (strawChance == (int) ModConfig.STRAW_DROP_CHANCE.getDefault());
        if (this.resetButton != null) this.resetButton.active = !isAtDefault;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.fill(0, 36, this.width, this.height - 36, 0x66000000);
        guiGraphics.fill(0, 36, this.width, 37, 0xFF000000);
        guiGraphics.fill(0, this.height - 37, this.width, this.height - 36, 0xFF000000);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);

        int textX = this.width / 2 - 140;

        guiGraphics.drawString(this.font, Component.literal("Can Flint Mine Copper"), textX, 50 + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("Allow Campfire Heating"), textX, 75 + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("Knife Compatibility"), textX, 100 + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("Plant Fiber Drop Chance"), textX, 125 + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("Straw Cord Drop Chance"), textX, 150 + 6, 0xFFFFFF);
    }
}