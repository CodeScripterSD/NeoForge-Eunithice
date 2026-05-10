package com.craftminerd.eunithice.block.blockentity;

import com.craftminerd.eunithice.block.custom.TriggerBlock;
import com.craftminerd.eunithice.gui.custom.TriggerScreen;
import com.craftminerd.eunithice.network.TriggerBlockData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class TriggerBlockEntity extends BlockEntity implements Configurable {
    private boolean showBoundingBox = true;
    private Vec3 offset = new Vec3(0, 0, 0);
    private Vec3 scale = new Vec3(1, 1, 1);
    private TriggerBlock.Sensitivity sensitivity = TriggerBlock.Sensitivity.LIVING;

    public TriggerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TRIGGER_BLOCK_ENTITY.get(), pos, blockState);
    }

    public boolean usedBy(Player player) {
        if (!player.canUseGameMasterBlocks()) {
            return false;
        } else {
            if (player.getCommandSenderWorld().isClientSide) {
                this.openConfigurationScreen();
            }
            return true;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("shift_x", offset.x());
        tag.putDouble("shift_z", offset.z());
        tag.putDouble("shift_y", offset.y());
        tag.putDouble("size_x", scale.x());
        tag.putDouble("size_z", scale.z());
        tag.putDouble("size_y", scale.y());
        tag.putInt("sensitivity", sensitivity.getId());
        tag.putBoolean("show_bounding_box", showBoundingBox);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        double i = Mth.clamp(tag.getDouble("shift_x"), -8, 8);
        double j = Mth.clamp(tag.getDouble("shift_y"), -8, 8);
        double k = Mth.clamp(tag.getDouble("shift_z"), -8, 8);
        this.offset = new Vec3(i, j, k);
        double a = Mth.clamp(tag.getDouble("size_x"), -8, 8);
        double b = Mth.clamp(tag.getDouble("size_y"), -8, 8);
        double c = Mth.clamp(tag.getDouble("size_z"), -8, 8);
        this.scale = new Vec3(a, b, c);
        this.sensitivity = TriggerBlock.Sensitivity.byInt(tag.getInt("sensitivity"));
        this.showBoundingBox = tag.getBoolean("show_bounding_box");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
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

    public void setData(TriggerBlockData data) {
        this.setSensitivity(data.sensitivity());
        this.setShowBoundingBox(data.showBoundingBox());
        this.setTriggerScale(data.scale());
        this.setTriggerOffset(data.offset());
        this.setChanged();
        this.level.sendBlockUpdated(data.pos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public Vec3 getTriggerOffset() {
        return offset;
    }

    public void setTriggerOffset(Vec3 offset) {
        this.offset = offset;
    }

    public TriggerBlock.Sensitivity getSensitivity() {
        return this.sensitivity;
    }

    public void setSensitivity(TriggerBlock.Sensitivity val) {
        this.sensitivity = val;
    }

    public boolean getShowBoundingBox() {
        return this.showBoundingBox;
    }

    public void setShowBoundingBox(boolean val) {
        this.showBoundingBox = val;
    }

    public Vec3 getTriggerScale() {
        return this.scale;
    }

    public void setTriggerScale(Vec3 size) {
        this.scale = size;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void openConfigurationScreen() {
        Minecraft.getInstance().setScreen(new TriggerScreen(this));
    }
}
