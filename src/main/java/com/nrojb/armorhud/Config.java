package com.nrojb.armorhud;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Locale;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public enum LayoutStyle {
        HORIZONTAL,
        VERTICAL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum AnchorPoint {
        TOP,
        BOTTOM,
        CENTER,
        LEFT,
        RIGHT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

        public enum Vertical {
            TOP(AnchorPoint.TOP),
            CENTER(AnchorPoint.CENTER),
            BOTTOM(AnchorPoint.BOTTOM);

            private final AnchorPoint anchorPoint;

            Vertical(AnchorPoint anchorPoint) {
                this.anchorPoint = anchorPoint;
            }

            public AnchorPoint getAnchorPoint() {
                return anchorPoint;
            }

            @Override
            public String toString() {
                return anchorPoint.toString();
            }
        }

        public enum Horizontal {
            LEFT(AnchorPoint.LEFT),
            CENTER(AnchorPoint.CENTER),
            RIGHT(AnchorPoint.RIGHT);

            private final AnchorPoint anchorPoint;

            Horizontal(AnchorPoint anchorPoint) {
                this.anchorPoint = anchorPoint;
            }

            public AnchorPoint getAnchorPoint() {
                return anchorPoint;
            }

            @Override
            public String toString() {
                return anchorPoint.toString();
            }
        }
    }

    public enum DurabilityNumber {
        OFF,
        NUMBER_AND_MAX,
        NUMBER_ONLY,
        PERCENTAGE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum DurabilityNumberColor {
        WHITE,
        BLACK,
        MATCH_DURABILITY_BAR,
        AUTO;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Visibility {
        ALWAYS,
        LOW_DURABILITY,
        NEVER;

        @Override
        public String toString() { return name().toLowerCase(); }
    }


    public static final ModConfigSpec.ConfigValue<Double> horizontalOffset = BUILDER
            .comment("X Position of the armor HUD from the anchor point")
            .define("horizontalOffset", 11.0);

    public static final ModConfigSpec.ConfigValue<Double> verticalOffset = BUILDER
            .comment("Y Position of the armor HUD from the anchor point")
            .define("verticalOffset", 0.0);

    public static final ModConfigSpec.EnumValue<LayoutStyle> layoutStyle = BUILDER
            .comment("Layout style of the armor HUD")
            .defineEnum("layoutStyle", LayoutStyle.VERTICAL);

    public static final ModConfigSpec.EnumValue<AnchorPoint.Vertical> verticalAnchor = BUILDER
            .comment("Anchor point for the armor HUD")
            .defineEnum("verticalAnchor", AnchorPoint.Vertical.CENTER);

    public static final ModConfigSpec.EnumValue<AnchorPoint.Horizontal> horizontalAnchor = BUILDER
            .comment("Anchor point for the armor HUD")
            .defineEnum("horizontalAnchor", AnchorPoint.Horizontal.LEFT);

    public static final ModConfigSpec.DoubleValue scale = BUILDER
            .comment("Size of each armor piece in the HUD")
            .defineInRange("scale", 1.0, 0.25, 2.0);

    public static final ModConfigSpec.IntValue spacing = BUILDER
            .comment("Spacing between armor boxes in the HUD")
            .defineInRange("spacing", 0, 0, 50);

    public static final ModConfigSpec.BooleanValue showHotbarSlot = BUILDER
            .comment("Show the hotbar slot behind the armor pieces in the HUD")
            .define("showHotbarSlot", true);

    public static final ModConfigSpec.BooleanValue showDurabilityBar = BUILDER
            .comment("Show durability bar below armor pieces in the HUD")
            .define("showDurabilityBar", true);

    public static final ModConfigSpec.EnumValue<DurabilityNumber> durabilityNumber = BUILDER
            .comment("Show number above armor pieces in the HUD")
            .defineEnum("durabilityNumber", DurabilityNumber.OFF);

    public static final ModConfigSpec.EnumValue<DurabilityNumberColor> durabilityNumberColor = BUILDER
            .comment("Color style for durability number")
            .defineEnum("durabilityNumberColor", DurabilityNumberColor.WHITE);

    public static final ModConfigSpec.EnumValue<Visibility> visibility = BUILDER
            .comment("Toggle the visibility of the armor HUD")
            .defineEnum("visibility", Visibility.ALWAYS);

    static final ModConfigSpec SPEC = BUILDER.build();
}