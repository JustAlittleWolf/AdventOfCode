package Day22;

public enum Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN;

    public Direction getRotatedRight() {
        return switch(this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }

    public Direction getRotatedLeft() {
        return switch(this) {
            case UP -> LEFT;
            case LEFT -> DOWN;
            case DOWN -> RIGHT;
            case RIGHT -> UP;
        };
    }

    public Direction getRotated(char rotator) {
        return switch(rotator) {
            case 'L' -> this.getRotatedLeft();
            case 'R' -> this.getRotatedRight();
            default -> {System.out.println("ERROR WHEN ROTATING"); yield RIGHT;}
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case RIGHT -> ">";
            case LEFT -> "<";
            case UP -> "^";
            case DOWN -> "v";
        };
    }
}