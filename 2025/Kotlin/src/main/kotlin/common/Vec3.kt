package me.wolfii.common

data class Vec3I(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vec3I) = Vec3I(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3I) = Vec3I(x - other.x, y - other.y, z - other.z)
    operator fun times(lambda: Int) = Vec3I(x * lambda, y * lambda, z * lambda)
}

data class Vec3L(val x: Long, val y: Long, val z: Long) {
    operator fun plus(other: Vec3L) = Vec3L(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3L) = Vec3L(x - other.x, y - other.y, z - other.z)
    operator fun times(lambda: Long) = Vec3L(x * lambda, y * lambda, z * lambda)
}

data class Vec3D(val x: Double, val y: Double, val z: Double) {
    operator fun plus(other: Vec3D) = Vec3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3D) = Vec3D(x - other.x, y - other.y, z - other.z)
    operator fun times(lambda: Double) = Vec3D(x * lambda, y * lambda, z * lambda)
}
