package com.bearvspython.armorhud;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue xOffset = BUILDER
            .comment("X Offset of the armor HUD from the default position")
            .defineInRange("xOffset", -224, -500, 500);

    public static final ModConfigSpec.IntValue yOffset = BUILDER
            .comment("Y Offset of the armor HUD from the default position")
            .defineInRange("yOffset", -22, -500, 500);

    public static final ModConfigSpec.BooleanValue showHotbarSlot = BUILDER
            .comment("Show the hotbar slot behind the armor pieces in the HUD")
            .define("showHotbarSlot", true);

    public static final ModConfigSpec.BooleanValue showExclamationMarks = BUILDER
            .comment("Show exclamation marks on items that are low on durability")
            .define("showExclamationMarks", true);

    public static final ModConfigSpec.ConfigValue<Float> durabilityWarningThreshold = BUILDER
            .comment("Durability percentage threshold to show exclamation marks (0.0 - 1.0)")
            .define("durabilityWarningThreshold", 0.20f);

    public static final ModConfigSpec.IntValue boxSize = BUILDER
            .comment("Size of each armor box in the HUD")
            .defineInRange("boxSize", 22, 10, 100);

    public static final ModConfigSpec.IntValue spacing = BUILDER
            .comment("Spacing between armor boxes in the HUD")
            .defineInRange("spacing", 2, 0, 50);

    public static final ModConfigSpec.BooleanValue visible = BUILDER
            .comment("Toggle the visibility of the armor HUD")
            .define("visible", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}