package com.cecer1.projects.mc.nochangethegame.mixin.disableposes;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables automatically applying sneaking/crawling poses when in tight spaces.
 */
@Mixin(Player.class)
public abstract class DisablePoseToFitMixin extends LivingEntity {
    protected DisablePoseToFitMixin(EntityType<? extends LivingEntity> entityType, Level level) { super(entityType, level); }

    @Shadow
    protected abstract boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose pose);
    
    @Inject(method = "updatePlayerPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"), cancellable = true)
    protected void disablePoseToFit(CallbackInfo ci, @Local(ordinal = 0) Pose pose) {
        Pose pose2;
        if (this.isSpectator() || this.isPassenger() || this.canPlayerFitWithinBlocksAndEntitiesWhen(pose)) {
            pose2 = pose;
        } else if (this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.CROUCHING)) {
            if (!NoChangeTheGameMod.INSTANCE.getConfig().getPoses().getDisableCrouchToFit()) {
                pose2 = Pose.CROUCHING;
            } else {
                pose2 = pose;
            }
        } else {
            if (!NoChangeTheGameMod.INSTANCE.getConfig().getPoses().getDisableCrawlToFit()) {
                pose2 = Pose.SWIMMING;
            } else {
                pose2 = pose;
            }
        }
        this.setPose(pose2);
        ci.cancel();
    }
}