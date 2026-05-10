package com.craftminerd.eunithice.network;

import com.craftminerd.eunithice.Eunithice;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record RenameItemPacket(String name) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RenameItemPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Eunithice.MODID, "rename_item"));
    public static final StreamCodec<ByteBuf, RenameItemPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            RenameItemPacket::name,
            RenameItemPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
