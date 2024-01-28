package com.cecer1.projects.mc.nochangethegame.config

import me.shedaniel.autoconfig.ConfigData
import kotlin.reflect.KProperty

class NCTGServerOverrideConfig(private val userConfig: NCTGConfig) : NCTGConfig, ConfigData {

    override val sneakHeight: NCTGConfig.SneakHeight by ConfigCategory(::SneakHeight)
    override val movementInterpolation: NCTGConfig.MovementInterpolation by ConfigCategory(::MovementInterpolation)
    override val swordBlocking: NCTGConfig.SwordBlocking by ConfigCategory(::SwordBlocking)
    override val itemCooldowns: NCTGConfig.ItemCooldowns by ConfigCategory(::ItemCooldowns)
    override val poses: NCTGConfig.Poses by ConfigCategory(::Poses)
    
    
    val overrides = mutableMapOf<String, Any>()
    operator fun set(key: String, value: Any) {
        overrides[key] = value
    }
    fun clearAllOverrides() {
        overrides.clear()
    }
    
    
    private inner class SneakHeight(key: String) : Category(key), NCTGConfig.SneakHeight {
        private val fallback = userConfig.sneakHeight
        
        override val eyeHeight by ConfigField(fallback::eyeHeight)
        override val hitboxHeight by ConfigField(fallback::hitboxHeight)
    }

    private inner class MovementInterpolation(key: String) : Category(key), NCTGConfig.MovementInterpolation {
        private val fallback = userConfig.movementInterpolation
        
        override val disableForSneaking by ConfigField(fallback::disableForSneaking)
        override val disableForHeads by ConfigField(fallback::disableForHeads)
    }

    private inner class SwordBlocking(key: String) : Category(key), NCTGConfig.SwordBlocking {
        private val fallback = userConfig.swordBlocking
        
        override val hideShields by ConfigField(fallback::hideShields)
        override val hideOffhandSlot by ConfigField(fallback::hideOffhandSlot)
        override val animateSword by ConfigField(fallback::animateSword)
        override val preventCombinedAnimation by ConfigField(fallback::preventCombinedAnimation)
        override val fakeShield by ConfigField(fallback::fakeShield)
    }

    private inner class Poses(key: String) : Category(key), NCTGConfig.Poses {
        private val fallback = userConfig.poses
        
        override val disableCrouchToFit by ConfigField(fallback::disableCrouchToFit)
        override val disableCrawlToFit by ConfigField(fallback::disableCrawlToFit)
        override val disableSwimming by ConfigField(fallback::disableSwimming)
    }

    private inner class ItemCooldowns(key: String) : Category(key), NCTGConfig.ItemCooldowns {
        private val fallback = userConfig.itemCooldowns
        
        override val disableEnderpearlCooldown by ConfigField(fallback::disableEnderpearlCooldown)
    }
    
    
    private open inner class Category(val key: String)
    private inner class ConfigCategory<T : Category>(val constructor: (String) -> T) {
        operator fun getValue(thisRef: Any, property: KProperty<*>): T = constructor.invoke(property.name) 
    }
    private inner class ConfigField<T>(val fallbackGetter: () -> T) {
        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: Category, property: KProperty<*>): T = overrides["${thisRef.key}.${property.name}"] as? T ?: fallbackGetter.invoke() 
    }
}