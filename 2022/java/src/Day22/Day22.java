package Day22;

public class Day22 {
    private static Area area;
    private static Path path;

    public static void solve(String input) {
        String[] parts = input.split("(\r?\n\r?\n)");
        area = new Area(parts[0]);
        path = new Path(parts[1].trim());
        System.out.println(calcPathValue(area, path));
        area = new Area(parts[0]);
        area.setWrapAroundCube(true);
        path = new Path(parts[1].trim());
        System.out.println(calcPathValue(area, path));
    }

    private static int calcPathValue(Area area, Path path) {
        Vec2 currentPosition = area.getStartPosition();
        Direction lastDirection = Direction.RIGHT;
        while (path.hasElements()) {
            Move curMove = path.getNext();
            lastDirection = curMove.direction();
            while (curMove.distance() > 0) {
                Tile nextTile = area.getNextTile(currentPosition, curMove);
                if (nextTile == Tile.WALL) {
                    curMove = curMove.stop();
                    continue;
                }
                currentPosition = area.step(currentPosition, curMove);
                area.movemap.put(currentPosition, curMove.direction());
                curMove = curMove.decreaseDistance();
            }
        }
        int directionValue = switch (lastDirection) {
            case RIGHT -> 0;
            case DOWN -> 1;
            case LEFT -> 2;
            case UP -> 3;
        };
        return (currentPosition.x() + 1) * 4 + (currentPosition.y() + 1) * 1000 + directionValue;
    }
}
