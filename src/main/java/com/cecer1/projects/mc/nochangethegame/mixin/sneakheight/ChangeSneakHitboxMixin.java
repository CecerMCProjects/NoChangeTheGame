package com.cecer1.projects.mc.nochangethegame.mixin.sneakheight;

import com.cecer1.projects.mc.nochangethegame.utilities.PlayerDimensionsOverrideHelper;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Changes the physical hitbox height of sneaking players.
 */
@Mixin(Player.class)
public abstract class ChangeSneakHitboxMixin {
    
    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    public void overrideHitboxHeight(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (pose == Pose.CROUCHING) {
            EntityDimensions crouchDimensionsOverride = PlayerDimensionsOverrideHelper.INSTANCE.getCrouchDimensionsOverride();
            cir.setReturnValue(crouchDimensionsOverride);
        }
    }
}