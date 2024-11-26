package Day22;

public record Vec2(int x, int y) {
    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 add(int x, int y) {
        return new Vec2(this.x + x, this.y + y);
    }

    public Vec2 stepInDirectionOf(Move move) {
        Vec2 relativeVector = switch (move.direction()) {
            case UP -> new Vec2(0, -1);
            case DOWN -> new Vec2(0, 1);
            case RIGHT -> new Vec2(1, 0);
            case LEFT -> new Vec2(-1, 0);
        };
        return this.add(relativeVector);
    }
}
