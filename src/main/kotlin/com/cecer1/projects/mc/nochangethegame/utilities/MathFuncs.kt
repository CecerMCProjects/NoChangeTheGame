package com.cecer1.projects.mc.nochangethegame.utilities

import org.joml.Quaternionf
import kotlin.math.cos
import kotlin.math.sin

fun getDegreesQuaternion(x: Double, y: Double, z: Double, angle: Float): Quaternionf {
    val radAngle = angle * (Math.PI / 180.0).toFloat()
    val f = sin((radAngle / 2.0f).toDouble())
    return Quaternionf(x * f, y * f, z * f, cos((radAngle / 2.0f).toDouble()))
}
