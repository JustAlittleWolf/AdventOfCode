package me.wolfii.implementations.day21;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day21 implements Solution {
    public void solveFirst(List<String> lines) {
        char[][] gardenMap = getGardenMap(lines);
        Vec2 start = getStart(gardenMap);
        long possibleEndTiles = solve(start, 64, gardenMap, false);
        System.out.println("Part 1: " + possibleEndTiles);
    }

    @SuppressWarnings({"IntegerDivisionInFloatingPointContext", "DuplicatedCode"})
    public void solveSecond(List<String> lines) {
        char[][] gardenMap = getGardenMap(lines);

        if(!fulfillsPart2Requirements(gardenMap)) {
            System.out.println("Part 2: Incompatible input data");
            return;
        }

        Vec2 start = getStart(gardenMap);

        int size = gardenMap.length;
        long steps = 26501365L;

        long gridsFromCenter = steps / size - 1;

        long oddGrids = (long) Math.pow(((gridsFromCenter / 2) * 2 + 1), 2);
        long evenGrids = (long) Math.pow((((gridsFromCenter + 1) / 2) * 2), 2);

        long pointsPerOddFullyCovered = solve(start, size * 2L + 1, gardenMap, true);
        long pointsPerEvenFullyCovered = solve(start, size * 2L, gardenMap, true);

        long totalPointsFullyCovered = oddGrids * pointsPerOddFullyCovered + evenGrids * pointsPerEvenFullyCovered;

        long corner_steps = size - 1;
        long topPoints = solve(new Vec2(start.x(), size - 1), corner_steps, gardenMap, true);
        long rightPoints = solve(new Vec2(0, start.y()), corner_steps, gardenMap, true);
        long bottomPoints = solve(new Vec2(start.x(), 0), corner_steps, gardenMap, true);
        long leftPoints = solve(new Vec2(size - 1, start.y()), corner_steps, gardenMap, true);

        long totalCornerPoints = topPoints + rightPoints + bottomPoints + leftPoints;

        long totalEdgePointsOuter = getEdgePoints(size / 2 - 1, gardenMap, gridsFromCenter + 1);
        long totalEdgePointsInner = getEdgePoints(size * 3L / 2 - 1, gardenMap, gridsFromCenter);

        System.out.println("Part 2: " + (totalPointsFullyCovered + totalCornerPoints + totalEdgePointsOuter + totalEdgePointsInner));
    }

    private boolean fulfillsPart2Requirements(char[][] gardenMap) {
        if(gardenMap.length % 2 == 0) return false;
        if(gardenMap[0].length % 2 == 0) return false;
        int center = gardenMap.length / 2;
        for(int i = 0; i < gardenMap.length; i++) {
            if(gardenMap[center][i] == '#') return false;
            if(gardenMap[i][center] == '#') return false;
        }
        return true;
    }

    private long getEdgePoints(long steps, char[][] gardenMap, long gridsPerSide) {
        long topRight = solve(new Vec2(0, gardenMap.length - 1), steps, gardenMap, true);
        long topLeft = solve(new Vec2(gardenMap.length - 1, gardenMap.length - 1), steps, gardenMap, true);
        long bottomRight = solve(new Vec2(0, 0), steps, gardenMap, true);
        long bottomLeft = solve(new Vec2(gardenMap.length - 1, 0), steps, gardenMap, true);
        return gridsPerSide * (topRight + bottomRight + bottomLeft + topLeft);
    }

    private char[][] getGardenMap(List<String> lines) {
        char[][] gardenMap = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                gardenMap[x][y] = line.charAt(x);
            }
        }
        return gardenMap;
    }

    private Vec2 getStart(char[][] gardenMap) {
        for (int y = 0; y < gardenMap[0].length; y++) {
            for (int x = 0; x < gardenMap.length; x++) {
                if(gardenMap[x][y] == 'S') return new Vec2(x, y);
            }
        }
        return new Vec2(-1, -1);
    }

    private long solve(Vec2 start, long steps, char[][] gardenMap, boolean checkStart) {
        boolean[][] visitedTiles = new boolean[gardenMap.length][gardenMap[0].length];
        Set<Vec2> visitNext = new HashSet<>();
        long endTileCount = 0;
        visitNext.add(start);
        for (int step = 0; step <= steps; step++) {
            if (visitNext.isEmpty()) return endTileCount;
            Set<Vec2> current = new HashSet<>(visitNext);
            visitNext.clear();
            for (Vec2 pos : current) {
                visitedTiles[pos.x()][pos.y()] = true;
                if (step % 2 == (checkStart ? steps % 2 : 0)) endTileCount++;
                for (Vec2 nextPos : pos.neighbours()) {
                    if (nextPos.x() < 0 || nextPos.x() >= gardenMap.length) continue;
                    if (nextPos.y() < 0 || nextPos.y() >= gardenMap.length) continue;
                    if (visitedTiles[nextPos.x()][nextPos.y()]) continue;
                    if (gardenMap[nextPos.x()][nextPos.y()] == '#') continue;
                    visitNext.add(nextPos);
                }
            }
        }
        return endTileCount;
    }
}
