package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Eunithice.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ModItems.GOLDEN_OMNITOOL.get());
        handheldItem(ModItems.WOODEN_OMNITOOL.get());
        handheldItem(ModItems.STONE_OMNITOOL.get());
        handheldItem(ModItems.IRON_OMNITOOL.get());
        handheldItem(ModItems.DIAMOND_OMNITOOL.get());
        handheldItem(ModItems.NETHERITE_OMNITOOL.get());

        handheldItem(ModItems.EXPERIMENTAL_SHORTBOW.get());

        basicItem(ModBlocks.DARKWOOD_DOOR.asItem());
        blockItem(ModBlocks.TRIGGER_BLOCK, "leaves");

        saplingItem(ModBlocks.DARKWOOD_SAPLING);

        handheldItem(ModItems.CONFIG_TOOL.get());
        basicItem(ModItems.AIMING_ARROW.asItem());
        withExistingParent(ModItems.MECHANICAL_BOW.getId().getPath(), modLoc("item/experimental_bow"));

        withExistingParent(ModItems.POSTAR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder blockItem(DeferredBlock<Block> block, String parent) {
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.parse("block/" + parent)).texture("all",
                ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "block/"+block.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "block/"+item.getId().getPath()));
    }
}
