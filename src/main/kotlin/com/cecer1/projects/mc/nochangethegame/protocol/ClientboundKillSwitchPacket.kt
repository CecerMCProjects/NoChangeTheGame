package com.cecer1.projects.mc.nochangethegame.protocol

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import net.minecraft.resources.ResourceLocation

class ClientboundKillSwitchPacket(val active: Boolean) : CustomPacketPayload {

    override fun type() = TYPE
    
    companion object {
        private val PACKET_ID = ResourceLocation(NoChangeTheGameMod.MOD_ID, "kill_switch") 
        val TYPE: Type<ClientboundKillSwitchPacket> = Type(PACKET_ID)

        val CODEC: StreamCodec<FriendlyByteBuf, ClientboundKillSwitchPacket> = object : StreamCodec<FriendlyByteBuf, ClientboundKillSwitchPacket> {
            override fun encode(buf: FriendlyByteBuf, packet: ClientboundKillSwitchPacket) {
                buf.writeBoolean(packet.active)
            }
            override fun decode(buf: FriendlyByteBuf): ClientboundKillSwitchPacket {
                return ClientboundKillSwitchPacket(buf.readBoolean())
            }
        }
    }
}