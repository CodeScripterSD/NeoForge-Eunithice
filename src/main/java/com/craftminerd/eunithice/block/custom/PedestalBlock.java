package com.craftminerd.eunithice.block.custom;

import com.craftminerd.eunithice.block.blockentity.ModBlockEntities;
import com.craftminerd.eunithice.block.blockentity.PedestalBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {
    private static final MapCodec CODEC = simpleCodec(PedestalBlock::new);
    private static final VoxelShape SHAPE = Shapes.or(Block.box(1, 0, 1, 15, 3, 15), Block.box(3, 3, 3, 13, 15, 13));
    public PedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntity::tick);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity) {
                pedestalBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity) {
            if (pedestalBlockEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                pedestalBlockEntity.inventory.insertItem(0, stack.copy(), false);
                if (!player.getAbilities().instabuild)
                    stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if (!pedestalBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
                ItemStack stackOnPedestal = pedestalBlockEntity.inventory.extractItem(0, 1, false);
                if (!player.getInventory().add(stackOnPedestal)) {
                    player.drop(stackOnPedestal, false);
                }
                pedestalBlockEntity.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
