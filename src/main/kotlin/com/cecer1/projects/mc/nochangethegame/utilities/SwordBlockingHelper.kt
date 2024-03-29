package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod.MOD_ID
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.nbt.ByteTag
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

    private val FAKE_SHIELD_ITEM_STACK 
        get() = ItemStack(Items.SHIELD).apply {
            getOrCreateTagElement(MOD_ID).put("isFakeShield", ByteTag.ONE)
        }
    fun updateFakeShield() {
        Minecraft.getInstance().player?.inventory?.run {
            val mainHand = getSelected()
            val offHand = offhand[0]
            
            if (!NoChangeTheGameMod.config.swordBlocking.fakeShield) {
                if (isFakeShield(offhand[0])) {
                    offhand[0] = ItemStack.EMPTY
                }
                return
            }
            
            if (mainHand.item is SwordItem) {
                if (offHand.isEmpty) {
                    offhand[0] = FAKE_SHIELD_ITEM_STACK
                }
            } else {
                if (isFakeShield(offHand)) {
                    offhand[0] = ItemStack.EMPTY
                }
            }
        }
    }
    fun isFakeShield(itemStack: ItemStack): Boolean {
        return itemStack.item == Items.SHIELD && itemStack.getTagElement(MOD_ID)?.getBoolean("isFakeShield") ?: false
    }
}
