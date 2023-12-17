package me.wolfii.implementations.day17;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Direction;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day17 implements Solution {
    public void solveFirst(List<String> lines) {
        int[][] heatLosses = getHeatLosses(lines);

        int minHeatLoss = getMinimumHeatloss(heatLosses, 3, 0);

        System.out.println("Part 1: " + minHeatLoss);
    }

    public void solveSecond(List<String> lines) {
        int[][] heatLosses = getHeatLosses(lines);

        int minHeatLoss = getMinimumHeatloss(heatLosses, 10, 4);

        System.out.println("Part 2: " + minHeatLoss);
    }

    private int[][] getHeatLosses(List<String> lines) {
        int width = lines.get(0).length();
        int height = lines.size();
        int[][] heatLosses = new int[width][height];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                heatLosses[x][y] = Character.getNumericValue(lines.get(y).charAt(x));
            }
        }
        return heatLosses;
    }

    private int getMinimumHeatloss(int[][] heatLosses, int maxStraight, int minTurn) {
        int width = heatLosses.length;
        int height = heatLosses[0].length;
        Vec2 target = new Vec2(width - 1, height - 1);

        int[][][] visitedBitmap = new int[width][height][4];
        int heatLoss = 0;
        List<MemoryVector> processNext = new ArrayList<>();
        processNext.add(new MemoryVector(new Vec2(1, 0), Direction.EAST, 1, 0));
        processNext.add(new MemoryVector(new Vec2(0, 1), Direction.SOUTH, 1, 0));

        outer:
        while (heatLoss < 1000) {
            heatLoss++;
            List<MemoryVector> nextProcessNext = new ArrayList<>();
            for (MemoryVector memoryVector : processNext) {
                Vec2 position = memoryVector.position();
                Direction direction = memoryVector.direction();
                if (position.x() < 0 || position.x() >= width) continue;
                if (position.y() < 0 || position.y() >= height) continue;
                if ((visitedBitmap[position.x()][position.y()][getIndex(direction)] & (1 << memoryVector.distance)) != 0)
                    continue;
                int currentHeatLoss = memoryVector.heatLoss + heatLosses[position.x()][position.y()];
                if (currentHeatLoss > heatLoss) {
                    nextProcessNext.add(memoryVector);
                    continue;
                }
                visitedBitmap[position.x()][position.y()][getIndex(direction)] |= 1 << memoryVector.distance;
                if (memoryVector.distance >= minTurn && position.equals(target)) break outer;
                MemoryVector straight = new MemoryVector(position.plus(direction.vec2()), direction, memoryVector.distance + 1, currentHeatLoss);
                if (straight.distance <= maxStraight) nextProcessNext.add(straight);
                if (straight.distance <= minTurn) continue;
                Direction left = direction.turnLeft();
                nextProcessNext.add(new MemoryVector(position.plus(left.vec2()), left, 1, currentHeatLoss));
                Direction right = direction.turnRight();
                nextProcessNext.add(new MemoryVector(position.plus(right.vec2()), right, 1, currentHeatLoss));
            }
            processNext = nextProcessNext;
        }
        return heatLoss;
    }

    private int getIndex(Direction direction) {
        return switch (direction) {
            case NORTH -> 0;
            case SOUTH -> 1;
            case EAST -> 2;
            case WEST -> 3;
            default -> -1;
        };
    }


    private record MemoryVector(Vec2 position, Direction direction, int distance, int heatLoss) {
    }
}
