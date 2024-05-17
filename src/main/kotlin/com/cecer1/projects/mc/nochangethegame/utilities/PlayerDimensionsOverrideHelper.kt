package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import net.minecraft.world.entity.EntityAttachment
import net.minecraft.world.entity.EntityAttachments
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.player.Player

object PlayerDimensionsOverrideHelper {
    private var cachedCrouchDimensionsHeight = Float.NaN
    private lateinit var cachedCrouchDimensions: EntityDimensions

    val crouchDimensionsOverride: EntityDimensions
        get() {
            val targetHeight = NoChangeTheGameMod.config.sneakHeight.hitboxHeight
            val targetEyeHeight = NoChangeTheGameMod.config.sneakHeight.eyeHeight
            if (!cachedCrouchDimensionsHeight.equals(targetHeight)) { // This should be accurate enough for needs... right?
                cachedCrouchDimensions = EntityDimensions.scalable(0.6f, targetHeight)
                    .withEyeHeight(targetEyeHeight)
                    .withAttachments(EntityAttachments.builder()
                        .attach(EntityAttachment.VEHICLE, Player.DEFAULT_VEHICLE_ATTACHMENT))
                cachedCrouchDimensions = EntityDimensions.scalable(0.6f, targetHeight)
                cachedCrouchDimensionsHeight = targetHeight
            }
            return cachedCrouchDimensions
        }
}
