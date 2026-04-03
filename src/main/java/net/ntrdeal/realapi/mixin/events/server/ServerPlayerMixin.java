package net.ntrdeal.realapi.mixin.events.server;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ntrdeal.realapi.item.events.ServerItemEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "openItemGui", at = @At("TAIL"))
    private void ntrdeal$openGuiEvent(ItemStack itemStack, InteractionHand hand, CallbackInfo ci) {
        ServerItemEvents.OPEN_GUI.invoker().open((ServerPlayer)(Player)this, itemStack, hand);
    }
}