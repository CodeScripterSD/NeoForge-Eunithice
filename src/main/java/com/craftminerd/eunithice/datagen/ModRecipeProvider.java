package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.item.ModItems;
import com.craftminerd.eunithice.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLDEN_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.GOLDEN_HOE)
                .define('A', Items.GOLDEN_AXE)
                .define('S', Items.GOLDEN_SHOVEL)
                .define('P', Items.GOLDEN_PICKAXE)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WOODEN_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.WOODEN_HOE)
                .define('A', Items.WOODEN_AXE)
                .define('S', Items.WOODEN_SHOVEL)
                .define('P', Items.WOODEN_PICKAXE)
                .unlockedBy("has_wood", has(ItemTags.PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STONE_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.STONE_HOE)
                .define('A', Items.STONE_AXE)
                .define('S', Items.STONE_SHOVEL)
                .define('P', Items.STONE_PICKAXE)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.IRON_HOE)
                .define('A', Items.IRON_AXE)
                .define('S', Items.IRON_SHOVEL)
                .define('P', Items.IRON_PICKAXE)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.DIAMOND_HOE)
                .define('A', Items.DIAMOND_AXE)
                .define('S', Items.DIAMOND_SHOVEL)
                .define('P', Items.DIAMOND_PICKAXE)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.NETHERITE_OMNITOOL.get())
                .pattern("ASP")
                .pattern("H# ")
                .pattern(" # ")
                .define('#', Items.STICK)
                .define('H', Items.NETHERITE_HOE)
                .define('A', Items.NETHERITE_AXE)
                .define('S', Items.NETHERITE_SHOVEL)
                .define('P', Items.NETHERITE_PICKAXE)
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(recipeOutput);
        eunithiceNetheriteSmithing(recipeOutput, ModItems.DIAMOND_OMNITOOL, RecipeCategory.TOOLS, ModItems.NETHERITE_OMNITOOL);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EXPERIMENTAL_SHORTBOW.get())
                .pattern(" QS")
                .pattern("EOS")
                .pattern(" QS")
                .define('S', Items.STRING)
                .define('Q', Items.QUARTZ)
                .define('O', Items.OBSIDIAN)
                .define('E', Items.ENDER_EYE)
                .unlockedBy(getHasName(Items.OBSIDIAN), has(Items.OBSIDIAN))
                .save(recipeOutput);

        woodFromLogs(recipeOutput, ModBlocks.DARKWOOD_WOOD, ModBlocks.DARKWOOD_LOG);
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_DARKWOOD_WOOD, ModBlocks.STRIPPED_DARKWOOD_LOG);
        planksFromLogs(recipeOutput, ModBlocks.DARKWOOD_PLANKS, ModTags.Items.DARKWOOD_LOGS, 4);
        stairBuilder(ModBlocks.DARKWOOD_STAIRS, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARKWOOD_SLAB, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        fenceBuilder(ModBlocks.DARKWOOD_FENCE, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        fenceGateBuilder(ModBlocks.DARKWOOD_FENCE_GATE, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        doorBuilder(ModBlocks.DARKWOOD_DOOR, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        trapdoorBuilder(ModBlocks.DARKWOOD_TRAPDOOR, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        pressurePlateBuilder(RecipeCategory.REDSTONE, ModBlocks.DARKWOOD_PRESSURE_PLATE, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
        buttonBuilder(ModBlocks.DARKWOOD_BUTTON, Ingredient.of(ModBlocks.DARKWOOD_PLANKS))
                .unlockedBy(getHasName(ModBlocks.DARKWOOD_PLANKS), has(ModBlocks.DARKWOOD_PLANKS)).save(recipeOutput);
    }

    protected static void eunithiceNetheriteSmithing(RecipeOutput recipeOutput, ItemLike ingredientItem, RecipeCategory category, ItemLike resultItem) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(ingredientItem),
                Ingredient.of(Items.NETHERITE_INGOT), category, resultItem.asItem())
                .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, getItemName(resultItem) + "_smithing"));
    }
}
