package com.craftminerd.eunithice.block.blockentity;

import com.craftminerd.eunithice.block.custom.TriggerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TriggerBlockEntity extends BlockEntity implements Configurable<Double> {
    public static final String EAST_SHIFT = "x_shift";
    public static final String SOUTH_SHIFT = "z_shift";
    public static final String Y_SHIFT = "y_shift";
    private Vec3 shift = new Vec3(0, 0, 0);

    public TriggerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TRIGGER_BLOCK_ENTITY.get(), pos, blockState);
    }

//    public boolean usedBy(Player player) {
//        if (!player.canUseGameMasterBlocks()) {
//            return false;
//        } else {
//            if (player.getCommandSenderWorld().isClientSide) {
//                player.openMenu(this);
//            }
//
//            return true;
//        }
//    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble(EAST_SHIFT, shift.x());
        tag.putDouble(SOUTH_SHIFT, shift.z());
        tag.putDouble(Y_SHIFT, shift.y());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        double i = Mth.clamp(tag.getDouble(EAST_SHIFT), -48, 48);
        double j = Mth.clamp(tag.getDouble(Y_SHIFT), -48, 48);
        double k = Mth.clamp(tag.getDouble(SOUTH_SHIFT), -48, 48);
        this.shift = new Vec3(i, j, k);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public void setProperty(String name, Double value) {
        switch (name) {
            case EAST_SHIFT -> shift = shift.add(value, 0, 0);
            case SOUTH_SHIFT -> shift = shift.add(0, 0, value);
            case Y_SHIFT -> shift = shift.add(0, value, 0);
        }
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public Double getProperty(String name) {
        return switch (name) {
            case EAST_SHIFT -> shift.x();
            case SOUTH_SHIFT -> shift.z();
            case Y_SHIFT -> shift.y();
            default -> -1d;
        };
    }

    @Override
    public final List<String> getProperties() {
        return List.of(EAST_SHIFT, SOUTH_SHIFT, Y_SHIFT);
    }

    public Vec3 getTriggerOffset() {
        return shift;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TriggerBlockEntity entity) {
        if (state.getBlock() instanceof TriggerBlock block) {
            if (!level.isClientSide) {
                int i = block.getSignalForState(state);
                if (i == 0) {
                    block.checkPressed(level, pos, state, i);
                }
            }
        }
    }

//    @Override
//    public Component getDisplayName() {
//        return Component.translatable("container.eunithice.trigger");
//    }
//
//    @Override
//    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
//        return null;
//    }
}
