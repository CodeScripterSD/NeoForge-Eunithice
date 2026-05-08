package com.craftminerd.eunithice.event;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.entity.ModEntities;
import com.craftminerd.eunithice.entity.client.PostarModel;
import com.craftminerd.eunithice.entity.custom.PostarEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Eunithice.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PostarModel.LAYER_LOCATION, PostarModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.POSTAR.get(), PostarEntity.createAttributes().build());
    }
}
