package com.craftminerd.eunithice.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier WOOD = new SimpleTier(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59*4,
            2f,
            0f,
            15,
            () -> Ingredient.of(ItemTags.PLANKS));
    public static final Tier STONE = new SimpleTier(
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            131*4,
            4f,
            1f,
            5,
            () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));
    public static final Tier IRON = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            250*4,
            6f,
            2f,
            14,
            () -> Ingredient.of(Items.IRON_INGOT));
    public static final Tier DIAMOND = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1561*4,
            8f,
            3f,
            10,
            () -> Ingredient.of(Items.DIAMOND));
    public static final Tier GOLD = new SimpleTier(
            BlockTags.INCORRECT_FOR_GOLD_TOOL,
            32*4,
            12f,
            0f,
            22,
            () -> Ingredient.of(Items.GOLD_INGOT));
    public static final Tier NETHERITE = new SimpleTier(
            BlockTags.INCORRECT_FOR_GOLD_TOOL,
            2031*4,
            9f,
            4f,
            15,
            () -> Ingredient.of(Items.NETHERITE_INGOT));
}
