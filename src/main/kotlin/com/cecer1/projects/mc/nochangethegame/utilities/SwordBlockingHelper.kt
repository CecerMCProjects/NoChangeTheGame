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
    private val ROTATIONS = arrayOf(
        getDegreesQuaternion(1.0, 0.0, 0.0, -94f),
        getDegreesQuaternion(0.0, 1.0, 0.0, -128f),
        getDegreesQuaternion(0.0, 0.0, 1.0, -68f)
    )
    private val FAKE_SHIELD_MARKER = CompoundTag().apply {
        putBoolean("isFakeShield", true)
    }
    private val FAKE_SHIELD_ITEM_STACK = ItemStack(Items.SHIELD).apply {
        update(DataComponents.CUSTOM_DATA, CustomData.EMPTY) {
            it.update { tag ->
                tag.put(MOD_ID, FAKE_SHIELD_MARKER)
            }
        }
    }
    private val UNBLOCKABLE_SKYBLOCK_SWORDS = setOf(
        "ASPECT_OF_THE_DRAGON",
        "ASPECT_OF_THE_END",
        "ASPECT_OF_THE_JERRY",
        "ASPECT_OF_THE_JERRY_SIGNATURE",
        "ATOMSPLIT_KATANA",
        "BLADE_OF_DRAGONFIRE",
        "BONE_REAVER",
        "BURSTFIRE_DAGGER",
        "BURSTMAW_DAGGER",
        "CRYPT_DREADLORD_SWORD",
        "EARTH_SHARD",
        "END_STONE_SWORD",
        "FELTHORN_REAPER",
        "FIREDUST_DAGGER",
        "FLORID_ZOMBIE_SWORD",
        "GIANTS_EYE_SWORD",
        "GIANTS_SWORD",
        "GOLEM_SWORD",
        "HEARTFIRE_DAGGER",
        "HEARTMAW_DAGGER",
        "LEAPING_SWORD",
        "LIVID_DAGGER",
        "MAWDUST_DAGGER",
        "NECROMANCER_SWORD",
        "ORNATE_ZOMBIE_SWORD",
        "PIGMAN_SWORD",
        "ROGUE_SWORD",
        "SHADOW_FURY",
        "SILENT_DEATH",
        "SILK_EDGE_SWORD",
        "SPIRIT_SWORD",
        "STARRED_SHADOW_FURY",
        "STARRED_YETI_SWORD",
        "SWORD_OF_BAD_HEALTH",
        "VORPAL_KATANA",
        "WITHER_CLOAK",
        "YETI_SWORD",
        "ZOMBIE_SWORD",
    )
    
    fun applyBlockingTransformations(poses: PoseStack) {
        poses.translate(-0.125f, 0.098f, 0.0f)
        poses.mulPose(ROTATIONS[0])
        poses.mulPose(ROTATIONS[1])
        poses.mulPose(ROTATIONS[2])
    }
    
    fun isFakeShield(itemStack: ItemStack): Boolean {
        return itemStack.item == Items.SHIELD && itemStack.get(DataComponents.CUSTOM_DATA)?.tag?.getCompound(MOD_ID)?.getBoolean("isFakeShield") ?: false
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
            
            if (isBlockableSword(mainHand)) {
                if (offHand.isEmpty) {
                    offhand[0] = FAKE_SHIELD_ITEM_STACK.copy()
                }
            } else {
                if (isFakeShield(offHand)) {
                    offhand[0] = ItemStack.EMPTY
                }
            }
        }
    }

    private fun isBlockableSword(mainHand: ItemStack): Boolean {
        // Allow servers to specifically mark items as blockable or not
        val itemOverride = mainHand.components.get(DataComponents.CUSTOM_DATA)?.tag?.getBoolean("nochangethegame:blockable")
        if (itemOverride != null) {
            return itemOverride
        }
        
        if (mainHand.item !is SwordItem) {
            return false
        }
        
        // Check for Hypixel SkyBlock specific unblockable items
        val sbItem = SkyBlockItemData.of(mainHand) ?: return true
        
        // Simple ID checks
        if (UNBLOCKABLE_SKYBLOCK_SWORDS.contains(sbItem.id)) {
            return false
        }
        
        // Check for scrolls
        for (scroll in sbItem.abilityScrolls) {
            when (scroll) {
                "WITHER_SHIELD_SCROLL",
                "IMPLOSION_SCROLL",
                "SHADOW_WARP_SCROLL" -> return false
            }
        }
        
        return true
    }
}

