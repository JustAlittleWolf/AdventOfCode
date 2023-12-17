package me.wolfii.implementations.common;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public record Vec2(int x, int y) {
    public static List<Vec2> NEIGHBOURS_NEXTTO = List.of(new Vec2(0, 1), new Vec2(0, -1), new Vec2(1, 0), new Vec2(-1, 0));
    public static List<Vec2> NEIGHBOURS_DIAGONAL = List.of(
            new Vec2(0, 1), new Vec2(0, -1), new Vec2(1, 0), new Vec2(-1, 0),
            new Vec2(1, 1), new Vec2(-1, 1), new Vec2(1, -1), new Vec2(-1, -1)
    );

    public static Vec2 ZERO = new Vec2(0, 0);

    public boolean isNextTo(Vec2 other) {
        int xDiff = Math.abs(x - other.x);
        int yDiff = Math.abs(y - other.y);
        return (xDiff <= 1 && yDiff == 0) || (yDiff <= 1 && xDiff == 0);
    }

    public Vec2 plus(Vec2 other) {
        return new Vec2(x + other.x(), y + other.y());
    }

    public Vec2 plus(int x, int y) {
        return new Vec2(this.x + x, this.y + y);
    }

    public Vec2 multiply(int lambda) {
        return new Vec2(this.x * lambda, this.y * lambda);
    }

    public Vec2 abs() {
        return new Vec2(Math.abs(x), Math.abs(y));
    }

    public Vec2 inverted() {
        return new Vec2(-x, -y);
    }

    public List<Vec2> neighbours() {
        return neighbours(false);
    }

    public List<Vec2> neighbours(boolean diagonal) {
        List<Vec2> neighboursRelative = diagonal ? NEIGHBOURS_DIAGONAL : NEIGHBOURS_NEXTTO;
        List<Vec2> neighbours = new ArrayList<>();
        for (Vec2 neighbourRelative : neighboursRelative) {
            neighbours.add(this.plus(neighbourRelative));
        }
        return neighbours;
    }
}