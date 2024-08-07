package com.cecer1.projects.mc.nochangethegame.config

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod.MOD_ID
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip

@Config(name = MOD_ID)
class NCTGUserConfig : NCTGConfig, ConfigData {

    @CollapsibleObject
    override val sneakHeight = SneakHeight()

    @CollapsibleObject
    override val movementInterpolation = MovementInterpolation()

    @CollapsibleObject
    override val swordBlocking = SwordBlocking()

    @CollapsibleObject
    override val itemCooldowns = ItemCooldowns()

    @CollapsibleObject
    override val poses = Poses()

    @CollapsibleObject
    override val worldLoadingBackgrounds = WorldLoadingBackgrounds()

    @CollapsibleObject
    val dangerZone = DangerZone()

    class SneakHeight : NCTGConfig.SneakHeight {
        @Tooltip(count = 10)
        override var eyeHeight = 1.54f

        @Tooltip(count = 10)
        override var hitboxHeight = 1.8f
    }

    class MovementInterpolation : NCTGConfig.MovementInterpolation {
        @Tooltip(count = 2)
        override var disableForSneaking = true

        @Tooltip(count = 2)
        override var disableForHeads = true
    }

    class SwordBlocking : NCTGConfig.SwordBlocking {
        @Tooltip(count = 2)
        override var hideShields = true

        @Tooltip(count = 2)
        override var hideOffhandSlot = true

        @Tooltip(count = 2)
        override var animateSword = true
        
        @Excluded
//        @Tooltip(count = 2)
        override var preventCombinedAnimation = true

        @Tooltip(count = 2)
        override var fakeShield = true
    }

    class Poses : NCTGConfig.Poses {
        @Tooltip(count = 2)
        override var disableCrouchToFit = true

        @Tooltip(count = 2)
        override var disableCrawlToFit = true

        @Tooltip
        override var disableSwimming = true
    }

    class ItemCooldowns : NCTGConfig.ItemCooldowns {
        @Tooltip
        override var disableEnderpearlCooldown = true
    }

    class WorldLoadingBackgrounds : NCTGConfig.WorldLoadingBackgrounds {
        @Tooltip(count = 4)
        override val disableNether = false

        @Tooltip(count = 4)
        override val disableEnd = false

        @Tooltip(count = 4)
        @Excluded
        override val disableOther = false
    }

    class DangerZone {
        @Tooltip(count = 7)
        var disableOnNonHypixelServers = true
        @Tooltip(count = 7)
        var disableOnHypixelSMP = true
    }
}