package com.cecer1.projects.mc.nochangethegame

import com.cecer1.projects.mc.nochangethegame.config.NCTGConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGVanillaConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGServerOverrideConfig
import com.cecer1.projects.mc.nochangethegame.config.NCTGUserConfig
import com.cecer1.projects.mc.nochangethegame.utilities.ServerBrand
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents

object NoChangeTheGameMod : ClientModInitializer {
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

        userConfig.run {
            if (configResetVersion < 1) {
                configHolder.resetToDefault()
                configResetVersion = 1
            }
        }
        configHolder.save()
        

        ClientLoginConnectionEvents.DISCONNECT.register { _, _ -> serverBrand.setBrand(null) }
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> serverBrand.setBrand(null) }
    }
}