package com.bearvspython.armorhud;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ArmorHudOverlay {


    private static final ResourceLocation ITEM_SLOT_TEXTURE = ResourceLocation.withDefaultNamespace("hud/hotbar_offhand_left");
    private static final int ITEM_SLOT_TEXTURE_WIDTH = 29;
    private static final int ITEM_SLOT_TEXTURE_HEIGHT = 24;
    private static final int ITEM_ICON_SIZE = 16;

    public static void register(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, Overlay.ID, new Overlay());
    }

    public static class Overlay implements GuiLayer {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ArmorHud.MODID, "armor_hud");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            Minecraft mc = Minecraft.getInstance();

            if (!Config.visible.getAsBoolean() || mc.options.hideGui || mc.player == null || mc.gameMode == null) {
                return;
            }

            // Get screen width and height
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            // Calculate reference points based on anchor settings
            int xReference;
            switch (Config.horizontalAnchor.get()) {
                case LEFT -> xReference = 0;
                case CENTER -> xReference = (screenWidth / 2);
                case RIGHT -> xReference = screenWidth;
                default -> xReference = 0;
            }

            int yReference;
            switch (Config.verticalAnchor.get()) {
                case TOP -> yReference = 0;
                case CENTER -> yReference = screenHeight / 2;
                case BOTTOM -> yReference = screenHeight;
                default -> yReference = 0;
            }

            // Use config values
            float scale = Mth.clamp(Config.scale.get()/50f, 0.25f, 2f);
            int spacing = Config.spacing.getAsInt();
            boolean isVertical = Config.layoutStyle.get() == Config.LayoutStyle.VERTICAL;
            boolean showDurabilityBar = Config.showDurabilityBar.getAsBoolean();
            boolean showItemSlot = Config.showHotbarSlot.getAsBoolean();
            float yPosition = (float) (double) Config.verticalOffset.get();
            float xPosition = (float) (double) Config.horizontalOffset.get();

            // Get armor items
            int itemCount = 0;
            ItemStack[] armorStack = new ItemStack[4];
            for (int i = 3; i >= 0; i--) {
                ItemStack stack = mc.player.getInventory().getItem(Inventory.INVENTORY_SIZE + i);
                if (!stack.isEmpty()) {
                    itemCount++;
                }
                if (isVertical) {
                    armorStack[i] = stack;
                } else {
                    armorStack[(3 - i)] = stack;
                }
            }

            // Adjust x and y position to be relative to the center of the armor HUD
            float centeringOffsetX;
            float centeringOffsetY;

            if (isVertical) {
                if (showItemSlot) {
                    centeringOffsetY = (float) -(((((itemCount/2) * (21 + spacing)) + spacing) / 2) + 2.5);
                } else {
                    centeringOffsetY = (float) -((((itemCount/2) * (ITEM_ICON_SIZE + spacing)) + spacing) / 2);
                }

                centeringOffsetX = (ITEM_ICON_SIZE/2);
            } else {
                if (showItemSlot) {
                    centeringOffsetX = (float) ((((itemCount * (21 + spacing)) - spacing) / 2) - 2.5);
                } else {
                    centeringOffsetX = (float) (((itemCount * (ITEM_ICON_SIZE + spacing)) - spacing) / 2);
                }

                centeringOffsetY = (ITEM_ICON_SIZE/2);
            }
            centeringOffsetY *= scale;
            centeringOffsetX *= scale;

            // Apply the centering offset
            xPosition -= centeringOffsetX;
            yPosition -= centeringOffsetY;

            // Adjust starting position based on anchor points
            xPosition += xReference;
            yPosition += yReference;



            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(xPosition, yPosition);
            guiGraphics.pose().scale(scale);

            int i = 0;
            for (ItemStack stack : armorStack) {
                if (!stack.isEmpty()) {

                    int xPos = (((isVertical ? 0 : (i * (spacing + (showItemSlot ? (21) : ITEM_ICON_SIZE))))));
                    int yPos = (((isVertical ? - (i * (spacing + (showItemSlot ? (21) : ITEM_ICON_SIZE))) : 0)));


                    // Render item slot if enabled
                    if (showItemSlot) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ITEM_SLOT_TEXTURE, ITEM_SLOT_TEXTURE_WIDTH, ITEM_SLOT_TEXTURE_HEIGHT, 0, 0, xPos-3, yPos-4, 22, 23);
                    }

                    // Render armor item
//                    guiGraphics.fill(RenderPipelines.GUI, xPos, yPos, xPos + 16, yPos + 16, 0xFFFF0000);
//                    guiGraphics.fill(RenderPipelines.GUI, xPos + 1, yPos + 1, xPos + 15, yPos + 15, 0xFF0000FF);
                    guiGraphics.renderItem(stack, xPos, yPos);

                    // Render durability bar if enabled
                    if (showDurabilityBar && stack.isBarVisible()) {

                        int max = 14;
                        int barWidth = Mth.clamp(Math.round((float) max - (float) stack.getDamageValue() * (float) max / (float) stack.getMaxDamage()), 0, max);
                        int x = (int) ((16 - max) / 2f);
                        int y = 13;

                        x += xPos;
                        y += yPos;

                        guiGraphics.fill(RenderPipelines.GUI, x, y, x + max, y + 2, -16777216);
                        guiGraphics.fill(RenderPipelines.GUI, x, y, x + barWidth, y + 1, ARGB.opaque(stack.getBarColor()));
                    }

                    i++;
                }
            }
            guiGraphics.pose().popMatrix();
        }
    }


}
