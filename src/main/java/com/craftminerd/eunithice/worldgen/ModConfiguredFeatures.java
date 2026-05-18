package com.craftminerd.eunithice.worldgen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.OptionalInt;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_WOOD_KEY = registerKey("dark_wood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DARK_WOOD_KEY = registerKey("fancy_dark_wood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_DARK_WOOD_KEY = registerKey("mega_dark_wood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NEUDONITE = registerKey("ore_neudonite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NEUDONITE_SMALL = registerKey("ore_neudonite_small");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, DARK_WOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LOG.get()),
                new StraightTrunkPlacer(5, 3, 0),
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(2, 0, 2))
                .build());
        register(context, FANCY_DARK_WOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LOG.get()),
                new FancyTrunkPlacer(4, 12, 0),
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .build());
        register(context, MEGA_DARK_WOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LOG.get()),
                new DarkOakTrunkPlacer(5, 5, 3),
                BlockStateProvider.simple(ModBlocks.DARKWOOD_LEAVES.get()),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))
                .build());

        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest grimstoneReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> neudoniteOres = List.of(OreConfiguration.target(stoneReplaceables, ModBlocks.NEUDONITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(grimstoneReplaceables, ModBlocks.DEEPSLATE_NEUDONITE_ORE.get().defaultBlockState()));
        register(context, ORE_NEUDONITE, Feature.ORE, new OreConfiguration(neudoniteOres, 16));
        register(context, ORE_NEUDONITE_SMALL, Feature.ORE, new OreConfiguration(neudoniteOres, 8));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
