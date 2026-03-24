package net.ntrdeal.realapi.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.ntrdeal.realapi.RealAPI;

public interface RealPacketTypes {
    CustomPacketPayload.Type<BundleLikeIndexPacket> BUNDLE_LIKE_INDEX = register("bundle_like_index");

    static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> register(String id) {
        return new CustomPacketPayload.Type<>(RealAPI.id(id));
    }
}
