package com.craftminerd.eunithice;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.block.blockentity.ModBlockEntities;
import com.craftminerd.eunithice.entity.ModEntities;
import com.craftminerd.eunithice.gui.ModMenuTypes;
import com.craftminerd.eunithice.item.ModCreativeModeTabs;
import com.craftminerd.eunithice.item.ModItems;
import com.craftminerd.eunithice.item.component.ModDataComponents;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Eunithice.MODID)
public class Eunithice {
    public static final String MODID = "eunithice";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Eunithice(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        ModEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

}
