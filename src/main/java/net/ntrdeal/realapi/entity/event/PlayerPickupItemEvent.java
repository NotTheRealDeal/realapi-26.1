package net.ntrdeal.realapi.entity.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public interface PlayerPickupItemEvent {
    Event<PlayerPickupItemEvent> PICKUP = EventFactory.createArrayBacked(PlayerPickupItemEvent.class, events -> (inventory, entity, stack) -> {
        for (PlayerPickupItemEvent event : events) {
            if (event.pickup(inventory, entity, stack)) return false;
        }

        return true;
    });

    boolean pickup(Inventory inventory, ItemEntity entity, ItemStack stack);
}