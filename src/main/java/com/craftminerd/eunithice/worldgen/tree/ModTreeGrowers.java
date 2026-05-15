package com.craftminerd.eunithice.worldgen.tree;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower DARKWOOD = new TreeGrower(Eunithice.MODID + ":darkwood",
            0.1f,
            Optional.of(ModConfiguredFeatures.MEGA_DARK_WOOD_KEY),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.DARK_WOOD_KEY),
            Optional.of(ModConfiguredFeatures.FANCY_DARK_WOOD_KEY),
            Optional.empty(),
            Optional.empty());
}
