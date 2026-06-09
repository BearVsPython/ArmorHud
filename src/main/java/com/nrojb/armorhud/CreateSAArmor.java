package com.nrojb.armorhud;

import net.mcreator.createstuffadditions.item.*;
import net.mcreator.createstuffadditions.network.CreateSaModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class CreateSAArmor implements IArmor {

    private static Class<?>[] specialArmor = {
            AndesiteJetpackItem.class,
            BrassJetpackItem.class,
            CopperJetpackItem.class,
            NetheriteJetpackItem.class,

            //AndesiteExoskeletonItem.class,
            //BrassExoskeletonItem.class,
            //CopperExoskeletonItem.class,
    };

    private static int fuelBarColor = 16099696;
    private static int waterBarColor = 8361684;


    private ItemStack itemstack;

    public CreateSAArmor(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public int getMaxDamage() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        return (int) CreateSaModVariables.MapVariables.get(player.level()).gadgetCapacity;
    }

    public int getDamageValue() {
        float fuel = 0.0F;
        float water = 0.0F;
        IFluidHandlerItem handler = (IFluidHandlerItem) itemstack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler != null) {
            fuel = (float)Math.round((float)handler.getFluidInTank(0).getAmount() * 0.1F);
            water = (float)Math.round((float)handler.getFluidInTank(1).getAmount() * 0.1F);
        }

        return (int) (getMaxDamage() - (fuel > water ? water : fuel));
    }

    public int getBarColor() {
        float fuel = 0.0F;
        float water = 0.0F;
        IFluidHandlerItem handler = (IFluidHandlerItem) itemstack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler != null) {
            fuel = (float)Math.round((float)handler.getFluidInTank(0).getAmount() * 0.1F);
            water = (float)Math.round((float)handler.getFluidInTank(1).getAmount() * 0.1F);
        }

        return fuel > water ? waterBarColor : fuelBarColor;
    }

    public boolean isBarVisible() {
        return true;
    }

    public void render(GuiGraphics guiGraphics, int xPos, int yPos) {
        guiGraphics.renderItem(itemstack, xPos, yPos);
    }

    public static boolean isCreateSAArmor(ItemStack armor) {
        for (Class<?> clazz : specialArmor) {
            if (clazz.isInstance(armor.getItem())) {
                return true;
            }
        }
        return false;
    }


    public ItemStack getStack() {
        return this.itemstack;
    }
}
