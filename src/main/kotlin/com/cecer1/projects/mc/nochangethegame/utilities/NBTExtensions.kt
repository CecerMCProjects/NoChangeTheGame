package com.cecer1.projects.mc.nochangethegame.utilities

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag

fun CompoundTag.getCompoundOrNull(key: String): CompoundTag? {
    if (!contains(key)) {
        return null
    }
    return getCompound(key)
}
fun CompoundTag.getListOrNull(key: String, typeId: Byte): ListTag? {
    if (!contains(key)) {
        return null
    }
    return getList(key, typeId.toInt())
}
fun CompoundTag.getStringOrNull(key: String): String? {
    if (!contains(key)) {
        return null
    }
    return getString(key)
}