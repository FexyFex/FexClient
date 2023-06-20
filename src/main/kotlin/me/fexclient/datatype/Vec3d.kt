package me.fexclient.datatype

import net.minecraft.src.helpers.MathHelper.floor_double


data class Vec3d(var x: Double, var y: Double, var z: Double) {
    operator fun plus(other: Vec3d): Vec3d {
        return Vec3d(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        )
    }

    fun floorToVec3i() = Vec3i(floor_double(x), floor_double(y), floor_double(z))
}
