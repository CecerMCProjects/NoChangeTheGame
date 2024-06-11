package com.cecer1.projects.mc.nochangethegame.mixin.disablehandswings;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Disables the swinging of the arm when using items 
 */
@Mixin(Minecraft.class)
public abstract class DisableItemSwingOnUseLocalMixin {

    @ModifyExpressionValue(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionResult;shouldSwing()Z"))
    private boolean shouldSwing(boolean original) {
        return original && !NoChangeTheGameMod.INSTANCE.getConfig().getArmSwings().getDisableOnUse();
    }
}