package net.ntrdeal.realapi.item.stack_holder;

import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.Bees;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.ntrdeal.realapi.data.StackInsertable;
import net.ntrdeal.realapi.data.WeightHolder;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface StackHolder<T extends StackHolder<T>> extends StackInsertable, TooltipComponent {
    List<ItemStackTemplate> stacks();
    Supplier<DataResult<Fraction>> weightSupplier();

    default Fraction scale() {
        return Fraction.ONE;
    }

    @Override
    default boolean canInsert(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().canFitInsideContainerItems() && StackInsertable.super.canInsert(stack);
    }

    default Stream<ItemStack> stackStream() {
        return this.stacks().stream().map(ItemStackTemplate::create);
    }

    default int size() {
        return this.stacks().size();
    }

    default boolean isEmpty() {
        return this.stacks().isEmpty();
    }

    default int index() {
        return -1;
    }

    @Nullable
    default ItemStackTemplate getSelectedItem() {
        return this.index() == -1 ? null : this.stacks().get(this.index());
    }

    default int getNumberOfItemsToShow() {
        int numberOfItemStacks = this.size();
        int availableItemsToShow = numberOfItemStacks > 12 ? 11 : 12;
        int itemsOnNonFullRow = numberOfItemStacks % 4;
        int emptySpaceOnNonFullRow = itemsOnNonFullRow == 0 ? 0 : 4 - itemsOnNonFullRow;
        return Math.min(numberOfItemStacks, availableItemsToShow - emptySpaceOnNonFullRow);
    }

    default boolean isEqualToHolder(StackHolder<?> holder) {
        return this.equals(holder) || this.stacks().equals(holder.stacks());
    }

    default int getHash() {
        return this.stacks().hashCode();
    }

    default String getString() {
        return "StackHolder: " + this.stacks();
    }

    default DataResult<Fraction> weight() {
        return this.weightSupplier().get();
    }

    @Nullable
    default DataResult<Fraction> overrideWeight(ItemInstance instance) {
        if (instance.typeHolder().value() instanceof WeightHolder holder && holder.getWeight(instance) instanceof DataResult<Fraction> weight) {
            return weight.map(fraction -> fraction.multiplyBy(this.scale()));
        } else if (instance.get(DataComponents.BUNDLE_CONTENTS) instanceof BundleContents contents) {
            return contents.weight().map(weight -> weight.add(Fraction.getFraction(1, 16)).multiplyBy(this.scale()));
        } else if (instance.get(DataComponents.BEES) instanceof Bees(List<BeehiveBlockEntity.Occupant> bees) && !bees.isEmpty()) {
            return DataResult.success(Fraction.ONE.multiplyBy(this.scale()));
        } else return null;
    }

    default DataResult<Fraction> getPerWeight(ItemInstance instance) {
        DataResult<Fraction> override = this.overrideWeight(instance);
        return override != null ? override : DataResult.success(Fraction.getFraction(1, instance.getMaxStackSize()).multiplyBy(this.scale()));
    }

    default DataResult<Fraction> getWeight(ItemInstance instance) {
        return this.getPerWeight(instance).map(weight -> weight.multiplyBy(Fraction.getFraction(instance.count(), 1)));
    }

    default DataResult<Fraction> computeWeight() {
        Fraction totalWeight = Fraction.ZERO;
        for (ItemStackTemplate template : this.stacks()) {
            DataResult<Fraction> weight = this.getWeight(template);
            if (weight.isError()) return weight;
            totalWeight = totalWeight.add(weight.getOrThrow());
        }
        return DataResult.success(totalWeight);
    }

    @SuppressWarnings("unchecked")
    default T getThis() {
        return (T) this;
    }

    T build(Builder<T> builder);

    default Builder<T> builder() {
        return new Builder<>(this.getThis());
    }

    static <T extends StackHolder<T>> void registerBundleLike(Item item, DataComponentType<T> type) {
        StackHolderUtils.register(item, type);
    }

    class Builder<T extends StackHolder<T>> {
        public final T holder;
        public final List<ItemStack> stacks;
        public Fraction weight;
        public int index;

        public Builder(T holder) {
            this.holder = holder;
            DataResult<Fraction> weight = holder.weight();
            if (weight.isError()) {
                this.stacks = new ArrayList<>();
                this.weight = Fraction.ZERO;
                this.index = -1;
            } else {
                this.stacks = new ArrayList<>(holder.size());
                holder.stacks().forEach(template -> this.stacks.add(template.create()));
                this.weight = weight.getOrThrow();
                this.index = holder.index();
            }
        }

        public Builder<T> clear() {
            this.stacks.clear();
            this.weight = Fraction.ZERO;
            this.index = -1;
            return this;
        }

        public int getMaxAmount(Fraction weight) {
            Fraction weightLeft = Fraction.ONE.subtract(this.weight);
            return Math.max(weightLeft.divideBy(weight).intValue(), 0);
        }

        public int tryAdd(ItemStack stack) {
            if (!this.holder.canInsert(stack)) return 0;
            DataResult<Fraction> maxWeight = this.holder.getPerWeight(stack);
            if (maxWeight.isError()) return 0;
            Fraction oneWeight = maxWeight.getOrThrow();
            int adding = this.getMaxAmount(oneWeight);
            if (adding == 0) return 0;

            this.weight = this.weight.add(oneWeight.multiplyBy(Fraction.getFraction(adding, 1)));
            ItemStack insertingStack = stack.split(adding);

            for (ItemStack listedStack : this.stacks.reversed()) {
                if (!listedStack.isEmpty() && listedStack.isStackable() && ItemStack.isSameItemSameComponents(insertingStack, listedStack)) {
                    int decrementAmount = Math.min(insertingStack.count(), listedStack.getMaxStackSize() - listedStack.count());
                    insertingStack.shrink(decrementAmount);
                    listedStack.grow(decrementAmount);
                }
            }
            if (!insertingStack.isEmpty()) {
                this.stacks.addFirst(insertingStack.copy());
                insertingStack.shrink(insertingStack.count());
            }

            return adding;
        }

        public int tryTransfer(Player player, Slot slot) {
            ItemStack stack = slot.getItem();
            if (!this.holder.canInsert(stack)) return 0;
            DataResult<Fraction> oneWeight = this.holder.getPerWeight(stack);
            if (oneWeight.isError()) return 0;
            int maxAmount = this.getMaxAmount(oneWeight.getOrThrow());
            return this.tryAdd(slot.safeTake(stack.count(), maxAmount, player));
        }

        public boolean isAllowedIndex(int index) {
            return index >= 0 && index < this.stacks.size();
        }

        public void setIndex(int index) {
            this.index = this.index != index && isAllowedIndex(index) ? index : -1;
        }

        @Nullable
        public ItemStack removeStack() {
            if (this.stacks.isEmpty()) return null;
            int removeIndex = isAllowedIndex(this.index) ? this.index : 0;
            ItemStack stack = this.stacks.remove(removeIndex).copy();
            this.weight = this.weight.subtract(this.holder.getWeight(stack).getOrThrow());
            this.setIndex(-1);
            return stack;
        }

        public List<ItemStackTemplate> toTemplate() {
            return this.stacks.stream().map(ItemStackTemplate::fromNonEmptyStack).toList();
        }

        public T build() {
            return this.holder.build(this);
        }

        public int getIndex() {
            return this.index;
        }
    }
}