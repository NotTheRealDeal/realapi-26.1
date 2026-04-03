package net.ntrdeal.realapi.item.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public interface ClientItemEvents {
    Event<OpenGuiEvent> OPEN_GUI = EventFactory.createArrayBacked(OpenGuiEvent.class, events -> (player, stack, hand) -> {
        for (OpenGuiEvent event : events) {
            event.open(player, stack, hand);
        }
    });

    interface OpenGuiEvent {
        void open(LocalPlayer player, ItemStack stack, InteractionHand hand);
    }
}