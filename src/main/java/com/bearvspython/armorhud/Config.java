package com.bearvspython.armorhud;

import net.neoforged.neoforge.common.ModConfigSpec;

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


    public static final ModConfigSpec.ConfigValue<Integer> horizontalOffset = BUILDER
            .comment("X Position of the armor HUD from the anchor point")
            .define("horizontalOffset", 0);

    public static final ModConfigSpec.ConfigValue<Integer> verticalOffset = BUILDER
            .comment("Y Position of the armor HUD from the anchor point")
            .define("verticalOffset", 0);

    public static final ModConfigSpec.EnumValue<LayoutStyle> layoutStyle = BUILDER
            .comment("Layout style of the armor HUD")
            .defineEnum("layoutStyle", LayoutStyle.HORIZONTAL);

    public static final ModConfigSpec.EnumValue<AnchorPoint.Vertical> verticalAnchor = BUILDER
            .comment("Anchor point for the armor HUD")
            .defineEnum("verticalAnchor", AnchorPoint.Vertical.BOTTOM);

    public static final ModConfigSpec.EnumValue<AnchorPoint.Horizontal> horizontalAnchor = BUILDER
            .comment("Anchor point for the armor HUD")
            .defineEnum("horizontalAnchor", AnchorPoint.Horizontal.CENTER);

    public static final ModConfigSpec.IntValue scale = BUILDER
            .comment("Size of each armor piece in the HUD")
            .defineInRange("scale", 50, 0, 100);

    public static final ModConfigSpec.IntValue spacing = BUILDER
            .comment("Spacing between armor boxes in the HUD")
            .defineInRange("spacing", 2, 0, 50);

    public static final ModConfigSpec.BooleanValue showHotbarSlot = BUILDER
            .comment("Show the hotbar slot behind the armor pieces in the HUD")
            .define("showHotbarSlot", true);

    public static final ModConfigSpec.BooleanValue showDurabilityBar = BUILDER
            .comment("Show durability bar below armor pieces in the HUD")
            .define("showDurabilityBar", true);

    /* public static final ModConfigSpec.ConfigValue<Float> durabilityWarningThreshold = BUILDER
            .comment("Durability percentage threshold to show warning (0.0 - 1.0)")
            .define("durabilityWarningThreshold", 0.20f); */

    public static final ModConfigSpec.BooleanValue visible = BUILDER
            .comment("Toggle the visibility of the armor HUD")
            .define("visible", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}