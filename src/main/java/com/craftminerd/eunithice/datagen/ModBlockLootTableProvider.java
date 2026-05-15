package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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
        dropSelf(ModBlocks.SMELTERY_HOUSING.get());
        dropSelf(ModBlocks.SMELTERY_INVENTORY.get());
        dropSelf(ModBlocks.SMELTERY_CONTROLLER.get());
        add(ModBlocks.DARKWOOD_DOOR.get(), block -> createDoorTable(ModBlocks.DARKWOOD_DOOR.get()));
        add(ModBlocks.DARKWOOD_LEAVES.get(), block -> createFruitLeavesDrops(block, ModBlocks.DARKWOOD_SAPLING.get(), ModItems.MACROFIBER_APPLE.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.TRIGGER_BLOCK.get());
        dropSelf(ModBlocks.UNLOCKED_ANVIL.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    protected LootTable.Builder createFruitLeavesDrops(Block oakLeavesBlock, Block saplingBlock, Item fruit, float... chances) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(oakLeavesBlock, saplingBlock, chances)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(HAS_SHEARS.or(this.hasSilkTouch()).invert())
                                .add(
                                        (this.applyExplosionCondition(oakLeavesBlock, LootItem.lootTableItem(fruit)))
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(
                                                                registrylookup.getOrThrow(Enchantments.FORTUNE), 0.0025F, 0.0025555557F, 0.003125F, 0.004166662F, 0.0125F
                                                        )
                                                )
                                )
                );
    }
}
