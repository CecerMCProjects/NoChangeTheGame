package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking.animation;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.cecer1.projects.mc.nochangethegame.utilities.SwordBlockingHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies the sword blocking rotation to first person view.
 */
@Mixin(ItemInHandRenderer.class)
public class ApplyFirstPersonAnimationMixin {
    
    @Inject(method = "renderArmWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmAttackTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    public void applyBlockingAnimation(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getAnimateSword()) {
            if (hand == InteractionHand.MAIN_HAND &&
                    item.getItem() instanceof SwordItem &&
                    player.getOffhandItem().getItem() instanceof ShieldItem &&
                    player.isUsingItem()) {
                SwordBlockingHelper.INSTANCE.applyBlockingTransformations(poseStack);
            }
        }
    }
}
