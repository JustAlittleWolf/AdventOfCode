package me.wolfii.implementations.common;

import java.util.List;

public class InputUtil {
    public static char[][] getCharMap(List<String> lines) {
        char[][] map = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map[x][y] = line.charAt(x);
            }
        }
        return map;
    }
}
