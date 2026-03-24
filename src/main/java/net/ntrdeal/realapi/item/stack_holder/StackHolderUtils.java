package net.ntrdeal.realapi.item.stack_holder;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StackHolderUtils {
    public static final Map<Item, DataComponentType<? extends StackHolder<?>>> BUNDLE_LIKE_REGISTRY = new HashMap<>();

    public static <T extends StackHolder<T>> void register(Item item, DataComponentType<T> type) {
        BUNDLE_LIKE_REGISTRY.put(item, type);
    }

    @Nullable @SuppressWarnings("unchecked")
    public static <T extends StackHolder<T>> DataComponentType<T> getType(Item item) {
        return (DataComponentType<T>) BUNDLE_LIKE_REGISTRY.get(item);
    }

    public static boolean contains(Item item) {
        return BUNDLE_LIKE_REGISTRY.containsKey(item);
    }

    public static int getIndex(ItemStack stack) {
        DataComponentType<? extends StackHolder<?>> type = getType(stack.getItem());
        if (type == null) return -1;
        StackHolder<?> holder = stack.get(type);
        if (holder == null) return -1;
        return holder.index();
    }

    public static <T extends StackHolder<T>> void setIndex(ItemStack stack, int index) {
        DataComponentType<T> type = getType(stack.getItem());
        if (type == null) return;
        T holder = stack.get(type);
        if (holder == null) return;

        StackHolder.Builder<T> builder = holder.builder();
        builder.setIndex(index);
        stack.set(type, builder.build());
    }

    public static int getDisplayCount(ItemStack stack) {
        DataComponentType<? extends StackHolder<?>> type = getType(stack.getItem());
        if (type == null) return 0;
        StackHolder<?> holder = stack.get(type);
        if (holder == null) return 0;
        return holder.getNumberOfItemsToShow();
    }

    @Nullable
    public static ItemStackTemplate getIndexed(ItemStack stack) {
        DataComponentType<? extends StackHolder<?>> type = getType(stack.getItem());
        if (type == null) return null;
        StackHolder<?> holder = stack.get(type);
        if (holder == null) return null;
        return holder.getSelectedItem();
    }
}
