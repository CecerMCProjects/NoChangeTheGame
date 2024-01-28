package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides all held shields when viewed from a first-person perspective.
 * In this context, "first person" applies to the current entity the camera is attached to.
 */
@Mixin(ItemInHandRenderer.class)
public class HideFirstPersonShieldMixin {

    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    public void skipShieldRender(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (item.getItem() instanceof ShieldItem) {
            if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideShields()) {
                ci.cancel();
            }
        }
    }
}