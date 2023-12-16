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
        Set<DirectionalTile> visitedTiles = new HashSet<>();
        Deque<DirectionalTile> visitNext = new ArrayDeque<>();
        visitNext.addFirst(start);
        boolean[][] visitedMap = new boolean[mirrorMap.length][mirrorMap[0].length];
        int coveredCount = 0;

        while (!visitNext.isEmpty()) {
            DirectionalTile directionalTile = visitNext.poll();
            if (visitedTiles.contains(directionalTile)) continue;
            visitedTiles.add(directionalTile);

            Direction currentDirection = directionalTile.direction();
            Vec2 newPosition = directionalTile.pos().plus(currentDirection.vec2());
            if (newPosition.x() < 0 || newPosition.x() >= mirrorMap[0].length) continue;
            if (newPosition.y() < 0 || newPosition.y() >= mirrorMap.length) continue;

            if(!visitedMap[newPosition.x()][newPosition.y()]) coveredCount++;
            visitedMap[newPosition.x()][newPosition.y()] = true;

            switch (mirrorMap[newPosition.x()][newPosition.y()]) {
                case '|' -> {
                    if (currentDirection.isVertical()) {
                        visitNext.add(new DirectionalTile(newPosition, currentDirection));
                        break;
                    }
                    visitNext.add(new DirectionalTile(newPosition, currentDirection.turnLeft()));
                    visitNext.add(new DirectionalTile(newPosition, currentDirection.turnRight()));
                }
                case '-' -> {
                    if (currentDirection.isHorizontal()) {
                        visitNext.add(new DirectionalTile(newPosition, currentDirection));
                        break;
                    }
                    visitNext.add(new DirectionalTile(newPosition, currentDirection.turnLeft()));
                    visitNext.add(new DirectionalTile(newPosition, currentDirection.turnRight()));
                }
                case '/' -> visitNext.add(new DirectionalTile(newPosition, currentDirection.mirrorFirstMedian()));
                case '\\' -> visitNext.add(new DirectionalTile(newPosition, currentDirection.mirrorSecondMedian()));
                default -> visitNext.add(new DirectionalTile(newPosition, currentDirection));
            }
        }
        return coveredCount;
    }


    private record DirectionalTile(Vec2 pos, Direction direction) {
    }
}
