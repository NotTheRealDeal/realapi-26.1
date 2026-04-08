package net.ntrdeal.realapi.client.render;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record MultiProperty(List<ConditionalItemModelProperty> properties) implements ConditionalItemModelProperty {
    public static final MapCodec<MultiProperty> MAP_CODEC = RecordCodecBuilder.mapCodec(property -> property.group(
            ConditionalItemModelProperties.MAP_CODEC.codec().listOf().fieldOf("properties").forGetter(MultiProperty::properties)
    ).apply(property, MultiProperty::new));

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        for (ConditionalItemModelProperty property : this.properties) {
            if (!property.get(itemStack, level, owner, seed, displayContext)) return false;
        }

        return true;
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
