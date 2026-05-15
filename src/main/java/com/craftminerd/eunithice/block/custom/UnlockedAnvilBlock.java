package com.craftminerd.eunithice.block.custom;

import com.craftminerd.eunithice.gui.custom.UnlockedAnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UnlockedAnvilBlock extends AnvilBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.repair");
    public UnlockedAnvilBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(((containerId, playerInventory, player) ->
                new UnlockedAnvilMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos))), CONTAINER_TITLE);
    }

    @Override
    protected void falling(FallingBlockEntity fallingEntity) {
        fallingEntity.setHurtsEntities(4.0F, 80);
    }
}
