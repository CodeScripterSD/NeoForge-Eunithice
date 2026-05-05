package com.craftminerd.eunithice.network;

import com.craftminerd.eunithice.Eunithice;
import com.craftminerd.eunithice.block.custom.TriggerBlock;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record TriggerBlockData(BlockPos pos, Vec3 offset, Vec3 scale, TriggerBlock.Sensitivity sensitivity, boolean showBoundingBox) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TriggerBlockData> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "trigger_block_data"));

    public static final StreamCodec<ByteBuf, Vec3> VEC3_STREAM_CODEC = new StreamCodec<>() {
        public Vec3 decode(ByteBuf buf) {
            return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        public void encode(ByteBuf buf, Vec3 vec3) {
            buf.writeDouble(vec3.x());
            buf.writeDouble(vec3.y());
            buf.writeDouble(vec3.z());
        }
    };
    public static final StreamCodec<ByteBuf, TriggerBlockData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            TriggerBlockData::pos,
            VEC3_STREAM_CODEC,
            TriggerBlockData::offset,
            VEC3_STREAM_CODEC,
            TriggerBlockData::scale,
            TriggerBlock.Sensitivity.STREAM_CODEC,
            TriggerBlockData::sensitivity,
            ByteBufCodecs.BOOL,
            TriggerBlockData::showBoundingBox,
            TriggerBlockData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
