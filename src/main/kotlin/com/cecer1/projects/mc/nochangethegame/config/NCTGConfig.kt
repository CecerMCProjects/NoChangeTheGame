package com.cecer1.projects.mc.nochangethegame.config

interface NCTGConfig {
    val sneakHeight: SneakHeight
    val movementInterpolation: MovementInterpolation
    val swordBlocking: SwordBlocking
    val itemCooldowns: ItemCooldowns
    val poses: Poses
    val worldLoadingBackgrounds: WorldLoadingBackgrounds

    interface SneakHeight {
        val eyeHeight: Float
        val hitboxHeight: Float
    }

    interface MovementInterpolation {
        val disableForSneaking: Boolean
        val disableForHeads: Boolean
    }

    interface SwordBlocking {
        val hideShields: Boolean
        val hideOffhandSlot: Boolean
        val animateSword: Boolean
        val preventCombinedAnimation: Boolean
        val fakeShield: Boolean
    }

    interface ItemCooldowns {
        val disableEnderpearlCooldown: Boolean
    }

    interface Poses {
        val disableCrouchToFit: Boolean
        val disableCrawlToFit: Boolean
        val disableSwimming: Boolean
    }

    interface WorldLoadingBackgrounds {
        val disableNether: Boolean
        val disableEnd: Boolean
        val disableOther: Boolean
    }
}
