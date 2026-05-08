package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.DARKWOOD_LOG.get());
        dropSelf(ModBlocks.DARKWOOD_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_DARKWOOD_LOG.get());
        dropSelf(ModBlocks.STRIPPED_DARKWOOD_WOOD.get());
        dropSelf(ModBlocks.DARKWOOD_PLANKS.get());
        dropSelf(ModBlocks.DARKWOOD_SAPLING.get());
        dropSelf(ModBlocks.DARKWOOD_STAIRS.get());
        add(ModBlocks.DARKWOOD_SLAB.get(), block -> createSlabItemTable(ModBlocks.DARKWOOD_SLAB.get()));
        dropSelf(ModBlocks.DARKWOOD_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DARKWOOD_BUTTON.get());
        dropSelf(ModBlocks.DARKWOOD_FENCE.get());
        dropSelf(ModBlocks.DARKWOOD_FENCE_GATE.get());
        dropSelf(ModBlocks.DARKWOOD_TRAPDOOR.get());
        dropSelf(ModBlocks.SMELTER_HOUSING.get());
        dropSelf(ModBlocks.SMELTER_INVENTORY.get());
        dropSelf(ModBlocks.SMELTER_CONTROLLER.get());
        add(ModBlocks.DARKWOOD_DOOR.get(), block -> createDoorTable(ModBlocks.DARKWOOD_DOOR.get()));
        add(ModBlocks.DARKWOOD_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.DARKWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.TRIGGER_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
