package net.ntrdeal.realapi.item.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface ServerItemEvents {
    Event<OpenGuiEvent> OPEN_GUI = EventFactory.createArrayBacked(OpenGuiEvent.class, events -> (player, stack, hand) -> {
        for (OpenGuiEvent event : events) {
            event.open(player, stack, hand);
        }
    });

    interface OpenGuiEvent {
        void open(ServerPlayer player, ItemStack stack, InteractionHand hand);
    }
}