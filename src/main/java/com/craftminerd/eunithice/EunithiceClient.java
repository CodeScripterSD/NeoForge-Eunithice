package com.craftminerd.eunithice;

import com.craftminerd.eunithice.entity.ModEntities;
import com.craftminerd.eunithice.entity.client.PostarRenderer;
import com.craftminerd.eunithice.gui.ModMenuTypes;
import com.craftminerd.eunithice.gui.custom.UnlockedAnvilMenu;
import com.craftminerd.eunithice.gui.custom.UnlockedAnvilScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Eunithice.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Eunithice.MODID, value = Dist.CLIENT)
public class EunithiceClient {
    public EunithiceClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.POSTAR.get(), PostarRenderer::new);
    }

    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.UNLOCKED_ANVIL_MENU.get(), UnlockedAnvilScreen::new);
    }
}
