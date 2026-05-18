package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.util.ModTags;
import com.ibm.icu.impl.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.minecraft.world.item.HoeItem.changeIntoState;

public class OmnitoolItem extends DiggerItem {
    public OmnitoolItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.MINEABLE_MULTITOOL, properties.attributes(OmnitoolItem.createAttributes(tier, 3, -3.4f)));
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(blockPos);

        Optional<BlockState> axeStripping = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));
        Optional<BlockState> axeScraping = axeStripping.isPresent() ? Optional.empty() : Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false));
        Optional<BlockState> axeUnwaxing = axeStripping.isPresent() || axeScraping.isPresent() ? Optional.empty() : Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false));
        Optional<BlockState> hoeTilling = (!player.isCrouching()) || axeStripping.isPresent() || axeScraping.isPresent() || axeUnwaxing.isPresent() ? Optional.empty() : Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.HOE_TILL, false));
        Optional<BlockState> shovelFlattening = axeStripping.isPresent() || axeScraping.isPresent() || axeUnwaxing.isPresent() || hoeTilling.isPresent()? Optional.empty() : Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false));
        ItemStack stack = context.getItemInHand();

        Optional<BlockState> fuckidfkman = Optional.empty();

        if (axeStripping.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS);
            fuckidfkman = axeStripping;
        } else if (axeScraping.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS);
            level.levelEvent(player, 3005, blockPos, 0);
            fuckidfkman = axeScraping;
        } else if (axeUnwaxing.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS);
            level.levelEvent(player, 3006, blockPos, 0);
            fuckidfkman = axeUnwaxing;
        } else if (hoeTilling.isPresent()) {
            Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = Pair.of(ctx -> true, changeIntoState(hoeTilling.get()));
            Predicate<UseOnContext> predicate = pair.first;
            Consumer<UseOnContext> consumer = pair.second;
            if (predicate.test(context)) {
                level.playSound(player, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS);
                if (!level.isClientSide) {
                    consumer.accept(context);
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        } else if (shovelFlattening.isPresent()) {
            if (context.getClickedFace() == Direction.DOWN) {
                return InteractionResult.PASS;
            } else {
                BlockState state1 = shovelFlattening.get();
                BlockState state2 = null;
                if (level.isEmptyBlock(blockPos.above())) {
                    level.playSound(player, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS);
                    state2 = state1;
                } else if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
                    if (!level.isClientSide()) {
                        level.levelEvent(null, 1009, blockPos, 0);
                    }

                    CampfireBlock.dowse(context.getPlayer(), level, blockPos, state);
                    state2 = state.setValue(CampfireBlock.LIT, Boolean.FALSE);
                }

                if (state2 != null) {
                    if (!level.isClientSide) {
                        level.setBlock(blockPos, state2, 11);
                        if (player != null) {
                            context.getItemInHand().hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                        }
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    return InteractionResult.PASS;
                }
            }
        }
        if (fuckidfkman.isPresent()) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, stack);
            }

            level.setBlock(blockPos, fuckidfkman.get(), 11);
            if (player != null) {
                stack.hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility)
                || ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!stack.getComponents().has(DataComponents.UNBREAKABLE)) tooltipComponents.add(Component.translatable("tooltip.eunithice.omnitool_durability", stack.getMaxDamage()-stack.getDamageValue()));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
