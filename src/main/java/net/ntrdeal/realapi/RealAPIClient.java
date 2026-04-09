package net.ntrdeal.realapi;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.ntrdeal.realapi.client.event.ContainerScreenEvents;
import net.ntrdeal.realapi.client.item.stack_holder.BundleLikeHasIndexed;
import net.ntrdeal.realapi.client.item.stack_holder.BundleLikeSpecialRenderer;
import net.ntrdeal.realapi.client.render.FirstPerson;
import net.ntrdeal.realapi.client.render.LeftHand;
import net.ntrdeal.realapi.client.render.MultiProperty;
import net.ntrdeal.realapi.item.stack_holder.BundleLikeMouseActions;

public class RealAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ContainerScreenEvents.SLOT_MOUSE_ACTION.register((minecraft, consumer) -> {
            consumer.accept(new BundleLikeMouseActions(minecraft));
        });

        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("multi_property"), MultiProperty.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("bundle_like_has"), BundleLikeHasIndexed.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("first_person"), FirstPerson.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RealAPI.id("left_hand"), LeftHand.MAP_CODEC);

        ItemModels.ID_MAPPER.put(RealAPI.id("bundle_like"), BundleLikeSpecialRenderer.Unbaked.MAP_CODEC);
    }
}