package com.craftminerd.eunithice.item.enchantment;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> FIRING_SPEED =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "firing_speed"));
    public static final ResourceKey<Enchantment> TELEPORTITIS =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "teleportitis"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, FIRING_SPEED, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModTags.Items.SHORTBOW_ENCHANTABLE),
                5,
                4,
                Enchantment.dynamicCost(5, 7),
                Enchantment.dynamicCost(5, 7),
                2,
                EquipmentSlotGroup.MAINHAND)));

        register(context, TELEPORTITIS, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                5,
                4,
                Enchantment.dynamicCost(5, 7),
                Enchantment.dynamicCost(5, 7),
                2,
                EquipmentSlotGroup.MAINHAND)));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
