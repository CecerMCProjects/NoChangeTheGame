package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies the sword blocking rotation to third person view and to other players 
 * 
 * @implNote We change the rotation of opposite arms because it's technically the shield that is in use, not the sword.
 */
@Mixin(HumanoidModel.class)
public abstract class ApplyThirdPersonAnimationMixin<T extends LivingEntity> extends AgeableListModel<T> {

    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart rightArm;

    @Inject(method = "poseLeftArm", at = @At(value = "RETURN"))
    private void positionLeftArm(T entity, CallbackInfo ci) {
        if (entity instanceof Player player) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getAnimateSword()) {
                if (player.getMainArm() == HumanoidArm.RIGHT &&
                        player.getMainHandItem().getItem() instanceof SwordItem &&
                        player.getOffhandItem().getItem() instanceof ShieldItem &&
                        player.isUsingItem()) {
                    this.rightArm.yRot = -0.52f;
                    this.rightArm.xRot = -0.95f;
                }
            }
        }
    }

    @Inject(method = "poseRightArm", at = @At(value = "RETURN"))
    private void positionRightArm(T entity, CallbackInfo ci) {
        if (entity instanceof Player player) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getAnimateSword()) {
                if (player.getMainArm() == HumanoidArm.LEFT &&
                        player.getMainHandItem().getItem() instanceof SwordItem &&
                        player.getOffhandItem().getItem() instanceof ShieldItem &&
                        player.isUsingItem()) {
                    this.leftArm.yRot = 0.52f;
                    this.leftArm.xRot = -0.95f;
                }
            }
        }
    }
}
