package com.bearvspython.armorhud;

import net.minecraft.world.item.ItemStack;

public interface IArmor {

    public int getMaxDamage();
    public int getDamageValue();
    public int getBarColor();
    public boolean isBarVisible();
    public ItemStack getStack();
}
