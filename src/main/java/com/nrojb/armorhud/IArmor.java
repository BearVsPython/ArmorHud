package com.nrojb.armorhud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public interface IArmor {

    public int getMaxDamage();
    public int getDamageValue();
    public int getBarColor();
    public boolean isBarVisible();
    public void render(GuiGraphics g, int x, int y);
    public ItemStack getStack();
}
