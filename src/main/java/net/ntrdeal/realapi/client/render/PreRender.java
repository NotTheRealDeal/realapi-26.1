package net.ntrdeal.realapi.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.ItemDisplayContext;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PreRender<T>(
        SubmitNodeCollector collector,
        ItemStackRenderState state,
        PoseStack poseStack,
        ItemDisplayContext displayContext,
        ItemStackRenderState.FoilType foilType,
        List<BakedQuad> quads,
        int[] tints,
        int light,
        int overlay,
        int outline,
        @Nullable T data
){}