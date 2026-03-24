package net.ntrdeal.realapi.data;

import net.minecraft.world.item.ItemStack;

public interface StackInsertable {
    default boolean canInsert(ItemStack stack) {
        return true;
    }
}
