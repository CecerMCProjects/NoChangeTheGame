package com.cecer1.projects.mc.nochangethegame.mixin.movementinterpolation;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables the interpolation on head rotations.
 */
@Mixin(Entity.class)
public abstract class DisableSmoothHeadMixin {

    @Shadow public float yRotO;
    @Shadow private float yRot;

    @Shadow public float xRotO;
    @Shadow private float xRot;

    @Inject(method = "setYRot", at = @At(value = "RETURN"))
    public void disableYawInterpolation(CallbackInfo ci) {
        if (NoChangeTheGameMod.INSTANCE.getConfig().getMovementInterpolation().getDisableForHeads()) {
            this.yRotO = this.yRot;
        }
    }

    @Inject(method = "setXRot", at = @At(value = "RETURN"))
    public void disablePitchInterpolation(CallbackInfo ci) {
        if (NoChangeTheGameMod.INSTANCE.getConfig().getMovementInterpolation().getDisableForHeads()) {
            this.xRotO = this.xRot;
        }
    }
}