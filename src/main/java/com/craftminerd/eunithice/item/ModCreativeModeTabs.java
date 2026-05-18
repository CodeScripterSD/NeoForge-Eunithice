package com.craftminerd.eunithice.item;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Eunithice.MODID);

    // Creates a creative tab with the id "eunithice:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EUNITHICE_TAB = CREATIVE_MODE_TABS.register("eunithice", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.eunithice")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> ModItems.IRON_OMNITOOL.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.DARKWOOD_SAPLING);
                output.accept(ModItems.MACROFIBER_APPLE);
                output.accept(ModBlocks.DARKWOOD_LEAVES);
                output.accept(ModBlocks.DARKWOOD_LOG);
                output.accept(ModBlocks.DARKWOOD_WOOD);
                output.accept(ModBlocks.STRIPPED_DARKWOOD_LOG);
                output.accept(ModBlocks.STRIPPED_DARKWOOD_WOOD);
                output.accept(ModBlocks.DARKWOOD_PLANKS);
                output.accept(ModBlocks.DARKWOOD_STAIRS);
                output.accept(ModBlocks.DARKWOOD_SLAB);
                output.accept(ModBlocks.DARKWOOD_FENCE);
                output.accept(ModBlocks.DARKWOOD_FENCE_GATE);
                output.accept(ModBlocks.DARKWOOD_DOOR);
                output.accept(ModBlocks.DARKWOOD_TRAPDOOR);
                output.accept(ModBlocks.DARKWOOD_PRESSURE_PLATE);
                output.accept(ModBlocks.DARKWOOD_BUTTON);
                output.accept(ModBlocks.NEUDONITE_ORE);
                output.accept(ModBlocks.DEEPSLATE_NEUDONITE_ORE);
                output.accept(ModBlocks.NEUDONITE_BLOCK);
                output.accept(ModItems.NEUDONITE_INGOT);
                output.accept(ModBlocks.RAW_NEUDONITE_BLOCK);
                output.accept(ModItems.RAW_NEUDONITE);
                output.accept(ModBlocks.PEDESTAL);
                output.accept(ModBlocks.TRIGGER_BLOCK);
                output.accept(ModBlocks.SMELTERY_CONTROLLER);
                output.accept(ModBlocks.SMELTERY_INVENTORY);
                output.accept(ModBlocks.SMELTERY_HOUSING);
                output.accept(ModBlocks.UNLOCKED_ANVIL);
                output.accept(ModItems.GOLDEN_OMNITOOL);
                output.accept(ModItems.WOODEN_OMNITOOL);
                output.accept(ModItems.STONE_OMNITOOL);
                output.accept(ModItems.IRON_OMNITOOL);
                output.accept(ModItems.DIAMOND_OMNITOOL);
                output.accept(ModItems.NETHERITE_OMNITOOL);
                output.accept(ModItems.EXPERIMENTAL_SHORTBOW);
                output.accept(ModItems.AIMING_ARROW);
                output.accept(ModItems.CONFIG_TOOL);

                output.accept(ModItems.POSTAR_SPAWN_EGG);
            }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
