package Day22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Area {
    private final HashMap<Vec2, Tile> tilemap = new HashMap<>();
    public final HashMap<Vec2, Direction> movemap = new HashMap<Vec2, Direction>();
    private final int width;
    private final int height;
    private boolean wrapAroundCube = false;
    private final int regionSize;

    public Area(String area) {
        Vec2 position = new Vec2(0, 0);
        int maxWidth = 0;
        int maxHeight = 0;
        HashSet<Character> validCharacters = new HashSet<>(Arrays.asList(' ', '#', '.'));
        for (String line : area.split("\n")) {
            maxHeight++;
            maxWidth = Math.max(line.length(), maxWidth);
            for (char character : line.toCharArray()) {
                if (!validCharacters.contains(character)) continue;
                tilemap.put(position, Tile.of(character));
                position = position.add(1, 0);
            }
            position = new Vec2(0, position.y() + 1);
        }
        this.width = maxWidth;
        regionSize = width / 2;
        this.height = maxHeight;
    }

    public Tile getTileAt(Vec2 pos) {
        return tilemap.getOrDefault(pos, Tile.VOID);
    }

    public Vec2 getStartPosition() {
        Vec2 position = new Vec2(0, 0);
        while (tilemap.get(position) != null) {
            if (tilemap.get(position) == Tile.OPEN) return position;
            position = position.add(1, 0);
        }
        System.out.println("START POSITION ERROR");
        return position;
    }

    public Tile getNextTile(Vec2 currentPosition, Move curMove) {
        Vec2 nextPosition = this.step(currentPosition, curMove);
        return getTileAt(nextPosition);
    }

    public Vec2 step(Vec2 currentPosition, Move curMove) {
        Vec2 nextPosition = currentPosition.stepInDirectionOf(curMove);
        if (getTileAt(nextPosition) != Tile.VOID) return nextPosition;
        if (!wrapAroundCube) {
            Vec2 wrappedPosition = switch (curMove.direction()) {
                case RIGHT -> new Vec2(0, nextPosition.y());
                case LEFT -> new Vec2(width, nextPosition.y());
                case UP -> new Vec2(nextPosition.x(), height);
                case DOWN -> new Vec2(nextPosition.x(), 0);
            };
            while (getTileAt(wrappedPosition) == Tile.VOID) {
                wrappedPosition = wrappedPosition.stepInDirectionOf(curMove);
            }
            return wrappedPosition;
        }
        return new Vec2(0, 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = -1; y <= height; y++) {
            for (int x = -1; x <= height; x++) {
                Vec2 curVec = new Vec2(x, y);
                if (movemap.containsKey(curVec)) {
                    sb.append(movemap.get(curVec).toString());
                    continue;
                }
                sb.append(tilemap.getOrDefault(curVec, Tile.VOID).toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void setWrapAroundCube(boolean b) {
        wrapAroundCube = b;
    }
}
