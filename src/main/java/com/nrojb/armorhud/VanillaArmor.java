package com.nrojb.armorhud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class VanillaArmor implements IArmor {

    private ItemStack itemstack;

    public VanillaArmor(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public int getMaxDamage() {
        return itemstack.getMaxDamage();
    }

    public int getDamageValue() {
        return itemstack.getDamageValue();
    }

    public int getBarColor() {
        return itemstack.getBarColor();
    }

    public boolean isBarVisible() {
        return itemstack.isBarVisible();
    }

    public void render(GuiGraphics guiGraphics, int xPos, int yPos) {
        guiGraphics.renderItem(itemstack, xPos, yPos);
    }

    public ItemStack getStack() {
        return this.itemstack;
    }
}
