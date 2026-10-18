package net.Herothepro.progressioned.util.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public final class FluidUnits {
    private static final int NUGGET_MB = 16;
    private static final int INGOT_MB = 144;
    private static final int BLOCK_MB = 1296;

    private FluidUnits() {}

    public static MutableComponent breakdown(int amount) {
        int blocks = amount / BLOCK_MB;
        int remAfterBlocks = amount % BLOCK_MB;

        int ingots = remAfterBlocks / INGOT_MB;
        int remAfterIngots = remAfterBlocks % INGOT_MB;

        int nuggets = remAfterIngots / NUGGET_MB;
        int remainder = remAfterIngots % NUGGET_MB;

        List<String> parts = new ArrayList<>();

        if (blocks > 0) {
            parts.add(blocks + (blocks == 1 ? " Block" : " Blocks"));
        }
        if (ingots > 0) {
            parts.add(ingots + (ingots == 1 ? " Ingot" : " Ingots"));
        }
        if (nuggets > 0) {
            parts.add(nuggets + (nuggets == 1 ? " Nugget" : " Nuggets"));
        }
        if (remainder > 0 || parts.isEmpty()) {
            parts.add(remainder + " mB");
        }

        return Component.literal(String.join(", ", parts));
    }
}