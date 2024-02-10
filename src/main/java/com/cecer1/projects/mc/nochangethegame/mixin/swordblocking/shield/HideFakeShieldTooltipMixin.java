package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking.shield;

import com.cecer1.projects.mc.nochangethegame.utilities.SwordBlockingHelper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hides fake shields from ever rendering
 */
@Mixin(ItemStack.class)
public abstract class HideFakeShieldTooltipMixin {

    @Inject(method = "getTooltipLines", at = @At(value = "HEAD"), cancellable = true)
    private void hideLines(CallbackInfoReturnable<List<Component>> cir) {
        if (SwordBlockingHelper.INSTANCE.isFakeShield((ItemStack) (Object) this)) {
            cir.setReturnValue(Collections.emptyList());
        }
    }
    @Inject(method = "getTooltipImage", at = @At(value = "HEAD"), cancellable = true)
    private void hideImage(CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
        if (SwordBlockingHelper.INSTANCE.isFakeShield((ItemStack) (Object) this)) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
