package com.craftminerd.eunithice.network;

import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleDataOnMain(final TriggerBlockData data, final IPayloadContext context) {
        if (context.player().level().getBlockEntity(data.pos()) instanceof TriggerBlockEntity triggerBlockEntity) {
            triggerBlockEntity.setData(data);
        }
    }
}
