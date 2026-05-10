package com.craftminerd.eunithice.gui.custom;

import com.craftminerd.eunithice.block.ModBlocks;
import com.craftminerd.eunithice.gui.ModMenuTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class UnlockedAnvilMenu extends ItemCombinerMenu {
    protected String itemName;
    public int repairItemCountCost;
    private DataSlot cost = DataSlot.standalone();

    public UnlockedAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.UNLOCKED_ANVIL_MENU.get(), containerId, playerInventory, access);
    }

    public UnlockedAnvilMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public UnlockedAnvilMenu(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(i, inventory);
    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, p_266635_ -> true)
                .withSlot(1, 76, 47, p_266634_ -> true)
                .withResultSlot(2, 134, 47)
                .build();
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.UNLOCKED_ANVIL);
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return (player.hasInfiniteMaterials() || player.experienceLevel >= this.cost.get()) && this.cost.get() > 0;
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            player.giveExperienceLevels(-this.cost.get());
        }

        this.inputSlots.setItem(0, ItemStack.EMPTY);
        if (this.repairItemCountCost > 0) {
            ItemStack itemstack = this.inputSlots.getItem(1);
            if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                itemstack.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, itemstack);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        this.cost.set(0);
    }

    @Override
    public void createResult() {
        ItemStack itemstack = this.inputSlots.getItem(0);
        this.cost.set(1);
        int applyCost = 0;
        long repairCost = 0L;
        int k = 0;
//        if (!itemstack.isEmpty()) {
//            // todo: add my own event for the unlocked anvil ig
//            if (!net.neoforged.neoforge.common.CommonHooks.onAnvilChange(this, itemstack, this.inputSlots.getItem(1), resultSlots, itemName, j, this.player)) {
//                return;  // event is canceled or overrides the output item
//            }
//        }
        if (!itemstack.isEmpty() && EnchantmentHelper.canStoreEnchantments(itemstack)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = this.inputSlots.getItem(1);
            ItemEnchantments.Mutable itemenchantments$mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemstack1));
            repairCost += (long)itemstack.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0)).intValue()
                    + (long)itemstack2.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0)).intValue();
            this.repairItemCountCost = 0;
            boolean secondEnchanted = false;
            if (!itemstack2.isEmpty()) {
                secondEnchanted = itemstack2.has(DataComponents.STORED_ENCHANTMENTS);
                if (itemstack1.isDamageableItem() && itemstack1.getItem().isValidRepairItem(itemstack, itemstack2)) {
                    int toRepairBy = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
                    if (toRepairBy <= 0) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.cost.set(0);
                        return;
                    }

                    int repairIterations;
                    for (repairIterations = 0; toRepairBy > 0 && repairIterations < itemstack2.getCount(); repairIterations++) {
                        int newDurability = itemstack1.getDamageValue() - toRepairBy;
                        itemstack1.setDamageValue(newDurability);
                        applyCost++;
                        toRepairBy = Math.min(itemstack1.getDamageValue(), itemstack1.getMaxDamage() / 4);
                    }

                    this.repairItemCountCost = repairIterations;
                } else {
                    if (!secondEnchanted && (!itemstack1.is(itemstack2.getItem()) || !itemstack1.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.cost.set(0);
                        return;
                    }

                    if (itemstack1.isDamageableItem() && !secondEnchanted) {
                        int durabilityLeft = itemstack.getMaxDamage() - itemstack.getDamageValue();
                        int durabilityLeft2 = itemstack2.getMaxDamage() - itemstack2.getDamageValue();
                        int j1 = durabilityLeft2 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = durabilityLeft + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;
                        if (l1 < 0) {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getDamageValue()) {
                            itemstack1.setDamageValue(l1);
                            applyCost += 2;
                        }
                    }

                    ItemEnchantments itemenchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemstack2);
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
                        Holder<Enchantment> holder = entry.getKey();
                        int i2 = itemenchantments$mutable.getLevel(holder);
                        int j2 = entry.getIntValue();
                        j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                        Enchantment enchantment = holder.value();

                        if (j2 > enchantment.getMaxLevel()) {
                            j2 = enchantment.getMaxLevel();
                        }

                        itemenchantments$mutable.set(holder, j2);
                        int l3 = enchantment.getAnvilCost();
                        if (secondEnchanted) {
                            l3 = Math.max(1, l3 / 2);
                        }

                        applyCost += l3 * j2;
                        if (itemstack.getCount() > 1) {
                            applyCost = 40;
                        }
                    }

                }
            }

            if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
                if (!this.itemName.equals(itemstack.getHoverName().getString())) {
                    k = 1;
                    applyCost += k;
                    itemstack1.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                }
            } else if (itemstack.has(DataComponents.CUSTOM_NAME)) {
                k = 1;
                applyCost += k;
                itemstack1.remove(DataComponents.CUSTOM_NAME);
            }
            if (secondEnchanted && !itemstack1.isBookEnchantable(itemstack2)) itemstack1 = ItemStack.EMPTY;

            int fullApplyCost = (int) Mth.clamp(repairCost + (long)applyCost, 0L, 2147483647L);
            this.cost.set(fullApplyCost);
            if (applyCost <= 0) {
                itemstack1 = ItemStack.EMPTY;
            }

            if (k == applyCost && k > 0 && this.cost.get() >= 40) {
                this.cost.set(39);
            }

            if (!itemstack1.isEmpty()) {
                int i3 = itemstack1.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0));
                if (i3 < itemstack2.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0))) {
                    i3 = itemstack2.getOrDefault(DataComponents.REPAIR_COST, Integer.valueOf(0));
                }

                if (k != applyCost || k == 0) {
                    i3 = calculateIncreasedRepairCost(i3);
                }

                itemstack1.set(DataComponents.REPAIR_COST, i3);
                EnchantmentHelper.setEnchantments(itemstack1, itemenchantments$mutable.toImmutable());
            }

            this.resultSlots.setItem(0, itemstack1);
            this.broadcastChanges();
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.cost.set(0);
        }
    }

    public static int calculateIncreasedRepairCost(int oldRepairCost) {
        return (int)Math.min((long)oldRepairCost * 2L + 1L, 2147483647L);
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(2).hasItem()) {
                ItemStack itemstack = this.getSlot(2).getItem();
                if (StringUtil.isBlank(s)) {
                    itemstack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
                }
            }

            this.createResult();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
        return s.length() <= 50 ? s : null;
    }

    public int getCost() {
        return this.cost.get();
    }

    public void setMaximumCost(long value) {
        this.cost.set((int)Mth.clamp(value, 0L, Integer.MAX_VALUE));
    }
}
