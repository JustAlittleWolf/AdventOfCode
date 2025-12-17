package me.wolfii.common

enum class Direction(val vec: Vec2I) {
    NORTH(Vec2I(0, -1)),
    EAST(Vec2I(1, 0)),
    SOUTH(Vec2I(0, 1)),
    WEST(Vec2I(-1, 0));

    fun turnRight() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun turnLeft() = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun inverse() = when (this) {
        NORTH -> SOUTH
        SOUTH -> NORTH
        EAST -> WEST
        WEST -> EAST
    }
}