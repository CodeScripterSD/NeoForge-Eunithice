package com.craftminerd.eunithice.block;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.custom.ModFlammableRotatedPillarBlock;
import com.craftminerd.eunithice.block.custom.SmelteryControllerBlock;
import com.craftminerd.eunithice.block.custom.TriggerBlock;
import com.craftminerd.eunithice.block.custom.UnlockedAnvilBlock;
import com.craftminerd.eunithice.item.ModItems;
import com.craftminerd.eunithice.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Eunithice.MODID);


    public static final DeferredBlock<Block> DARKWOOD_SAPLING = registerBlock("darkwood_sapling", () ->
            new SaplingBlock(ModTreeGrowers.DARKWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    // Light Oak

    public static final DeferredBlock<Block> DARKWOOD_LOG = registerBlock("darkwood_log", ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));

    public static final DeferredBlock<Block> DARKWOOD_WOOD = registerBlock("darkwood_wood", ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));

    public static final DeferredBlock<Block> STRIPPED_DARKWOOD_LOG = registerBlock("stripped_darkwood_log", ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));

    public static final DeferredBlock<Block> STRIPPED_DARKWOOD_WOOD = registerBlock("stripped_darkwood_wood", ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));

    public static final DeferredBlock<Block> DARKWOOD_LEAVES = registerBlock("darkwood_leaves", () ->
            new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final DeferredBlock<Block> DARKWOOD_PLANKS = registerBlock("darkwood_planks", () ->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });

    public static final DeferredBlock<StairBlock> DARKWOOD_STAIRS = registerBlock("darkwood_stairs", () ->
            new StairBlock(DARKWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final DeferredBlock<SlabBlock> DARKWOOD_SLAB = registerBlock("darkwood_slab", SlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));

    public static final DeferredBlock<FenceBlock> DARKWOOD_FENCE = registerBlock("darkwood_fence", FenceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));

    public static final DeferredBlock<FenceGateBlock> DARKWOOD_FENCE_GATE = registerBlock("darkwood_fence_gate", () ->
            new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    // MR KAUPENJOE has stated that ^ should NOT matter, apparently. Thusly, instead of dedicating more hours to making a custom one, I will inherit vanilla Oak.

    public static final DeferredBlock<DoorBlock> DARKWOOD_DOOR = registerBlock("darkwood_door", () ->
            new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));

    public static final DeferredBlock<TrapDoorBlock> DARKWOOD_TRAPDOOR = registerBlock("darkwood_trapdoor", () ->
            new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));

    public static final DeferredBlock<ButtonBlock> DARKWOOD_BUTTON = registerBlock("darkwood_button", () ->
            new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final DeferredBlock<PressurePlateBlock> DARKWOOD_PRESSURE_PLATE = registerBlock("darkwood_pressure_plate", () ->
            new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<Block> TRIGGER_BLOCK = registerBlock("trigger_block", TriggerBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<Block> SMELTERY_CONTROLLER = registerBlock("smeltery_controller", SmelteryControllerBlock::new);
    public static final DeferredBlock<Block> SMELTERY_INVENTORY = registerSimpleBlock("smeltery_inventory");
    public static final DeferredBlock<Block> SMELTERY_HOUSING = registerSimpleBlock("smeltery_housing");

    public static final DeferredBlock<Block> UNLOCKED_ANVIL = registerRarityBlock("unlocked_anvil", () ->
                    new UnlockedAnvilBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANVIL)), Rarity.RARE);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    // Register Funcs
    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties props) {
        return registerBlockNoItem(name, () -> func.apply(props));
    }

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Function<BlockBehaviour.Properties, T> func) {
        return registerBlockNoItem(name, func, BlockBehaviour.Properties.of());
    }

    private static <T extends Block> DeferredBlock<T> registerRarityBlock(String name, Supplier<T> block, Rarity rarity) {
        DeferredBlock<T> ret = BLOCKS.register(name, block);
        registerBlockItem(ret, new Item.Properties().rarity(rarity));
        return ret;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> ret = BLOCKS.register(name, block);
        registerBlockItem(ret);
        return ret;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties props) {
        return registerBlock(name, () -> func.apply(props));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func) {
        return registerBlock(name, func, BlockBehaviour.Properties.of());
    }

    private static <T extends Block> DeferredBlock<T> registerFuelBlock(String name, Supplier<T> block, int burnTime) {
        DeferredBlock<T> ret = BLOCKS.register(name, block);
        registerFuelBlockItem(ret, burnTime);
        return ret;
    }

    private static <T extends Block> DeferredBlock<T> registerFuelBlock(String name, Function<BlockBehaviour.Properties, T> func, BlockBehaviour.Properties props, int burnTime) {
        DeferredBlock<T> ret = BLOCKS.register(name, () -> func.apply(props));
        registerFuelBlockItem(ret, burnTime);
        return ret;
    }

    private static DeferredBlock<Block> registerFuelBlock(String name, BlockBehaviour.Properties props, int burnTime) {
        DeferredBlock<Block> ret = BLOCKS.registerSimpleBlock(name, props);
        registerFuelBlockItem(ret, burnTime);
        return ret;
    }

    private static DeferredBlock<Block> registerSimpleBlock(String name) {
        DeferredBlock<Block> ret = BLOCKS.registerSimpleBlock(name);
        registerBlockItem(ret);
        return ret;
    }

    private static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties props) {
        DeferredBlock<Block> ret = BLOCKS.registerSimpleBlock(name, props);
        registerBlockItem(ret);
        return ret;
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(DeferredBlock<T> block, Item.Properties properties) {
        return ModItems.ITEMS.registerSimpleBlockItem(block, properties);
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(DeferredBlock<T> block) {
        return ModItems.ITEMS.registerSimpleBlockItem(block);
    }

    private static <T extends Block> DeferredItem<BlockItem> registerFuelBlockItem(DeferredBlock<T> block, int burnTime) {
        return ModItems.ITEMS.register(block.unwrapKey().orElseThrow().location().getPath(), () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return burnTime;
            }
        });
    }
}
