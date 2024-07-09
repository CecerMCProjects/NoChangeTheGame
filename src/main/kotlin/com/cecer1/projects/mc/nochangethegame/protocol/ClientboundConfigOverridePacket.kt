package com.cecer1.projects.mc.nochangethegame.protocol

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import net.minecraft.resources.ResourceLocation

class ClientboundConfigOverridePacket(val stuff: CompoundTag?) : CustomPacketPayload {

    override fun type() = TYPE
    
    companion object {
        private val PACKET_ID = ResourceLocation.fromNamespaceAndPath(NoChangeTheGameMod.MOD_ID, "config_override") 
        val TYPE: Type<ClientboundConfigOverridePacket> = Type(PACKET_ID)

        val CODEC: StreamCodec<FriendlyByteBuf, ClientboundConfigOverridePacket> = object : StreamCodec<FriendlyByteBuf, ClientboundConfigOverridePacket> {
            override fun encode(buf: FriendlyByteBuf, packet: ClientboundConfigOverridePacket) {
                buf.writeNbt(packet.stuff)
            }
            override fun decode(buf: FriendlyByteBuf): ClientboundConfigOverridePacket {
                return ClientboundConfigOverridePacket(buf.readNbt())
            }
        }
    }
}