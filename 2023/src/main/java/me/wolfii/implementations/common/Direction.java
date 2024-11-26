package me.wolfii.implementations.common;

public enum Direction {
    NORTH(0, -1, 0b1),
    SOUTH(0, 1, 0b10),
    EAST(1, 0, 0b100),
    WEST(-1, 0, 0b1000),
    NONE(0, 0, 0b10000),
    UNKNOWN(0, 0, 0b100000);
    final Vec2 vec2;
    final int bitmask;

    Direction(int x, int y, int bitmask) {
        vec2 = new Vec2(x, y);
        this.bitmask = bitmask;
    }

    public Vec2 vec2() {
        return this.vec2;
    }

    public int bitmask() {
        return this.bitmask;
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

    public Direction mirrorFirstMedian() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case EAST -> NORTH;
            case WEST -> SOUTH;
            default -> this;
        };
    }

    public Direction mirrorSecondMedian() {
        return switch (this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
            default -> this;
        };
    }

    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }

    public boolean isHorizontal() {
        return this == EAST || this == WEST;
    }
}
