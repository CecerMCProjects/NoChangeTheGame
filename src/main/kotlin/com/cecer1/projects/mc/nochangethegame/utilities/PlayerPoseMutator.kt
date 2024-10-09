package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.config.NCTGConfig
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.player.Player

object PlayerPoseMutator {
    init {
        // Make the player poses mutable map mutable so that we can edit
        Player.POSES = Player.POSES.toMutableMap()
    }

    fun applyConfig(config: NCTGConfig) {
        Player.POSES[Pose.CROUCHING] = Player.POSES[Pose.CROUCHING]!!.run {
            EntityDimensions(width, config.sneakHeight.hitboxHeight, config.sneakHeight.eyeHeight, attachments, fixed)
        }
    }
}