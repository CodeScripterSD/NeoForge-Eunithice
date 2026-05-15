package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.item.ModItems;
import com.craftminerd.eunithice.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Eunithice.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.OMNITOOLS)
                .add(ModItems.GOLDEN_OMNITOOL.get())
                .add(ModItems.WOODEN_OMNITOOL.get())
                .add(ModItems.STONE_OMNITOOL.get())
                .add(ModItems.IRON_OMNITOOL.get())
                .add(ModItems.DIAMOND_OMNITOOL.get())
                .add(ModItems.NETHERITE_OMNITOOL.get());

        tag(ItemTags.PICKAXES).addTag(ModTags.Items.OMNITOOLS);
        tag(ItemTags.SHOVELS).addTag(ModTags.Items.OMNITOOLS);
        tag(ItemTags.HOES).addTag(ModTags.Items.OMNITOOLS);
        tag(ItemTags.AXES).addTag(ModTags.Items.OMNITOOLS);

        tag(ItemTags.PLANKS).add(ModBlocks.DARKWOOD_PLANKS.asItem());
        tag(ItemTags.WOODEN_BUTTONS).add(ModBlocks.DARKWOOD_BUTTON.asItem());
        tag(ItemTags.WOODEN_DOORS).add(ModBlocks.DARKWOOD_DOOR.asItem());
        tag(ItemTags.WOODEN_FENCES).add(ModBlocks.DARKWOOD_FENCE.asItem());
        tag(ItemTags.FENCE_GATES).add(ModBlocks.DARKWOOD_FENCE_GATE.asItem());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.DARKWOOD_PRESSURE_PLATE.asItem());
        tag(ItemTags.WOODEN_SLABS).add(ModBlocks.DARKWOOD_SLAB.asItem());
        tag(ItemTags.WOODEN_STAIRS).add(ModBlocks.DARKWOOD_STAIRS.asItem());
        tag(ItemTags.WOODEN_TRAPDOORS).add(ModBlocks.DARKWOOD_TRAPDOOR.asItem());

        tag(ModTags.Items.DARKWOOD_LOGS)
                .add(ModBlocks.DARKWOOD_LOG.asItem())
                .add(ModBlocks.DARKWOOD_WOOD.asItem())
                .add(ModBlocks.STRIPPED_DARKWOOD_LOG.asItem())
                .add(ModBlocks.STRIPPED_DARKWOOD_WOOD.asItem());

        tag(ItemTags.LOGS_THAT_BURN).addTag(ModTags.Items.DARKWOOD_LOGS);

        tag(ModTags.Items.SHORTBOW_ENCHANTABLE).add(ModItems.EXPERIMENTAL_SHORTBOW.get());
        tag(ItemTags.BOW_ENCHANTABLE).add(ModItems.EXPERIMENTAL_SHORTBOW.get());
        tag(ItemTags.ARROWS).add(ModItems.AIMING_ARROW.get());

        tag(ModTags.Items.C_WOODEN_FENCE_GATES).add(ModBlocks.DARKWOOD_FENCE_GATE.asItem());
        tag(ModTags.Items.C_STRIPPED).add(ModBlocks.STRIPPED_DARKWOOD_LOG.asItem());
        tag(ModTags.Items.C_STRIPPED_WOOD).add(ModBlocks.STRIPPED_DARKWOOD_WOOD.asItem());
        tag(ModTags.Items.C_BOWS).add(ModItems.EXPERIMENTAL_SHORTBOW.get());
        tag(ModTags.Items.C_MINING).addTag(ModTags.Items.OMNITOOLS);
        tag(ItemTags.SAPLINGS).add(ModBlocks.DARKWOOD_SAPLING.asItem());
    }
}
