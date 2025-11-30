package com.bearvspython.armorhud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class Helpers {
    private static final ResourceLocation HOTBAR_TEXTURE = ResourceLocation.fromNamespaceAndPath("armorhud", "hotbar_texture");
    private static final ResourceLocation EXCLAMATION_MARKS_TEXTURE = ResourceLocation.fromNamespaceAndPath("armorhud", "exclamation_marks_flash");

    public static boolean isDurabilityLow(ItemStack item) {
        int maxDamage = item.getMaxDamage();
        int damage = item.getDamageValue();
        return damage > 0 && (maxDamage - damage) / (float) maxDamage < Config.durabilityWarningThreshold.get();
    }

    public static void drawExclamationMark(GuiGraphics guiGraphics, int boxX, int boxY, int boxSize) {
        long currentTime = System.currentTimeMillis();
        float bobbingOffset = (float) Math.sin(currentTime / 200.0) * 2;

        int iconSize = 11;
        int offsetX = -1; // Moved from 0 to -1 to shift it slightly left
        int offsetY = -2;

        int drawX = boxX + offsetX;
        int drawY = boxY + offsetY + (int) bobbingOffset;

        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                EXCLAMATION_MARKS_TEXTURE,
                drawX, drawY,
                0, 0,
                iconSize, iconSize,
                iconSize, iconSize
        );
    }

    public static void drawTexture(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                HOTBAR_TEXTURE,
                width, height,
                0, 0,
                x, y,
                width, height
        );
    }

    public static void drawDurabilityBar(GuiGraphics guiGraphics, int x, int y, int width, ItemStack item) {
        int maxDamage = item.getMaxDamage();
        int damage = item.getDamageValue();

        if (damage == 0) {
            return;
        }

        // Total width of the durability bar
        int barWidth = 13;
        int barX = x + (width - barWidth) / 2 + 1;
        int barHeight = 2;

        float durabilityRatio = ((maxDamage - damage) / (float) maxDamage);

        // Get remaining width using increments of barWidth / 13
        int remainingWidth = (int) Math.round(durabilityRatio * 13);

        // Get durability bar color from HSV
        int barColor = convertHSVtoARGB((durabilityRatio / 3f) * 360, 1, 1);

        // Draw whole black background
        guiGraphics.fill(barX, y, barX + barWidth, y + barHeight, 0xFF000000);

        // Draw the remaining durability over the background
        guiGraphics.fill(barX, y, barX + remainingWidth, y + barHeight / 2, barColor);
    }

    public static int convertHSVtoARGB(float h, float s, float v) {
        h = (h % 360 + 360) % 360;

        float hh = h / 60.0f;
        int i = (int) hh % 6;

        float f = hh - i;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);

        int r = 0, g = 0, b = 0;

        switch (i) {
            case 0: r = Math.round(v * 255); g = Math.round(t * 255); b = Math.round(p * 255); break;
            case 1: r = Math.round(q * 255); g = Math.round(v * 255); b = Math.round(p * 255); break;
            case 2: r = Math.round(p * 255); g = Math.round(v * 255); b = Math.round(t * 255); break;
            case 3: r = Math.round(p * 255); g = Math.round(q * 255); b = Math.round(v * 255); break;
            case 4: r = Math.round(t * 255); g = Math.round(p * 255); b = Math.round(v * 255); break;
            case 5: r = Math.round(v * 255); g = Math.round(p * 255); b = Math.round(q * 255); break;
        }

        // Return standard RGB hex value
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }
}
