package com.craftminerd.eunithice.network;

import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import com.craftminerd.eunithice.gui.custom.UnlockedAnvilMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleTriggerData(final TriggerBlockData data, final IPayloadContext context) {
        if (context.player().level().getBlockEntity(data.pos()) instanceof TriggerBlockEntity triggerBlockEntity) {
            triggerBlockEntity.setData(data);
        }
    }

    public static void handleRenameItem(final RenameItemPacket packet, final IPayloadContext context) {
        if (context.player().containerMenu instanceof UnlockedAnvilMenu unlockedAnvilMenu) {
            unlockedAnvilMenu.setItemName(packet.name());
        }
    }
}
