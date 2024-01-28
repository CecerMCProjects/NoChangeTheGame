package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents sword blocking and swinging animations from combining.
 */
@Mixin(LivingEntity.class)
public abstract class HideSwingingWhileBlockingMixin {

    @Shadow public abstract boolean isUsingItem();
    @Shadow public abstract ItemStack getMainHandItem();
    @Shadow public abstract ItemStack getOffhandItem();
    
    
    @Inject(method = "getAttackAnim", at = @At(value = "RETURN"), cancellable = true)
    public void manageFakeShieldItem(CallbackInfoReturnable<Float> cir) {
        if (NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getPreventCombinedAnimation()) {
            if (    this.isUsingItem() &&
                    this.getMainHandItem().getItem() instanceof SwordItem &&
                    this.getOffhandItem().getItem() instanceof ShieldItem) {
                cir.setReturnValue(0.0f);
            }
        }
    }
}
