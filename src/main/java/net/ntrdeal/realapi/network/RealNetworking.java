package net.ntrdeal.realapi.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.item.stack_holder.StackHolderUtils;

public interface RealNetworking {
    static void register() {
        PayloadTypeRegistry.serverboundPlay().register(RealPacketTypes.BUNDLE_LIKE_INDEX, BundleLikeIndexPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(RealPacketTypes.BUNDLE_LIKE_INDEX, (packet, context) -> {
            int slotIndex = packet.slotIndex(), index = packet.index();
            AbstractContainerMenu menu = context.player().containerMenu;

            if (slotIndex >= 0 && slotIndex < menu.slots.size()) {
                ItemStack stack = menu.slots.get(slotIndex).getItem();
                StackHolderUtils.setIndex(stack, index);
            }
        });
    }
}
