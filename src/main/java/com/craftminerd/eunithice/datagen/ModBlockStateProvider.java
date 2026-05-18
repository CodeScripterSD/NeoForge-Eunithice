package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

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

        blockWithItem(ModBlocks.SMELTERY_HOUSING);
        horizontallyRotatedBlock(ModBlocks.SMELTERY_CONTROLLER, blockTexture(ModBlocks.SMELTERY_CONTROLLER.get()), blockTexture(ModBlocks.SMELTERY_HOUSING.get()));
        blockWithItem(ModBlocks.SMELTERY_INVENTORY);

        blockItem(ModBlocks.SMELTERY_CONTROLLER);
        blockItem(ModBlocks.DARKWOOD_STAIRS);
        blockItem(ModBlocks.DARKWOOD_SLAB);
        blockItem(ModBlocks.DARKWOOD_PRESSURE_PLATE);
        blockItem(ModBlocks.DARKWOOD_FENCE_GATE);
        blockItem(ModBlocks.DARKWOOD_TRAPDOOR, "_bottom");

        particleOnly(ModBlocks.TRIGGER_BLOCK);
        anvilBlock(ModBlocks.UNLOCKED_ANVIL, blockTexture(ModBlocks.UNLOCKED_ANVIL.get()));
        blockItem(ModBlocks.UNLOCKED_ANVIL);

        blockWithItem(ModBlocks.NEUDONITE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_NEUDONITE_ORE);
        blockWithItem(ModBlocks.RAW_NEUDONITE_BLOCK);
        blockWithItem(ModBlocks.NEUDONITE_BLOCK);
    }
    private void anvilBlock(DeferredBlock<Block> block, ResourceLocation anvil) {
        horizontallyRotatedBlock(block.get(),
                $ -> models().getBuilder(BuiltInRegistries.BLOCK.getKey(block.get()).getPath())
                        .parent(new ModelFile.UncheckedModelFile(ResourceLocation.parse("minecraft:block/template_anvil")))
                        .texture("particle", anvil)
                        .texture("body", anvil)
                        .texture("top", anvil + "_top"));
    }

    private void horizontallyRotatedBlock(DeferredBlock<Block> block, ResourceLocation front, ResourceLocation others) {
        horizontallyRotatedBlock(block.get(),
                $ -> models().getBuilder(BuiltInRegistries.BLOCK.getKey(block.get()).getPath())
                        .parent(new ModelFile.UncheckedModelFile(ResourceLocation.parse("minecraft:block/orientable")))
                        .texture("front", front)
                        .texture("side", others)
                        .texture("top", others));
    }

    private void horizontallyRotatedBlock(Block block, Function<BlockState, ModelFile> it) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(HorizontalDirectionalBlock.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(it.apply(state))
                            .rotationY(((int) dir.toYRot() - 180) % 360)
                            .build();
                });
    }

    private void particleOnly(DeferredBlock<Block> block) {
        simpleBlock(block.get(),
                models().getBuilder(BuiltInRegistries.BLOCK.getKey(block.get()).getPath()).texture("particle", blockTexture(block.get())));
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
