package com.craftminerd.eunithice.datagen;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    private static final class ModAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(ModItems.WOODEN_OMNITOOL.get(),
                            Component.translatable("advancements.eunithice.root.title"),
                            Component.translatable("advancements.eunithice.root.description"),
                            ResourceLocation.withDefaultNamespace("textures/block/smooth_stone.png"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false)
                    .addCriterion("recipe_unlocked", RecipeUnlockedTrigger.unlocked(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "wooden_omnitool")))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "root"), existingFileHelper);
            AdvancementHolder hasIronOmnitool = Advancement.Builder.advancement()
                    .display(ModItems.IRON_OMNITOOL.get(),
                            Component.translatable("advancements.eunithice.has_iron_omnitool.title"),
                            Component.translatable("advancements.eunithice.has_iron_omnitool.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_OMNITOOL))
                    .parent(root)
                    .save(saver, ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "has_iron_omnitool"), existingFileHelper);
        }
    }
}
