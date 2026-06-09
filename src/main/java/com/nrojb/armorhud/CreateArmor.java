package com.nrojb.armorhud;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class CreateArmor implements IArmor {
    private ItemStack itemstack;

    public CreateArmor(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public int getMaxDamage() {
        return BacktankUtil.maxAir(itemstack);
    }

    public int getDamageValue() {
        return this.getMaxDamage() - BacktankUtil.getAir(itemstack);
    }

    public int getBarColor() {
        return BacktankItem.BAR_COLOR;
    }

    public boolean isBarVisible() {
        return true;
    }

    public void render(GuiGraphics guiGraphics, int xPos, int yPos) {
        guiGraphics.renderItem(itemstack, xPos, yPos);
    }

    public static boolean isBacktank(ItemStack itemstack) {
        return itemstack.getItem() instanceof BacktankItem;
    }

    public ItemStack getStack() {
        return itemstack;
    }
}
