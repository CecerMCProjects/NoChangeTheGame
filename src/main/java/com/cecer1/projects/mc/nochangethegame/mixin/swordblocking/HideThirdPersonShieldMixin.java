package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides all held shields when viewed from a third-person perspective.
 * In this context, "third person" applies to all entities except the current entity the camera is attached to.
 */
@Mixin(ItemInHandLayer.class)
public class HideThirdPersonShieldMixin {

    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    public void skipShieldRender(LivingEntity entity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (itemStack.getItem() instanceof ShieldItem) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideShields()) {
                ci.cancel();
            }
        }
    }
}