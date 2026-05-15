package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Eunithice.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.MINEABLE_MULTITOOL)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_HOE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE);

        tag(ModTags.Blocks.DARKWOOD_LOGS)
                .add(ModBlocks.DARKWOOD_LOG.get())
                .add(ModBlocks.DARKWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_DARKWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_DARKWOOD_WOOD.get());

        tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags.Blocks.DARKWOOD_LOGS);

        tag(BlockTags.PLANKS).add(ModBlocks.DARKWOOD_PLANKS.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.DARKWOOD_BUTTON.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.DARKWOOD_DOOR.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.DARKWOOD_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.DARKWOOD_FENCE_GATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.DARKWOOD_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.DARKWOOD_SLAB.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.DARKWOOD_STAIRS.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.DARKWOOD_TRAPDOOR.get());

        tag(BlockTags.LEAVES).add(ModBlocks.DARKWOOD_LEAVES.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.DARKWOOD_LEAVES.get());

        tag(ModTags.Blocks.C_STRIPPED).add(ModBlocks.STRIPPED_DARKWOOD_LOG.get());
        tag(ModTags.Blocks.C_STRIPPED_WOOD).add(ModBlocks.STRIPPED_DARKWOOD_WOOD.get());
        tag(ModTags.Blocks.C_WOODEN_FENCE_GATES).add(ModBlocks.DARKWOOD_FENCE_GATE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.UNLOCKED_ANVIL.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.UNLOCKED_ANVIL.get());
        tag(ModTags.Blocks.SMELTERY_VALID_BLOCKS).add(ModBlocks.SMELTERY_HOUSING.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.DARKWOOD_SAPLING.get());
    }
}
