package com.craftminerd.eunithice.item;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.item.custom.ExperimentalBow;
import com.craftminerd.eunithice.item.custom.OmnitoolItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Eunithice.MODID);

    public static final DeferredItem<Item> GOLDEN_OMNITOOL = ITEMS.register("golden_omnitool", () ->
            new OmnitoolItem(ModTiers.GOLD, new Item.Properties()));
    public static final DeferredItem<Item> WOODEN_OMNITOOL = ITEMS.register("wooden_omnitool", () ->
            new OmnitoolItem(ModTiers.WOOD, new Item.Properties()));
    public static final DeferredItem<Item> STONE_OMNITOOL = ITEMS.register("stone_omnitool", () ->
            new OmnitoolItem(ModTiers.STONE, new Item.Properties()));
    public static final DeferredItem<Item> IRON_OMNITOOL = ITEMS.register("iron_omnitool", () ->
            new OmnitoolItem(ModTiers.IRON, new Item.Properties()));
    public static final DeferredItem<Item> DIAMOND_OMNITOOL = ITEMS.register("diamond_omnitool", () ->
            new OmnitoolItem(ModTiers.DIAMOND, new Item.Properties()));
    public static final DeferredItem<Item> NETHERITE_OMNITOOL = ITEMS.register("netherite_omnitool", () ->
            new OmnitoolItem(ModTiers.NETHERITE, new Item.Properties()));

    public static final DeferredItem<Item> EXPERIMENTAL_SHORTBOW = ITEMS.register("experimental_bow", () ->
            new ExperimentalBow(new Item.Properties().durability(212)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
