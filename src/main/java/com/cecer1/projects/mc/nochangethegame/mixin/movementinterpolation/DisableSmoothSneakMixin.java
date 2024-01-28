package com.cecer1.projects.mc.nochangethegame.mixin.movementinterpolation;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables the interpolation on the sneaking animation.
 */
@Mixin(Camera.class)
public abstract class DisableSmoothSneakMixin {

    @Shadow private float eyeHeight;
    @Shadow private float eyeHeightOld;
    
    @Shadow public abstract Entity getEntity();

    @Inject(method = "tick", at = @At(value = "RETURN"))
    public void disableSneakInterpolation(CallbackInfo ci) {
        if (NoChangeTheGameMod.INSTANCE.getConfig().getMovementInterpolation().getDisableForSneaking()) {
            Entity focusedEntity = this.getEntity();
            if (focusedEntity != null) {
                this.eyeHeightOld = this.eyeHeight = focusedEntity.getEyeHeight();
            }
        }
    }
}