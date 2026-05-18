package com.craftminerd.eunithice.worldgen;

import com.craftminerd.eunithice.Eunithice;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_TREE_DARKWOOD = registerKey("add_tree_darkwood");
    public static final ResourceKey<BiomeModifier> ADD_MEGA_TREE_DARKWOOD = registerKey("add_mega_tree_darkwood");

    public static final ResourceKey<BiomeModifier> ADD_NEUDONITE_ORE = registerKey("add_neudonite_ore");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_DARKWOOD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DARKWOOD_PLACED_KEY), placedFeatures.getOrThrow(ModPlacedFeatures.FANCY_DARKWOOD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MEGA_TREE_DARKWOOD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MEGA_DARKWOOD_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_NEUDONITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORE_NEUDONITE), placedFeatures.getOrThrow(ModPlacedFeatures.ORE_NEUDONITE_SMALL)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
    }
}
