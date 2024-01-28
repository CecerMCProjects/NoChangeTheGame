package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents the offhand arm from raising up when blocking due to the invisible shield usage in third person view and for other players.  
 */
@Mixin(PlayerRenderer.class)
public class ApplyThirdPersonPoseMixin {

    @Inject(method = "getArmPose", at = @At(value = "HEAD"), cancellable = true)
    private static void applyArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (hand == InteractionHand.OFF_HAND) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getAnimateSword()) {
                // Offhand is always empty in Hypixel mode.
                cir.setReturnValue(HumanoidModel.ArmPose.EMPTY);
            }
        }
    }
}
