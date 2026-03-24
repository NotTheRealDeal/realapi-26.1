package net.ntrdeal.realapi.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.ntrdeal.realapi.RealAPI;
import net.ntrdeal.realapi.entity.event.EntityAttributeEvents;
import net.ntrdeal.realapi.tags.RealAttributeTags;

public class RealAttributes {
    public static final Holder<Attribute> MOVEMENT_SCALE = register("movement_scale", new RangedAttribute(
            "attribute.realapi.movement_scale", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static final Holder<Attribute> SHIELD_FRAGILITY = register("shield_fragility", new RangedAttribute(
            "attribute.realapi.shield_fragility", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final Holder<Attribute> ARMOR_PENETRATION = register("armor_penetration", new RangedAttribute(
            "attribute.realapi.armor_penetration", 0d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static final Holder<Attribute> APPETITE = register("appetite", new RangedAttribute(
            "attribute.realapi.appetite", 1d, 0.25d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final Holder<Attribute> CHARGE_TIME = register("charge_time", new RangedAttribute(
            "attribute.realapi.charge_time", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final Holder<Attribute> RANGED_ATTACK_MULTIPLIER = register("ranged_attack_multiplier", new RangedAttribute(
            "attribute.realapi.ranged_attack_multiplier", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static final Holder<Attribute> BANE_OF_ADOLESCENCE = register("bane_of_adolescence", new RangedAttribute(
            "attribute.realapi.bane_of_adolescence", 0d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static final Holder<Attribute> FIRE_DAMAGE_MULTIPLIER = register("fire_damage_multiplier", new RangedAttribute(
            "attribute.realapi.fire_damage_multiplier", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final Holder<Attribute> DODGE_CHANCE = register("dodge_chance", new RangedAttribute(
            "attribute.realapi.dodge_chance", 0d, 0d, 1d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static final Holder<Attribute> INTELLIGENCE = register("intelligence", new RangedAttribute(
            "attribute.realapi.intelligence", 1d, 0d, 1024d)
            .setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE));

    public static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, RealAPI.id(name), attribute);
    }

    public static void register() {
        EntityAttributeEvents.UPDATED.register((entity, attribute) -> {
            if (attribute.is(RealAttributeTags.DIMENSIONS_REFRESHER)) entity.refreshDimensions();
        });
    }
}
