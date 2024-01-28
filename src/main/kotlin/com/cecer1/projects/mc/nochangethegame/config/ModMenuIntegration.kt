package com.cecer1.projects.mc.nochangethegame.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.shedaniel.autoconfig.AutoConfig

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory {
        AutoConfig.getConfigScreen(NCTGUserConfig::class.java, it).get()
    }
}
