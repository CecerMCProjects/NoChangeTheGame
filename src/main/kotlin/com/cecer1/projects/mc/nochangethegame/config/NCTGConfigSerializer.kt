package com.cecer1.projects.mc.nochangethegame.config

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import me.shedaniel.cloth.clothconfig.shadowed.com.moandjiezana.toml.TomlWriter

class NCTGConfigSerializer<T : ConfigData>(definition: Config, configClass: Class<T>) : Toml4jConfigSerializer<T>(definition, configClass, TomlWriter()) {
    
    override fun serialize(config: T?) {
        super.serialize(config)
        NoChangeTheGameMod.applyConfigChanges()
    }
}