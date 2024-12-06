package me.wolfii.common

enum class Direction(val vec: Vec2I, val index: Int) {
    NORTH(Vec2I(0, -1), 0),
    EAST(Vec2I(1, 0), 1),
    SOUTH(Vec2I(0, 1), 2),
    WEST(Vec2I(-1, 0), 3);

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
}