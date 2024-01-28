package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents switching items between hands with the swap hands binding. This avoids the need to actually unset the keybinding.
 */
@Mixin(KeyMapping.class)
public abstract class DisableSwapHandsKeyBindingMixin {

    @Shadow
    protected abstract void release();

    @Inject(method = "matchesMouse", at = @At(value = "HEAD"), cancellable = true)
    public void disableMouseBinding(int code, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == Minecraft.getInstance().options.keySwapOffhand) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideOffhandSlot()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "matches", at = @At(value = "HEAD"), cancellable = true)
    public void disableKeyboardBinding(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == Minecraft.getInstance().options.keySwapOffhand) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideOffhandSlot()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "consumeClick", at = @At(value = "HEAD"), cancellable = true)
    public void disablePressedCheck(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == Minecraft.getInstance().options.keySwapOffhand) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideOffhandSlot()) {
                this.release();
                cir.setReturnValue(false);
            }
        }
    }
}