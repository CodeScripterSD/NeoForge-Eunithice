package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.STRIPPABLES)
                .add(ModBlocks.DARKWOOD_LOG, new Strippable(ModBlocks.STRIPPED_DARKWOOD_LOG.get()), false)
                .add(ModBlocks.DARKWOOD_WOOD, new Strippable(ModBlocks.STRIPPED_DARKWOOD_WOOD.get()), false);
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModBlocks.DARKWOOD_SAPLING.getId(), new Compostable(0.3f), false)
                .add(ModBlocks.DARKWOOD_LEAVES.getId(), new Compostable(0.3f), false);
        super.gather(provider);
    }
}
