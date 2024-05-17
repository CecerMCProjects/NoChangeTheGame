package com.cecer1.projects.mc.nochangethegame.mixin.disableitemcooldowns;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Disables the cooldown on throwing items such as enderpearls 
 */
@Mixin(EnderpearlItem.class)
public abstract class DisableEnderpearlCooldownMixin {

    @WrapWithCondition(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private boolean preventCooldown(ItemCooldowns instance, Item item, int duration) {
        return !NoChangeTheGameMod.INSTANCE.getConfig().getItemCooldowns().getDisableEnderpearlCooldown();
    }
}