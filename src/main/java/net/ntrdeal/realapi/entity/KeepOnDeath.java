package net.ntrdeal.realapi.entity;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.item.component.RealDataComponents;
import net.ntrdeal.realapi.tags.RealItemTags;

public interface KeepOnDeath {
    static void register() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                Inventory newInventory = newPlayer.getInventory();
                Inventory oldInventory = oldPlayer.getInventory();

                for (int index = 0; index < newInventory.getContainerSize(); index++) {
                    ItemStack stack = oldInventory.getItem(index);
                    if (keepOnDeath(stack)) newInventory.setItem(index, oldInventory.getItem(index));
                }
            }
        });
    }

    static boolean keepOnDeath(ItemInstance instance) {
        return instance.is(RealItemTags.KEEP_ON_DEATH) || instance.get(RealDataComponents.KEEP_ON_DEATH) != null;
    }
}
