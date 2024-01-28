package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.utilities.SwordBlockingHelper;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keeps a fake shield in the offhand when holding a sword in the main hand.
 * This essentially removes the latency delay of Hypixel's blocking compatibility.
 */
@Mixin(Minecraft.class)
public class UpdateFakeShieldMixin {

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void manageFakeShieldItem(CallbackInfo ci) {
        SwordBlockingHelper.INSTANCE.updateFakeShield();
    }
}