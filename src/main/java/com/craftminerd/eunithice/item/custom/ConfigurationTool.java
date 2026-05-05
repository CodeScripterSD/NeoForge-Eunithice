package com.craftminerd.eunithice.item.custom;

import com.craftminerd.eunithice.block.blockentity.Configurable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
        if (level.isClientSide && player != null) {
            BlockPos blockpos = context.getClickedPos();
            if (!(level.getBlockEntity(blockpos) instanceof Configurable configurable)) {
                return InteractionResult.FAIL;
            }
            configurable.openConfigurationScreen();
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
