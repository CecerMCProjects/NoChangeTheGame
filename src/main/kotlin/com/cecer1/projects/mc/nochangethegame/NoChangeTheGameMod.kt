package com.cecer1.projects.mc.nochangethegame

import com.cecer1.projects.mc.nochangethegame.config.NCTGConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGServerOverrideConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGUserConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGVanillaConfig
import com.cecer1.projects.mc.nochangethegame.utilities.ServerBrand
import com.terraformersmc.modmenu.util.mod.fabric.FabricMod
import io.netty.buffer.Unpooled
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.util.NbtType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.nbt.ByteArrayTag
import net.minecraft.nbt.ByteTag
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.FloatTag
import net.minecraft.nbt.IntArrayTag
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongArrayTag
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.ShortTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import java.nio.file.Paths

object NoChangeTheGameMod : ClientModInitializer {
    const val MOD_ID = "nochangethegame"
    private val ANNOUNCE_PACKET_ID = ResourceLocation(MOD_ID, "announce")
    private val CONFIG_OVERRIDE_PACKET_ID = ResourceLocation(MOD_ID, "config_override")
    
    private lateinit var userConfig: NCTGUserConfig
    private lateinit var serverOverrideConfig: NCTGServerOverrideConfig
    private val vanillaConfig = NCTGVanillaConfig()

    val serverBrand = ServerBrand()
    
    val config : NCTGConfig
        get() {
            return if (serverBrand.isHypixel || !userConfig.dangerZone.disableOnNonHypixelServers) {
                if (!serverBrand.isHypixelSMP || !userConfig.dangerZone.disableOnHypixelSMP) {
                    serverOverrideConfig
                } else {
                    vanillaConfig
                }
            } else {
                vanillaConfig
            }
        }

    override fun onInitializeClient() {
        val configHolder = AutoConfig.register(NCTGUserConfig::class.java, ::Toml4jConfigSerializer)
        userConfig = configHolder.config
        serverOverrideConfig = NCTGServerOverrideConfig(userConfig)
        configHolder.save()
        
        ClientLoginConnectionEvents.DISCONNECT.register { _, _ -> resetServerState() }
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> resetServerState() }
        ClientPlayConnectionEvents.JOIN.register { _, out: PacketSender, _ ->
            val buf = FriendlyByteBuf(Unpooled.buffer()).run {
                writeUtf(FabricLoader.getInstance().getModContainer(MOD_ID).get().metadata.version.friendlyString)
            }
            out.sendPacket(ANNOUNCE_PACKET_ID, buf)
        }
        
        ClientPlayNetworking.registerGlobalReceiver(CONFIG_OVERRIDE_PACKET_ID) { _, _, buf: FriendlyByteBuf, _ ->
            val nbt: CompoundTag = buf.readNbt() ?: return@registerGlobalReceiver // Ignore invalid payloads

            for (key in nbt.allKeys) {
                val value = when (val override = nbt.get(key)) {
                    is ByteTag -> override.asByte
                    is ShortTag -> override.asShort
                    is IntTag -> override.asInt
                    is LongTag -> override.asLong
                    is FloatTag -> override.asFloat
                    is DoubleTag -> override.asDouble
                    is ByteArrayTag -> override.asByteArray
                    is StringTag -> override.asString
                    is IntArrayTag -> override.asIntArray
                    is LongArrayTag -> override.asLongArray
                    else -> continue // Ignore unsupported tag types
                }
                serverOverrideConfig.overrides[key] = value
            }
            // TODO: Indicate to the user that the server has overridden some settings
        }
    }

    private fun resetServerState() {
        serverBrand.setBrand(null)
        serverOverrideConfig.clearAllOverrides()
    }
}