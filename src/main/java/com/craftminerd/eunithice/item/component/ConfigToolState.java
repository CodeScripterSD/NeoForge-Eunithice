package com.craftminerd.eunithice.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.Map;

// TODO: Update tool data component to have a Configurable.
// Ex: TriggerBlockEntity, TriggerBlockEntity implements function getConfigs();
// getConfigs() -> Map<String name, List<?> possibleValues>
// When Config Tool used, get the config from ConfigToolState.block and change property ConfigToolState.property
public record ConfigToolState(Map<Holder<Block>, String> properties) {
    public static final ConfigToolState EMPTY = new ConfigToolState(Map.of());
    public static final Codec<ConfigToolState> CODEC = Codec.dispatchedMap(
            BuiltInRegistries.BLOCK.holderByNameCodec(),
            block -> Codec.STRING
            ).xmap(ConfigToolState::new, ConfigToolState::properties);

    public ConfigToolState withProperty(Holder<Block> block, String property) {
        return new ConfigToolState(Util.copyAndPut(this.properties, block, property));
    }
}
