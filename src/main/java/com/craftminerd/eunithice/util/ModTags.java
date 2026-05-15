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

        public static final TagKey<Item> C_GLASS_PANES = commonTag("glass_panes");
        public static final TagKey<Item> C_WOODEN_FENCE_GATES = commonTag("fence_gates/wooden");
        public static final TagKey<Item> C_STRIPPED = commonTag("stripped_logs");
        public static final TagKey<Item> C_STRIPPED_WOOD = commonTag("stripped_woods");
        public static final TagKey<Item> C_BOWS = commonTag("tools/bow");
        public static final TagKey<Item> C_MINING = commonTag("tools/mining_tool");
        public static final TagKey<Item> C_FRUITS = commonTag("foods/fruit");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
        }
        private static TagKey<Item> commonTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_MULTITOOL = tag("mineable/multitool");
        public static final TagKey<Block> DARKWOOD_LOGS = tag("darkwood_logs");
        public static final TagKey<Block> SMELTERY_VALID_BLOCKS = tag("smeltery_valid_blocks");

        public static final TagKey<Block> C_WOODEN_FENCE_GATES = commonTag("fence_gates/wooden");
        public static final TagKey<Block> C_STRIPPED = commonTag("stripped_logs");
        public static final TagKey<Block> C_STRIPPED_WOOD = commonTag("stripped_woods");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, name));
        }
        private static TagKey<Block> commonTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}
