package com.cecer1.projects.mc.nochangethegame.mixin.disableposes;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Disables the ability to enable swimming.
 */
@Mixin(Entity.class)
public abstract class DisableSwimmingMixin {

    @Inject(method = "setSwimming", at = @At(value = "HEAD"))
    protected void disableSwimming(CallbackInfo ci, @Local(argsOnly = true) LocalBooleanRef state) {
        if (!state.get()) {
            return; // No need to do anything as it is already false
        }

        if (NoChangeTheGameMod.INSTANCE.getConfig().getPoses().getDisableSwimming()) {
            state.set(false);
        }
    }

    @Inject(method = "isVisuallySwimming", at = @At(value = "HEAD"), cancellable = true)
    protected void disableSwimming(CallbackInfoReturnable<Boolean> cir) {
        //noinspection ConstantValue
        if ((Object) this == Minecraft.getInstance().player) {
            // For safety, we don't fake visuals of the local player
            return;
        }
        
        if (NoChangeTheGameMod.INSTANCE.getConfig().getPoses().getDisableSwimming()) {
            cir.setReturnValue(false);
        }
    }
}