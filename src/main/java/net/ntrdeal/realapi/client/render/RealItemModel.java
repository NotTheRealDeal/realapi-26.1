package net.ntrdeal.realapi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RealItemModel<T> extends ItemModel {
    RenderStateDataKey<Map<Identifier, RealItemModel<Object>>> RENDERER_KEY = RenderStateDataKey.create();
    RenderStateDataKey<Map<Identifier, Object>> DATA_KEY = RenderStateDataKey.create();

    @Nullable T extractData(Update<T> extracting);
    void update(Update<T> extracted);
    void preRender(PreRender<T> preRender);
    Identifier rendererID();

    @SuppressWarnings("unchecked")
    default void useData(
            SubmitNodeCollector collector, ItemStackRenderState state, PoseStack poseStack, ItemDisplayContext displayContext,
            ItemStackRenderState.FoilType foilType, List<BakedQuad> quads, int[] tints, int light, int overlay, int outline
    ) {
        this.preRender(new PreRender<>(
                collector,
                state,
                poseStack,
                displayContext,
                foilType,
                quads,
                tints,
                light,
                overlay,
                outline,
                (T) state.getDataOrDefault(DATA_KEY, Collections.emptyMap()).get(this.rendererID())
        ));
    }

    @Override
    default void update(
            ItemStackRenderState state, ItemStack stack,
            ItemModelResolver resolver, ItemDisplayContext displayContext,
            @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
        this.update(new Update<>(
                resolver, state, stack, displayContext,
                this.setData(resolver, state, stack, displayContext, level, owner, seed), level, owner, seed
        ));
    }

    @SuppressWarnings("unchecked")
    default T setData(
            ItemModelResolver resolver, ItemStackRenderState state, ItemStack stack,
            ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed
    ) {
        Map<Identifier, RealItemModel<Object>> renderMap = state.getData(RENDERER_KEY);

        if (renderMap == null) {
            renderMap = new HashMap<>();
            renderMap.put(this.rendererID(), (RealItemModel<Object>) this);
            state.setData(RENDERER_KEY, renderMap);
        } else renderMap.put(this.rendererID(), (RealItemModel<Object>) this);

        T data = this.extractData(new Update<>(resolver, state, stack, displayContext, null, level, owner, seed));

        Map<Identifier, Object> dataMap = state.getData(DATA_KEY);

        if (dataMap == null) {
            dataMap = new HashMap<>();
            dataMap.put(this.rendererID(), data);
            state.setData(DATA_KEY, dataMap);
        } dataMap.put(this.rendererID(), data);

        return data;
    }
}