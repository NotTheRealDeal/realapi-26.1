package net.ntrdeal.realapi.client.render;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record LeftHand() implements ConditionalItemModelProperty {
    public static final MapCodec<LeftHand> MAP_CODEC = MapCodec.unit(new LeftHand());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        return displayContext.leftHand();
    }

    @Override
    public MapCodec<LeftHand> type() {
        return MAP_CODEC;
    }
}