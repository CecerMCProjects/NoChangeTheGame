package com.cecer1.projects.mc.nochangethegame.utilities

import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod
import com.cecer1.projects.mc.nochangethegame.NoChangeTheGameMod.MOD_ID
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.component.CustomData

object SwordBlockingHelper {
    private val FAKE_SHIELD_MARKER = CompoundTag().apply {
        putBoolean("isFakeShield", true)
    }

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
            update(DataComponents.CUSTOM_DATA, CustomData.EMPTY) {
                it.update { tag ->
                    tag.put(MOD_ID, FAKE_SHIELD_MARKER)
                }
            }
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
        @Suppress("DEPRECATION")
        return itemStack.item == Items.SHIELD && itemStack.get(DataComponents.CUSTOM_DATA)?.unsafe?.getCompound(MOD_ID)?.getBoolean("isFakeShield") ?: false
    }
}
