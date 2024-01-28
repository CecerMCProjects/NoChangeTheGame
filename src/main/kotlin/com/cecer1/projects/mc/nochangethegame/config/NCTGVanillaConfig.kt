package com.cecer1.projects.mc.nochangethegame.config

class NCTGVanillaConfig : NCTGConfig {
    override val sneakHeight: NCTGConfig.SneakHeight = SneakHeight()
    override val movementInterpolation: NCTGConfig.MovementInterpolation = MovementInterpolation()
    override val swordBlocking: NCTGConfig.SwordBlocking = SwordBlocking()
    override val itemCooldowns: NCTGConfig.ItemCooldowns = ItemCooldowns()
    override val poses: NCTGConfig.Poses = Poses()


    private class SneakHeight : NCTGConfig.SneakHeight {
        override val eyeHeight = 1.27f
        override val hitboxHeight  = 1.5f
    }
    private class MovementInterpolation : NCTGConfig.MovementInterpolation {
        override val disableForSneaking = false
        override val disableForHeads = false
    }
    private class SwordBlocking : NCTGConfig.SwordBlocking {
        override val hideShields = false
        override val hideOffhandSlot = false
        override val animateSword = false
        override val preventCombinedAnimation = false
        override val fakeShield = false
    }
    private class ItemCooldowns : NCTGConfig.ItemCooldowns {
        override val disableEnderpearlCooldown = false
    }
    private class Poses : NCTGConfig.Poses {
        override val disableCrouchToFit = false
        override val disableCrawlToFit = false
        override val disableSwimming = false
    }
}
