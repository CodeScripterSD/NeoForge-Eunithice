package com.craftminerd.eunithice;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.block.blockentity.ModBlockEntities;
import com.craftminerd.eunithice.block.renderer.PedestalBlockRenderer;
import com.craftminerd.eunithice.block.renderer.TriggerBlockRenderer;
import com.craftminerd.eunithice.entity.ModEntities;
import com.craftminerd.eunithice.entity.client.AimingArrowRenderer;
import com.craftminerd.eunithice.entity.client.PostarRenderer;
import com.craftminerd.eunithice.gui.ModMenuTypes;
import com.craftminerd.eunithice.gui.custom.UnlockedAnvilScreen;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
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

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, blockAndTintGetter, pos, tintIndex) -> 0x42364e, ModBlocks.DARKWOOD_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> 0x42364e, ModBlocks.DARKWOOD_LEAVES.get());
        event.register((stack, tintIndex) -> 0xFF9A0a, ModBlocks.TRIGGER_BLOCK.get());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.TRIGGER_BLOCK_ENTITY.get(), (context) -> new TriggerBlockRenderer());
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), (context) -> new PedestalBlockRenderer());
        event.registerEntityRenderer(ModEntities.AIMING_ARROW.get(), AimingArrowRenderer::new);
    }
}
