package net.ntrdeal.realapi.mixin.client.render;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemStackRenderState.class)
public interface DisplayContextAccessor {
    @Accessor("displayContext")
    ItemDisplayContext ntrdeal$displayContext();
}
