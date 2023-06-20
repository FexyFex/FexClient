package me.fexclient.datatype

data class Vec3i(var x: Int, var y: Int, var z: Int) {
    operator fun plus(other: Vec3i): Vec3i {
        return Vec3i(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        )
    }
}
