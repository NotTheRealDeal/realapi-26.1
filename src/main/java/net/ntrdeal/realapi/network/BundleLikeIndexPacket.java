package net.ntrdeal.realapi.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record BundleLikeIndexPacket(int slotIndex, int index) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, BundleLikeIndexPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, BundleLikeIndexPacket::slotIndex,
            ByteBufCodecs.VAR_INT, BundleLikeIndexPacket::index,
            BundleLikeIndexPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return RealPacketTypes.BUNDLE_LIKE_INDEX;
    }
}
