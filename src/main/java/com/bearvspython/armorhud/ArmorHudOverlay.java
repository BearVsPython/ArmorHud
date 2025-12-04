package com.bearvspython.armorhud;

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

            ItemStack[] armorStack = new ItemStack[4];
            for (int i = 3; i >= 0; i--) {
                //armorStack[(3 - i)] = mc.player.getInventory().getItem(Inventory.INVENTORY_SIZE + i);
                armorStack[i] = mc.player.getInventory().getItem(Inventory.INVENTORY_SIZE + i);
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
            float scale = Mth.clamp(Config.scale.getAsInt()/50f, 0.2f, 2f);
            int spacing = Config.spacing.getAsInt();
            boolean isVertical = Config.layoutStyle.get() == Config.LayoutStyle.VERTICAL;
            boolean showDurabilityBar = Config.showDurabilityBar.getAsBoolean();
            boolean showItemSlot = Config.showHotbarSlot.getAsBoolean();
            int xPosition = Config.horizontalOffset.get();
            int yPosition = Config.verticalOffset.get();

            // Adjust x and y position to be relative to the center of the armor HUD
            int itemCount = 0;

            for (ItemStack stack : armorStack) {
                if (!stack.isEmpty()) {
                    itemCount++;
                }
            }
            int total;
            if (isVertical) {
                if (showItemSlot) {
                    total = (int) (itemCount * ((((ITEM_SLOT_TEXTURE_HEIGHT * scale) - (ITEM_ICON_SIZE * scale)) + spacing)) - spacing);
                } else {
                    total = (int) (itemCount * ((ITEM_ICON_SIZE * scale) + spacing) - spacing);
                }
                yPosition += total / 2;
                xPosition -= (int) ((ITEM_ICON_SIZE * scale) / 2);
            } else {
                if (showItemSlot) {
                    total = (int) ((itemCount * (((ITEM_SLOT_TEXTURE_WIDTH * scale) - (ITEM_ICON_SIZE * scale)) + spacing)) - spacing);
                } else {
                    total = (int) (itemCount * ((ITEM_ICON_SIZE * scale) + spacing) - spacing);
                }
                xPosition -= total/2;
                yPosition -= (int) ((ITEM_ICON_SIZE * scale) / 2);
            }



            // Adjust starting position based on anchor points
            xPosition += xReference;
            yPosition += yReference;



            int i = 0;
            for (ItemStack stack : armorStack) {
                if (!stack.isEmpty()) {
                    float xPos = (xPosition + (scale * (isVertical ? 0 : (i * (spacing + (showItemSlot ? ((ITEM_SLOT_TEXTURE_WIDTH) - 8) : ITEM_ICON_SIZE))))));
                    float yPos = (yPosition + (scale * (isVertical ? - (i * (spacing + (showItemSlot ? ((ITEM_SLOT_TEXTURE_HEIGHT) - 3) : ITEM_ICON_SIZE))) : 0)));

                    guiGraphics.pose().pushMatrix();
                    guiGraphics.pose().translate(xPos, yPos);
                    guiGraphics.pose().scale(scale);


                    // Render item slot if enabled
                    if (showItemSlot) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ITEM_SLOT_TEXTURE, -3, -4, ITEM_SLOT_TEXTURE_WIDTH, ITEM_SLOT_TEXTURE_HEIGHT);
                    }

                    // Render armor item
                    guiGraphics.renderItem(stack, 0, 0);
                    if (showDurabilityBar && stack.isBarVisible()) {

                        int max = 14;
                        int barWidth = Mth.clamp(Math.round((float) max - (float) stack.getDamageValue() * (float) max / (float) stack.getMaxDamage()), 0, max);
                        int x = (int) ((16 - max) / 2f);
                        int y = 13;

                        guiGraphics.fill(RenderPipelines.GUI, x, y, x + max, y + 2, -16777216);
                        guiGraphics.fill(RenderPipelines.GUI, x, y, x + barWidth, y + 1, ARGB.opaque(stack.getBarColor()));
                    }

                    guiGraphics.pose().popMatrix();
                    i++;
                }
            }
        }
    }


}