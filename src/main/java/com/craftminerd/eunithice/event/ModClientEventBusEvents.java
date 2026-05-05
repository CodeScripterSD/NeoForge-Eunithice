package com.craftminerd.eunithice.event;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.block.blockentity.ModBlockEntities;
import com.craftminerd.eunithice.block.renderer.TriggerBlockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Eunithice.MODID, value = Dist.CLIENT)
public class ModClientEventBusEvents {
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, blockAndTintGetter, pos, tintIndex) -> 0x4c4c4c, ModBlocks.DARKWOOD_LEAVES.get());
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> 0x4c4c4c, ModBlocks.DARKWOOD_LEAVES.get());
        event.register((stack, tintIndex) -> 0xFF9A0a, ModBlocks.TRIGGER_BLOCK.get());
    }
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.TRIGGER_BLOCK_ENTITY.get(), TriggerBlockRenderer::new);
    }
}
