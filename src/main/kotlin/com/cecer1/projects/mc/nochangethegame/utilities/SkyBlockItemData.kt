package com.cecer1.projects.mc.nochangethegame.utilities

import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack

@JvmInline
value class SkyBlockItemData private constructor(private val data: CompoundTag) {
    
    val id get() = data.getStringOrNull("id")
    val abilityScrolls get() = AbilityScrolls.of(data.getListOrNull("ability_scroll", Tag.TAG_LIST))
    
    companion object {
        fun of(itemStack: ItemStack): SkyBlockItemData? {
            val data = itemStack.components.get(DataComponents.CUSTOM_DATA)?.tag ?: return null
            val extraAttributes = data.getCompoundOrNull("ExtraAttributes") ?: return null
            
            val skyBlockItemData = SkyBlockItemData(extraAttributes)
            if (skyBlockItemData.id == null) {
                return null
            }
            return skyBlockItemData
        }
    }
    
    @JvmInline
    value class AbilityScrolls private constructor(private val data: ListTag) : Iterable<String> {
        operator fun get(index: Int): String = data.getString(index)
        override fun iterator(): Iterator<String> {
            return object : Iterator<String> {
                private var index = 0
                override fun hasNext(): Boolean = index < data.size
                override fun next() = data.getString(index++)
            }
        }
        operator fun contains(key: String): Boolean {
            return this.any { it == key }
        }

        companion object {
            fun of(data: ListTag?): AbilityScrolls = AbilityScrolls(data ?: ListTag())
        }
    } 
}