package com.cecer1.projects.mc.nochangethegame.mixin.swordblocking.shield;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Prevents the offhand slot from being rendered.
 */
@Mixin(Gui.class)
public abstract class HideOffhandHotbarSlotMixin {
    
    @Shadow @Final private static ResourceLocation HOTBAR_OFFHAND_LEFT_SPRITE;
    @Shadow @Final private static ResourceLocation HOTBAR_OFFHAND_RIGHT_SPRITE;

    @WrapWithCondition(method = "renderItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private boolean hideBackground(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int width, int height) {
        if (!NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideOffhandSlot()) {
            // Don't hide
            return true;
        }

        return resourceLocation != HOTBAR_OFFHAND_LEFT_SPRITE && resourceLocation != HOTBAR_OFFHAND_RIGHT_SPRITE;
    }

    @WrapWithCondition(method = "renderItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V"))
    private boolean hideForeground(Gui instance, GuiGraphics guiGraphics, int i, int j, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int index) {
        if (!NoChangeTheGameMod.INSTANCE.getConfig().getSwordBlocking().getHideOffhandSlot()) {
            // Don't hide (disabled by config)
            return true;
        }
        if (index != 10) {
            // Don't hide (not offhand slot)
            return true;
        }

        // Only hide shields
        return !itemStack.is(Items.SHIELD);
    }
}