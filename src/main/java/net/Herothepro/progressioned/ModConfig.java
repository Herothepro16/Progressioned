package net.Herothepro.progressioned;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CAN_FLINT_MINE_COPPER = BUILDER
            .comment("Allows Flint tools to mine copper ores.")
            .translation("progressioned.configuration.canFlintMineCopper")
            .define("canFlintMineCopper", false);

    public static final ModConfigSpec.BooleanValue ALLOW_CAMPFIRE_HEATING = BUILDER
            .comment("Allows lit campfires to be used as a heating block under the Porcelain Kiln.")
            .define("allowCampfireHeating", false);

    public static final ModConfigSpec.BooleanValue KNIFE_COMPAT = BUILDER
            .comment("Allow knives from other mods (tagged under c:tools/knives) to drop Plant Fiber and Straw")
            .define("knifeCompat", false);

    public static final ModConfigSpec.IntValue PLANT_FIBER_DROP_CHANCE = BUILDER
            .comment("Percentage chance (0-100) for Plant Fiber to drop from grass and ferns.")
            .defineInRange("plantFiberDropChance", 75, 0, 100);

    public static final ModConfigSpec.IntValue STRAW_DROP_CHANCE = BUILDER
            .comment("Percentage chance (0-100) for Straw to drop from fully grown wheat.")
            .defineInRange("strawDropChance", 75, 0, 100);

    public static final ModConfigSpec SPEC = BUILDER.build();
}