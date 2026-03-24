package net.ntrdeal.realapi.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.ntrdeal.realapi.entity.RealAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @WrapMethod(method = "getChargeDuration")
    private static int ntrdeal$chargeTime(ItemStack crossbow, LivingEntity entity, Operation<Integer> original) {
        return Mth.ceil(original.call(crossbow, entity) * entity.getAttributeValue(RealAttributes.CHARGE_TIME));
    }
}
