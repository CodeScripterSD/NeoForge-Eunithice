package com.craftminerd.eunithice.worldgen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DARKWOOD_PLACED_KEY = registerKey("darkwood_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DARKWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_WOOD_KEY),
                // Note to shadow: the chance value gets 1 / x for some reason; The result of 1 / v MUST be an Integer. Keep that in mind when picking chances.
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.125f, 1),
                        ModBlocks.DARKWOOD_SAPLING.get())); // This is the generation restriction for the tree.
        // Basically any block will work here, but we pick the sapling so that generation is restricted to DIRT and GRASS.
        // Trees would grow on themselves without this, and also inside of caves and a bunch of dumb places.
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
