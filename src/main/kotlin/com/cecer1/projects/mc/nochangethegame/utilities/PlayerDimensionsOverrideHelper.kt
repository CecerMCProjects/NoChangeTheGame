package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import net.minecraft.world.entity.EntityDimensions

object PlayerDimensionsOverrideHelper {
    private var cachedCrouchDimensionsHeight = Float.NaN
    private lateinit var cachedCrouchDimensions: EntityDimensions

    val crouchDimensionsOverride: EntityDimensions
        get() {
            val targetHeight = NoChangeTheGameMod.config.sneakHeight.hitboxHeight
            if (!cachedCrouchDimensionsHeight.equals(targetHeight)) { // This should be accurate enough for needs... right?
                cachedCrouchDimensions = EntityDimensions.scalable(0.6f, targetHeight)
                cachedCrouchDimensionsHeight = targetHeight
            }
            return cachedCrouchDimensions
        }
}
