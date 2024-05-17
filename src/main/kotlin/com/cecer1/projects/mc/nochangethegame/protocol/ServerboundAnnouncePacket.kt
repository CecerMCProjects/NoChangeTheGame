package com.cecer1.projects.mc.nochangethegame.protocol

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type
import net.minecraft.resources.ResourceLocation

class ServerboundAnnouncePacket(val version: String) : CustomPacketPayload {

    override fun type() = TYPE
    
    companion object {
        private val PACKET_ID = ResourceLocation(NoChangeTheGameMod.MOD_ID, "announce") 
        val TYPE: Type<ServerboundAnnouncePacket> = Type(PACKET_ID)

        val CODEC: StreamCodec<FriendlyByteBuf, ServerboundAnnouncePacket> = object : StreamCodec<FriendlyByteBuf, ServerboundAnnouncePacket> {
            override fun encode(buf: FriendlyByteBuf, packet: ServerboundAnnouncePacket) {
                buf.writeUtf(packet.version)
            }
            override fun decode(buf: FriendlyByteBuf): ServerboundAnnouncePacket {
                return ServerboundAnnouncePacket(buf.readUtf())
            }
        }
    }
}