package com.cecer1.projects.mc.nochangethegame.mixin.packethandling;

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Disables the cooldown on throwing enderpearls 
 */
@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class CommonPacketHandlerMixin {
    
    @WrapOperation(method = "handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl;serverBrand:Ljava/lang/String;", opcode = Opcodes.PUTFIELD))
    private void interceptServerBrandSet(ClientCommonPacketListenerImpl instance, String brand, Operation<Void> original) {
        NoChangeTheGameMod.INSTANCE.getServerBrand().setBrand(brand);
        original.call(instance, brand);
    }
}