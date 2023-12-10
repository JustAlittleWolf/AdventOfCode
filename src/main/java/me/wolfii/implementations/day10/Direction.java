package me.wolfii.implementations.day10;

import me.wolfii.implementations.common.Vec2;

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0),
    NONE(0, 0),
    UNKNOWN(0, 0);
    final Vec2 vec2;

    Direction(int x, int y) {
        vec2 = new Vec2(x, y);
    }

    public static Direction of(Vec2 vector) {
        if (vector.equals(new Vec2(0, -1))) {
            return NORTH;
        } else if (vector.equals(new Vec2(0, 1))) {
            return SOUTH;
        } else if (vector.equals(new Vec2(1, 0))) {
            return EAST;
        } else if (vector.equals(new Vec2(-1, 0))) {
            return WEST;
        }
        return UNKNOWN;
    }

    public Direction turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> UNKNOWN;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            default -> UNKNOWN;
        };
    }

    public Direction inverted() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> this;
        };
    }
}
