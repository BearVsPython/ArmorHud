package com.bearvspython.armorhud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ArmorHudOverlay {

    public static void register(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, Overlay.ID, new Overlay());
    }

    public static class Overlay implements GuiLayer {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ArmorHud.MODID, "armor_durability");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            Minecraft mc = Minecraft.getInstance();

            if (!Config.visible.getAsBoolean() || mc.options.hideGui || mc.player == null || mc.gameMode == null) {
                return;
            }

            // Get armor items
            ItemStack[] armorItems = new ItemStack[4];
            for (int i = 0; i < 4; i++) {
                armorItems[i] = mc.player.getInventory().getItem(36 + i);
            }

            // Get screen width and height
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            // Use config values
            int boxSize = Config.boxSize.getAsInt();
            int spacing = Config.spacing.getAsInt();
            int xOffset = screenWidth / 2 + Config.xOffset.getAsInt();
            int yOffset = screenHeight + Config.yOffset.getAsInt();

            // Draw armor boxes and icons
            for (int i = armorItems.length - 1; i >= 0; i--) {
                ItemStack armorItem = armorItems[i];

                if (!armorItem.isEmpty()) {
                    int armorSpacing = (armorItems.length - 1 - i) * (boxSize + spacing);

                    // Draw box background
                    Helpers.drawTexture(guiGraphics, xOffset + armorSpacing, yOffset, boxSize, boxSize);

                    // Draw armor icon
                    guiGraphics.renderItem(armorItem, xOffset + armorSpacing + (boxSize - 16) / 2, yOffset + (boxSize - 16) / 2);
                }
            }

            // Draw durability bar and exclamation mark
            for (int i = armorItems.length - 1; i >= 0; i--) {
                ItemStack armorItem = armorItems[i];

                if (!armorItem.isEmpty()) {
                    int armorSpacing = (armorItems.length - 1 - i) * (boxSize + spacing);

                    // Draw the durability
                    Helpers.drawDurabilityBar(guiGraphics, xOffset + armorSpacing, yOffset + boxSize - 6, boxSize, armorItem);

                    // Draw exclamation mark if needed
                    if (Helpers.isDurabilityLow(armorItem) && Config.showExclamationMarks.getAsBoolean()) {
                        // Exclamation mark appears at the top right of the box
                        Helpers.drawExclamationMark(guiGraphics, xOffset + armorSpacing, yOffset, boxSize);
                    }
                }
            }
        }
    }
}