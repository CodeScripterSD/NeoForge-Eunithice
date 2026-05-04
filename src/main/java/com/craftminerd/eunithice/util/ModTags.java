package com.craftminerd.eunithice.util;

import com.craftminerd.eunithice.Eunithice;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> OMNITOOLS = tag("omnitools");
        public static final TagKey<Item> SHORTBOW_ENCHANTABLE = tag("shortbow_enchantable");
        public static final TagKey<Item> DARKWOOD_LOGS = tag("darkwood_logs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
        }
    }
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_MULTITOOL = tag("mineable/multitool");
        public static final TagKey<Block> DARKWOOD_LOGS = tag("darkwood_logs");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
        }
    }
}
