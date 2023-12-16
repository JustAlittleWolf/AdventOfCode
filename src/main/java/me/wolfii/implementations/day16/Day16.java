package me.wolfii.implementations.day16;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec2;
import me.wolfii.implementations.common.Direction;

import java.util.*;

public class Day16 implements Solution {
    public void solveFirst(List<String> lines) {
        char[][] mirrorMap = getMirrorMap(lines);

        int coveredTiles = getCoveredTiles(new DirectionalTile(new Vec2(-1, 0), Direction.EAST), mirrorMap);
        System.out.println("Part 1: " + coveredTiles);
    }

    public void solveSecond(List<String> lines) {
        char[][] mirrorMap = getMirrorMap(lines);
        int width = lines.get(0).length();
        int height = lines.size();

        int maximum = 0;
        for (int x = 0; x < width; x++) {
            DirectionalTile topDirectionalTile = new DirectionalTile(new Vec2(x, -1), Direction.SOUTH);
            DirectionalTile bottomDirectionalTile = new DirectionalTile(new Vec2(x, height), Direction.NORTH);
            int topTileCovered = getCoveredTiles(topDirectionalTile, mirrorMap);
            int bottomTileCovered = getCoveredTiles(bottomDirectionalTile, mirrorMap);
            maximum = Math.max(Math.max(topTileCovered, bottomTileCovered), maximum);
        }

        for (int y = 0; y < lines.size(); y++) {
            DirectionalTile leftDirectionalTile = new DirectionalTile(new Vec2(-1, y), Direction.EAST);
            DirectionalTile rightDirectionalTile = new DirectionalTile(new Vec2(width, y), Direction.WEST);
            int leftTileCovered = getCoveredTiles(leftDirectionalTile, mirrorMap);
            int rightTileCovered = getCoveredTiles(rightDirectionalTile, mirrorMap);
            maximum = Math.max(Math.max(leftTileCovered, rightTileCovered), maximum);
        }

        System.out.println("Part 2: " + maximum);
    }

    private char[][] getMirrorMap(List<String> lines) {
        char[][] mirrorMap = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                mirrorMap[x][y] = lines.get(y).charAt(x);
            }
        }
        return mirrorMap;
    }

    private int getCoveredTiles(DirectionalTile start, char[][] mirrorMap) {
        Deque<DirectionalTile> visitNext = new ArrayDeque<>(List.of(start));
        int[][] visitedTiles = new int[mirrorMap.length][mirrorMap[0].length];
        int coveredCount = 0;

        while (!visitNext.isEmpty()) {
            DirectionalTile directionalTile = visitNext.poll();
            Direction direction = directionalTile.direction();
            Vec2 nextPosition = directionalTile.pos().plus(direction.vec2());
            if (nextPosition.x() < 0 || nextPosition.x() >= mirrorMap[0].length) continue;
            if (nextPosition.y() < 0 || nextPosition.y() >= mirrorMap.length) continue;

            int visitedDirections = visitedTiles[nextPosition.x()][nextPosition.y()];
            if ((visitedDirections & direction.bitmask()) != 0) continue;
            if (visitedDirections == 0) coveredCount++;
            visitedTiles[nextPosition.x()][nextPosition.y()] |= direction.bitmask();

            switch (mirrorMap[nextPosition.x()][nextPosition.y()]) {
                case '|' -> {
                    if (direction.isVertical()) {
                        visitNext.add(new DirectionalTile(nextPosition, direction));
                        break;
                    }
                    visitNext.add(new DirectionalTile(nextPosition, direction.turnLeft()));
                    visitNext.add(new DirectionalTile(nextPosition, direction.turnRight()));
                }
                case '-' -> {
                    if (direction.isHorizontal()) {
                        visitNext.add(new DirectionalTile(nextPosition, direction));
                        break;
                    }
                    visitNext.add(new DirectionalTile(nextPosition, direction.turnLeft()));
                    visitNext.add(new DirectionalTile(nextPosition, direction.turnRight()));
                }
                case '/' -> visitNext.add(new DirectionalTile(nextPosition, direction.mirrorFirstMedian()));
                case '\\' -> visitNext.add(new DirectionalTile(nextPosition, direction.mirrorSecondMedian()));
                default -> visitNext.add(new DirectionalTile(nextPosition, direction));
            }
        }
        return coveredCount;
    }


    private record DirectionalTile(Vec2 pos, Direction direction) {
    }
}
