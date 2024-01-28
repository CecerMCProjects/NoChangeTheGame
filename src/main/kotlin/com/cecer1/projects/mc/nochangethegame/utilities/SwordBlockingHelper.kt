package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.ShieldItem
import net.minecraft.world.item.SwordItem

object SwordBlockingHelper {
    private val ROTATIONS = arrayOf(
        getDegreesQuaternion(1.0, 0.0, 0.0, -94f),
        getDegreesQuaternion(0.0, 1.0, 0.0, -128f),
        getDegreesQuaternion(0.0, 0.0, 1.0, -68f)
    )

    fun applyBlockingTransformations(poses: PoseStack) {
        poses.translate(-0.125f, 0.098f, 0.0f)
        poses.mulPose(ROTATIONS[0])
        poses.mulPose(ROTATIONS[1])
        poses.mulPose(ROTATIONS[2])
    }

    fun updateFakeShield() {
        if (!NoChangeTheGameMod.config.swordBlocking.fakeShield) {
            return
        }
        
        Minecraft.getInstance().player?.inventory?.run {
            val mainHand = getSelected()
            val offHand = offhand[0]

            if (mainHand.item is SwordItem) {
                if (offHand.isEmpty) {
                    offhand[0] = ItemStack(Items.SHIELD, 2)
                }
            } else {
                if (offHand.item is ShieldItem) {
                    offhand[0] = ItemStack.EMPTY
                }
            }
        }
    }
}
