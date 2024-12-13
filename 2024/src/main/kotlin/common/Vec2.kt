package me.wolfii.common

data class Vec2I(val x: Int, val y: Int) {
    operator fun plus(other: Vec2I) = Vec2I(x + other.x, y + other.y)
    operator fun minus(other: Vec2I) = Vec2I(x - other.x, y - other.y)
    operator fun times(lambda: Int) = Vec2I(x * lambda, y * lambda)
}

data class Vec2L(val x: Long, val y: Long) {
    operator fun plus(other: Vec2L) = Vec2L(x + other.x, y + other.y)
    operator fun minus(other: Vec2L) = Vec2L(x - other.x, y - other.y)
    operator fun times(lambda: Long) = Vec2L(x * lambda, y * lambda)
}

data class Vec2D(val x: Double, val y: Double) {
    operator fun plus(other: Vec2D) = Vec2D(x + other.x, y + other.y)
    operator fun minus(other: Vec2D) = Vec2D(x - other.x, y - other.y)
    operator fun times(lambda: Double) = Vec2D(x * lambda, y * lambda)
}
