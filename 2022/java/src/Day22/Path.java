package Day22;

import java.util.ArrayList;

public class Path {
    private final ArrayList<Move> moves = new ArrayList<>();

    public Path(String path) {
        StringBuilder curNum = new StringBuilder();
        Direction direction = Direction.RIGHT;
        for (char character : path.toCharArray()) {
            if (Character.isDigit(character)) {
                curNum.append(character);
                continue;
            }
            moves.add(new Move(direction, Integer.parseInt(curNum.toString())));
            curNum.setLength(0);
            direction = direction.getRotated(character);
        }
        if(curNum.isEmpty()) return;
        moves.add(new Move(direction, Integer.parseInt(curNum.toString())));
    }

    public Move getNext() {
        return moves.remove(0);
    }

    public boolean hasElements() {
        return !moves.isEmpty();
    }
}
