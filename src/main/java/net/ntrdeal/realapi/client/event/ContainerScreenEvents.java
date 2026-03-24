package net.ntrdeal.realapi.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public interface ContainerScreenEvents {
    Event<InitEvent> INIT = EventFactory.createArrayBacked(InitEvent.class, events -> (screen, minecraft) -> {
        for (InitEvent event : events) {
            event.init(screen, minecraft);
        }
    });

    Event<SlotMouseActionEvent> SLOT_MOUSE_ACTION = EventFactory.createArrayBacked(SlotMouseActionEvent.class, events -> (minecraft, consumer) -> {
        for (SlotMouseActionEvent event : events) {
            event.add(minecraft, consumer);
        }
    });


    interface InitEvent {
        void init(AbstractContainerScreen<?> screen, Minecraft minecraft);
    }

    interface SlotMouseActionEvent {
        void add(Minecraft minecraft, Consumer<ItemSlotMouseAction> consumer);
    }
}
