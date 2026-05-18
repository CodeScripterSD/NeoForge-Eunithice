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
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DARKWOOD_PLACED_KEY = registerKey("darkwood_placed");
    public static final ResourceKey<PlacedFeature> FANCY_DARKWOOD_PLACED_KEY = registerKey("fancy_darkwood_placed");
    public static final ResourceKey<PlacedFeature> MEGA_DARKWOOD_PLACED_KEY = registerKey("mega_darkwood_placed");

    public static final ResourceKey<PlacedFeature> ORE_NEUDONITE = registerKey("ore_neudonite");
    public static final ResourceKey<PlacedFeature> ORE_NEUDONITE_SMALL = registerKey("ore_neudonite_small");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Note: the chance value gets 1 / x for some reason; The result of 1 / v MUST be an Integer. Keep that in mind when picking chances.
        register(context, DARKWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_WOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.125f, 2),
                        ModBlocks.DARKWOOD_SAPLING.get()));
        register(context, FANCY_DARKWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FANCY_DARK_WOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.0625f, 1),
                        ModBlocks.DARKWOOD_SAPLING.get()));
        register(context, MEGA_DARKWOOD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MEGA_DARK_WOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1),
                        ModBlocks.DARKWOOD_SAPLING.get()));

        register(context, ORE_NEUDONITE, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_NEUDONITE),
                rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-24), VerticalAnchor.aboveBottom(40))));
        register(context, ORE_NEUDONITE_SMALL, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_NEUDONITE_SMALL),
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange);
    }
}
