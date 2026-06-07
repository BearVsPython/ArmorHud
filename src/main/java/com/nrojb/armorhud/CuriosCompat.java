package com.nrojb.armorhud;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.Map;

public class CuriosCompat {

    public static ArrayList<ItemStack> getCuriosItems(Player player) {

        ArrayList<ItemStack> curiosItems = new ArrayList<ItemStack>();

        CuriosApi.getCuriosInventory(player).ifPresent(curios -> {
            for (Map.Entry<String, ICurioStacksHandler> entry : curios.getCurios().entrySet()) {
                IDynamicStackHandler stacks = entry.getValue().getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack curiosItem = stacks.getStackInSlot(i);
                    if (curiosItem.equals(ItemStack.EMPTY)) {
                        continue;
                    }
                    curiosItems.add(curiosItem);
                }
            }
        });

        return curiosItems;
    }
}
