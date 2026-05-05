package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.block.blockentity.Configurable;
import com.craftminerd.eunithice.block.blockentity.TriggerBlockEntity;
import com.craftminerd.eunithice.item.component.ConfigToolState;
import com.craftminerd.eunithice.item.component.ModDataComponents;
import com.craftminerd.eunithice.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class ConfigurationTool extends Item {
    public ConfigurationTool(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (!level.isClientSide && player != null) {
            BlockPos blockpos = context.getClickedPos();
            if (!this.handleInteraction(player, level.getBlockState(blockpos), level, blockpos, true, context.getItemInHand())) {
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public boolean handleInteraction(Player player, BlockState stateClicked, LevelAccessor accessor,
                                      BlockPos pos, boolean shouldCycleState, ItemStack stack) {
        Holder<Block> holder = stateClicked.getBlockHolder();
        if (!(accessor.getBlockEntity(pos) instanceof Configurable<?> configurable)) {
            message(player, Component.translatable(this.getDescriptionId() + ".empty", holder.getRegisteredName()));
            return false;
        } else {
            List<String> collection = (List<String>) configurable.getProperties();
            if (collection.isEmpty()) {
                message(player, Component.translatable(this.getDescriptionId() + ".empty", holder.getRegisteredName()));
                return false;
            } else {
                ConfigToolState data = stack.get(ModDataComponents.CONFIG_TOOL_STATE);
                if (data == null) {
                    return false;
                } else {
                    String property = data.properties().get(holder);
                    if (shouldCycleState) {
                        if (property == null) {
                            property = collection.getFirst();
                        }

                        cycle(configurable, property, player.isSecondaryUseActive(), player.getItemInHand(InteractionHand.OFF_HAND).is(Items.STICK) ? 0.1D : 1D);
                        message(player, Component.translatable(this.getDescriptionId() + ".update", property, configurable.getProperty(property)));
                    } else {
                        property = getRelative(collection, property, player.isSecondaryUseActive());
                        stack.set(ModDataComponents.CONFIG_TOOL_STATE, data.withProperty(holder, property));
                        message(player, Component.translatable(this.getDescriptionId() + ".select", property, configurable.getProperty(property)));
                    }
                }
                return true;
            }
        }
    }

    private void cycle(Configurable<?> configurable, String property, boolean secondaryUseActive, double amount) {
        if (configurable instanceof TriggerBlockEntity triggerBlockEntity)
            triggerBlockEntity.setProperty(property, secondaryUseActive ? -amount : amount);
    }

    private static String getRelative(Iterable<String> allowedValues, @Nullable String currentValue, boolean backwards) {
        return backwards ? ModUtil.findPreviousInIterable(allowedValues, currentValue) : ModUtil.findNextInIterable(allowedValues, currentValue);
    }
    private static void message(Player player, Component messageComponent) {
        ((ServerPlayer)player).sendSystemMessage(messageComponent, true);
    }
}
