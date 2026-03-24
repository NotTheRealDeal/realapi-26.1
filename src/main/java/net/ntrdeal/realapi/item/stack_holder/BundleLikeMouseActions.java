package net.ntrdeal.realapi.item.stack_holder;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.gui.ItemSlotMouseAction;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.network.BundleLikeIndexPacket;
import org.joml.Vector2i;

public class BundleLikeMouseActions implements ItemSlotMouseAction {
    private final Minecraft minecraft;
    private final ScrollWheelHandler scrollWheelHandler;

    public BundleLikeMouseActions(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.scrollWheelHandler = new ScrollWheelHandler();
    }

    @Override
    public boolean matches(Slot slot) {
        return StackHolderUtils.contains(slot.getItem().getItem());
    }

    @Override
    public boolean onMouseScrolled(double scrollX, double scrollY, int slotIndex, ItemStack stack) {
        int displayCount = StackHolderUtils.getDisplayCount(stack);
        if (displayCount == 0) return false;

        Vector2i wheelVector = this.scrollWheelHandler.onMouseScroll(scrollX, scrollY);
        int wheel = wheelVector.y() == 0 ? -wheelVector.x() : wheelVector.y();

        if (wheel != 0) {
            int selectedItem = StackHolderUtils.getIndex(stack);
            int updatedSelectedItem = ScrollWheelHandler.getNextScrollWheelSelection(wheel, selectedItem, displayCount);
            if (selectedItem != updatedSelectedItem) this.setIndex(stack, slotIndex, updatedSelectedItem);
        }

        return true;
    }

    @Override
    public void onStopHovering(Slot slot) {
        this.resetIndex(slot.getItem(), slot.index);
    }

    @Override
    public void onSlotClicked(Slot slot, ContainerInput input) {
        if (input.equals(ContainerInput.QUICK_MOVE) || input.equals(ContainerInput.SWAP)) {
            this.resetIndex(slot.getItem(), slot.index);
        }
    }

    public void resetIndex(ItemStack stack, int slotIndex) {
        this.setIndex(stack, slotIndex, -1);
    }

    public void setIndex(ItemStack stack, int slotIndex, int index) {
        if (this.minecraft.getConnection() != null && index < StackHolderUtils.getDisplayCount(stack)) {
            StackHolderUtils.setIndex(stack, index);
            ClientPlayNetworking.send(new BundleLikeIndexPacket(slotIndex, index));
        }
    }
}
