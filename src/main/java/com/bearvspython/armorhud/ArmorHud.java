package com.bearvspython.armorhud;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ArmorHud.MODID, dist = Dist.CLIENT)
public class ArmorHud {
    public static final String MODID = "armorhud";
    public static final boolean CREATE_LOADED = net.neoforged.fml.ModList.get().isLoaded("create");
    public static final boolean CURIOS_LOADED = net.neoforged.fml.ModList.get().isLoaded("curios");

    public ArmorHud(IEventBus modEventBus, ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        container.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        modEventBus.addListener(this::onRegisterHudHandler);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    private void onRegisterHudHandler(final RegisterGuiLayersEvent event) {
        ArmorHudOverlay.register(event);
    }
}
