package Day22;

public enum Tile {
    WALL,
    OPEN,
    VOID;

    public static Tile of(char character) {
        return switch (character) {
            case ' ' -> VOID;
            case '#' -> WALL;
            case '.' -> OPEN;
            default -> {
                System.out.println("ERROR IN TILE");
                yield WALL;
            }
        };
    }

    @Override
    public String toString() {
        return switch (this) {

            case WALL -> "#";
            case OPEN -> ".";
            case VOID -> " ";
        };
    }
}
