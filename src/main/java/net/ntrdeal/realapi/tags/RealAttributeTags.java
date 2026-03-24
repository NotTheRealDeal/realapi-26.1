package net.ntrdeal.realapi.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.ntrdeal.realapi.RealAPI;

public interface RealAttributeTags {
    TagKey<Attribute> DIMENSIONS_REFRESHER = bind("dimensions_refresher");

    private static TagKey<Attribute> bind(final String name) {
        return TagKey.create(Registries.ATTRIBUTE, RealAPI.id(name));
    }
}
