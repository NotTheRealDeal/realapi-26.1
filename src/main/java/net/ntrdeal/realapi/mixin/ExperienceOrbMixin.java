package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {
    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;getValue()I"))
    private int ntrdeal$intelligence(ExperienceOrb orb, Operation<Integer> original, Player player) {
        return Math.round(original.call(orb) * (float) player.getAttributeValue(RealAttributes.INTELLIGENCE));
    }
}
