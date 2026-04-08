package net.ntrdeal.realapi.client.render;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record Update<T>(
        ItemModelResolver resolver,
        ItemStackRenderState state,
        ItemStack stack,
        ItemDisplayContext displayContext,
        @Nullable T data,
        @Nullable ClientLevel level,
        @Nullable ItemOwner owner,
        int seed
){
    public void updateModel(ItemModel model) {
        model.update(this.state, this.stack, this.resolver, this.displayContext, this.level, this.owner, this.seed);
    }
}