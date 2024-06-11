package com.cecer1.projects.mc.nochangethegame

import com.cecer1.projects.mc.nochangethegame.config.NCTGConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGServerOverrideConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGUserConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGVanillaConfig
import com.cecer1.projects.mc.nochangethegame.protocol.ClientboundConfigOverridePacket
import com.cecer1.projects.mc.nochangethegame.protocol.ClientboundKillSwitchPacket
import com.cecer1.projects.mc.nochangethegame.protocol.ServerboundAnnouncePacket
import com.cecer1.projects.mc.nochangethegame.utilities.ServerBrand
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.nbt.ByteArrayTag
import net.minecraft.nbt.ByteTag
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.FloatTag
import net.minecraft.nbt.IntArrayTag
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.LongArrayTag
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.ShortTag
import net.minecraft.nbt.StringTag

object NoChangeTheGameMod : ClientModInitializer {
    const val MOD_ID = "nochangethegame"
    
    private lateinit var userConfig: NCTGUserConfig
    private lateinit var serverOverrideConfig: NCTGServerOverrideConfig
    private val vanillaConfig = NCTGVanillaConfig()
    private var killSwitchActive = false
    val serverBrand = ServerBrand()
    
    val config : NCTGConfig
        get() {
            if (killSwitchActive) {
                return vanillaConfig
            }

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
        ClientConfigurationConnectionEvents.DISCONNECT.register { _, _ -> resetServerState() }
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> resetServerState() }
        
        PayloadTypeRegistry.playC2S().register(ServerboundAnnouncePacket.TYPE, ServerboundAnnouncePacket.CODEC)
        PayloadTypeRegistry.playS2C().register(ClientboundConfigOverridePacket.TYPE, ClientboundConfigOverridePacket.CODEC)
        PayloadTypeRegistry.playS2C().register(ClientboundKillSwitchPacket.TYPE, ClientboundKillSwitchPacket.CODEC)


        ClientPlayConnectionEvents.JOIN.register { _, out: PacketSender, _ ->
            val version = FabricLoader.getInstance().getModContainer(MOD_ID).get().metadata.version.friendlyString
            out.sendPacket(ServerboundAnnouncePacket(version))
        }
        
        ClientPlayNetworking.registerGlobalReceiver(ClientboundConfigOverridePacket.TYPE) { packet, _ ->
            val nbt: CompoundTag = packet.stuff ?: return@registerGlobalReceiver // Ignore invalid payloads

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

        ClientPlayNetworking.registerGlobalReceiver(ClientboundKillSwitchPacket.TYPE) { packet, _ ->
            killSwitchActive = packet.active
        }
    }

    private fun resetServerState() {
        killSwitchActive = false
        serverBrand.setBrand(null)
        serverOverrideConfig.clearAllOverrides()
    }
}