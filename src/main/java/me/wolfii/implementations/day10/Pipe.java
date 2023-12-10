package me.wolfii.implementations.day10;

import me.wolfii.implementations.common.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Pipe {
    private final Direction first;
    private final Direction second;

    public Pipe(char letter) {
        switch (letter) {
            case '|':
                first = Direction.NORTH;
                second = Direction.SOUTH;
                break;
            case '-':
                first = Direction.EAST;
                second = Direction.WEST;
                break;
            case 'L':
                first = Direction.NORTH;
                second = Direction.EAST;
                break;
            case 'J':
                first = Direction.NORTH;
                second = Direction.WEST;
                break;
            case '7':
                first = Direction.SOUTH;
                second = Direction.WEST;
                break;
            case 'F':
                first = Direction.SOUTH;
                second = Direction.EAST;
                break;
            case 'S':
                first = Direction.UNKNOWN;
                second = Direction.UNKNOWN;
                break;
            default:
                first = Direction.NONE;
                second = Direction.NONE;
        }
    }

    public boolean canEnterFrom(Direction direction) {
        return first == direction || second == direction;
    }

    public Direction getExit(Direction entry) {
        if (entry == first) return second;
        if (entry == second) return first;
        return null;
    }

    public boolean isStraight() {
        return (canEnterFrom(Direction.SOUTH) && canEnterFrom(Direction.NORTH)) || (canEnterFrom(Direction.WEST) && canEnterFrom(Direction.EAST));
    }

    public boolean isRightTurn(Direction entry) {
        Direction exit = getExit(entry);
        return switch (entry) {
            case NORTH -> exit == Direction.WEST;
            case EAST -> exit == Direction.NORTH;
            case SOUTH -> exit == Direction.EAST;
            case WEST -> exit == Direction.SOUTH;
            default -> false;
        };
    }

    public List<Vec2> disconnectedNeighbours() {
        List<Vec2> disconnectedTilesRelative = new ArrayList<>(Vec2.NEIGHBOURS_NEXTTO);
        disconnectedTilesRelative.remove(first.vec2);
        disconnectedTilesRelative.remove(second.vec2);

        return disconnectedTilesRelative;
    }
}
