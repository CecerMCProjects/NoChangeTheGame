package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking.shield;

import com.cecer1.projects.mc.nochangethegame.utilities.SwordBlockingHelper;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides fake shields from ever rendering
 */
@Mixin(GuiGraphics.class)
public abstract class HideFakeShieldItemMixin {

    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At(value = "HEAD"))
    private void hideItem(CallbackInfo ci, @Local(argsOnly = true) LocalRef<ItemStack> itemStack) {
        if (SwordBlockingHelper.INSTANCE.isFakeShield(itemStack.get())) {
            itemStack.set(ItemStack.EMPTY);
        }
    }
}
