package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.UnknownNullability;

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

        saplingItem(ModBlocks.DARKWOOD_SAPLING);
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "block/"+item.getId().getPath()));
    }
}
