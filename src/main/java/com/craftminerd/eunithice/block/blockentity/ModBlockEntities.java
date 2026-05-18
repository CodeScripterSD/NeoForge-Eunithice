package com.craftminerd.eunithice.block.blockentity;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Eunithice.MODID);

    public static final Supplier<BlockEntityType<TriggerBlockEntity>> TRIGGER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("trigger_block_entity",
            () -> BlockEntityType.Builder.of(TriggerBlockEntity::new, ModBlocks.TRIGGER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<SmelteryControllerBlockEntity>> SMELTERY_CONTROLLER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("smeltery_controller_block_entity",
            () -> BlockEntityType.Builder.of(SmelteryControllerBlockEntity::new, ModBlocks.SMELTERY_CONTROLLER.get()).build(null));

    public static final Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("pedestal_block_entity",
            () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ModBlocks.PEDESTAL.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
