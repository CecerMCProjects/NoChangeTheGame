package com.cecer1.projects.mc.nochangethegame.mixin.sneakheight;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Changes the visual height of sneaking. 
 */
@Mixin(Player.class)
public abstract class ChangeSneakEyeHeightMixin {
    
    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    public void overrideEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> ci) {
        if (pose == Pose.CROUCHING) {
            ci.setReturnValue(NoChangeTheGameMod.INSTANCE.getConfig().getSneakHeight().getEyeHeight());
        }
    }
}