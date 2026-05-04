package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Eunithice.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DARKWOOD_PLANKS);
        stairsBlock(ModBlocks.DARKWOOD_STAIRS.get(), blockTexture(ModBlocks.DARKWOOD_PLANKS.get()));
        slabBlock(ModBlocks.DARKWOOD_SLAB.get(), blockTexture(ModBlocks.DARKWOOD_PLANKS.get()), blockTexture(ModBlocks.DARKWOOD_PLANKS.get()));
        buttonBlockWithItem(ModBlocks.DARKWOOD_BUTTON, ModBlocks.DARKWOOD_PLANKS);
        pressurePlateBlock(ModBlocks.DARKWOOD_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DARKWOOD_PLANKS.get()));
        fenceBlockWithItem(ModBlocks.DARKWOOD_FENCE, ModBlocks.DARKWOOD_PLANKS);
        fenceGateBlock(ModBlocks.DARKWOOD_FENCE_GATE.get(), blockTexture(ModBlocks.DARKWOOD_PLANKS.get()));
        doorBlockWithRenderType(ModBlocks.DARKWOOD_DOOR.get(), modLoc("block/darkwood_door_bottom"), modLoc("block/darkwood_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.DARKWOOD_TRAPDOOR.get(),  modLoc("block/darkwood_trapdoor"), true, "cutout");

        logBlock((RotatedPillarBlock) ModBlocks.DARKWOOD_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.DARKWOOD_WOOD.get(), blockTexture(ModBlocks.DARKWOOD_LOG.get()), blockTexture(ModBlocks.DARKWOOD_LOG.get()));
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_DARKWOOD_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_DARKWOOD_WOOD.get(), blockTexture(ModBlocks.STRIPPED_DARKWOOD_LOG.get()), blockTexture(ModBlocks.STRIPPED_DARKWOOD_LOG.get()));

        foliageBlock(ModBlocks.DARKWOOD_SAPLING);
        leavesBlock(ModBlocks.DARKWOOD_LEAVES);

        blockItem(ModBlocks.DARKWOOD_LOG);
        blockItem(ModBlocks.DARKWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_DARKWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_DARKWOOD_WOOD);

        blockItem(ModBlocks.DARKWOOD_STAIRS);
        blockItem(ModBlocks.DARKWOOD_SLAB);
        blockItem(ModBlocks.DARKWOOD_PRESSURE_PLATE);
        blockItem(ModBlocks.DARKWOOD_FENCE_GATE);
        blockItem(ModBlocks.DARKWOOD_TRAPDOOR, "_bottom");
    }

    private void foliageBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), blockTexture(block.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(block.get())).renderType("cutout"));
    }

    private void buttonBlockWithItem(DeferredBlock<ButtonBlock> block, DeferredBlock<?> source) {
        buttonBlock(block.get(), blockTexture(source.get()));
        itemModels().getBuilder(block.getId().getPath()).parent(new ModelFile.UncheckedModelFile("minecraft:block/button_inventory"))
                .texture("texture", "eunithice:block/" + source.getId().getPath());
    }

    private void fenceBlockWithItem(DeferredBlock<FenceBlock> block, DeferredBlock<?> source) {
        fenceBlock(block.get(), blockTexture(source.get()));
        itemModels().getBuilder(block.getId().getPath()).parent(new ModelFile.UncheckedModelFile("minecraft:block/fence_inventory"))
                .texture("texture", "eunithice:block/" + source.getId().getPath());
    }

    private void blockItem(DeferredBlock<?> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("eunithice:block/" + block.getId().getPath()));
    }
    private void blockItem(DeferredBlock<?> block, String appendix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile("eunithice:block/" + block.getId().getPath() + appendix));
    }

    private void blockWithItem(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
