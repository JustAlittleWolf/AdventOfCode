package me.wolfii.implementations.day13;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec2;

import java.util.HashMap;
import java.util.List;

public class Day13 implements Solution {
    public void solveFirst(List<String> lines) {
        System.out.println("Part 1: " + getResult(lines, false));
    }

    public void solveSecond(List<String> lines) {
        System.out.println("Part 2: " + getResult(lines, true));
    }

    private long getResult(List<String> lines, boolean misplacedRock) {
        lines.add("");

        long sum = 0;
        int y = 0, width = 0;
        HashMap<Vec2, Boolean> rockMap = new HashMap<>();

        for (String line : lines) {
            if (line.isEmpty()) {
                sum += getReflectionValue(rockMap, width, y, misplacedRock);
                rockMap.clear();
                y = 0;
                continue;
            }
            width = line.length();
            for (int x = 0; x < width; x++) {
                Vec2 pos = new Vec2(x, y);
                if (line.charAt(x) == '.') rockMap.put(pos, false);
                if (line.charAt(x) == '#') rockMap.put(pos, true);
            }
            y++;
        }
        return sum;
    }

    private int getReflectionValue(HashMap<Vec2, Boolean> rockMap, int width, int height, boolean misplacedRock) {
        for (int col = 0; col < width - 1; col++) {
            if (misplacedRocksVerticalMirror(rockMap, height, col) == (misplacedRock ? 1 : 0)) return col + 1;
        }
        for (int row = 0; row < height - 1; row++) {
            if (misplacedRocksHorizontalMirror(rockMap, width, row) == (misplacedRock ? 1 : 0)) return (row + 1) * 100;
        }
        return 0;
    }

    private int misplacedRocksVerticalMirror(HashMap<Vec2, Boolean> rockMap, int height, int mirrorCol) {
        int misplacedRocks = 0;
        for (int y = 0; y < height; y++) {
            for (int x = mirrorCol; x >= 0; x--) {
                Vec2 leftPoint = new Vec2(x, y);
                Vec2 rightPoint = new Vec2(mirrorCol + Math.abs(x - mirrorCol) + 1, y);
                if (!rockMap.containsKey(rightPoint)) continue;
                if (rockMap.get(leftPoint) != rockMap.get(rightPoint)) misplacedRocks++;
            }
        }
        return misplacedRocks;
    }

    private int misplacedRocksHorizontalMirror(HashMap<Vec2, Boolean> rockMap, int width, int mirrorRow) {
        int misplacedRocks = 0;
        for (int x = 0; x < width; x++) {
            for (int y = mirrorRow; y >= 0; y--) {
                Vec2 topPoint = new Vec2(x, y);
                Vec2 bottomPoint = new Vec2(x, mirrorRow + Math.abs(y - mirrorRow) + 1);
                if (!rockMap.containsKey(bottomPoint)) continue;
                if (rockMap.get(topPoint) != rockMap.get(bottomPoint)) misplacedRocks++;
            }
        }
        return misplacedRocks;
    }
}
