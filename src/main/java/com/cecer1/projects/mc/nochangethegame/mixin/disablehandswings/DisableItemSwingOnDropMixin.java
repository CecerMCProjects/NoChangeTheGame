package com.cecer1.projects.mc.nochangethegame.mixin.disablehandswings;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Disables the swinging of the arm when dropping items 
 */
@Mixin(Minecraft.class)
public abstract class DisableItemSwingOnDropMixin {

    @Shadow @Final public Options options;

    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;consumeClick()Z"))
    private boolean detectItemDrop(KeyMapping instance, Operation<Boolean> original, @Share("isDropping") LocalBooleanRef isDropping) {
        boolean keyPressed = original.call(instance);
        if (instance != this.options.keyDrop) {
            isDropping.set(keyPressed);
        }
        return keyPressed;
    }


    @WrapWithCondition(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private boolean detectItemDrop(LocalPlayer instance, InteractionHand interactionHand, @Share("isDropping") LocalBooleanRef isDropping) {
        // The isDropping value will always be true in vanilla (at least in 1.20.6) but this adds some sanity checking and potential future proofing.
        return !isDropping.get();
    }
}