package com.craftminerd.eunithice.item.component;

import com.craftminerd.eunithice.Eunithice;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Eunithice.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ConfigToolState>> CONFIG_TOOL_STATE = DATA_COMPONENTS.registerComponentType(
            "config_tool_state", builder ->
                    builder.persistent(ConfigToolState.CODEC).cacheEncoding());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
