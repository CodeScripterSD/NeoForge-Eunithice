package com.craftminerd.eunithice.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity {
    private static final int maxProgress = 200;
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private float rotation;
    private float oRotation;
    private float levitation;
    private float oLevitation;
    private int progress;

    public PedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inv);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        this.progress = tag.getInt("Progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", inventory.serializeNBT(registries));
        tag.putInt("Progress", progress);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public float getoRotation() {
        return oRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public float getoLevitation() {
        return oLevitation;
    }

    public float getLevitation() {
        return levitation;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.getStackInSlot(0);
        if (!stack.isEmpty() && stack.isDamageableItem() && !stack.getComponents().has(DataComponents.UNBREAKABLE)) {
            if (blockEntity.progress >= PedestalBlockEntity.maxProgress) {
                blockEntity.progress = 0;
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 20f, 2f);
                }
                stack.applyComponents(DataComponentMap.builder().set(DataComponents.UNBREAKABLE, new Unbreakable(true)).build());
            } else {
                blockEntity.progress++;
            }
        } else if (blockEntity.progress != 0) {
            blockEntity.progress = 0;
        }

        if (level.isClientSide()) {
            if (!blockEntity.inventory.getStackInSlot(0).isEmpty()) {
                blockEntity.oRotation = blockEntity.rotation;
                blockEntity.oLevitation = blockEntity.levitation;
                blockEntity.levitation = (float) blockEntity.progress / maxProgress / 2;
                blockEntity.rotation = (blockEntity.rotation + 0.5f) % 360f;
            }
        }
    }
}
