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

        Set<DirectionalVector> visited = new HashSet<>();
        int heatLoss = 0;
        List<MemoryVector> processNext = new ArrayList<>();
        processNext.add(new MemoryVector(new Vec2(1, 0), Direction.EAST, 1, 0));
        processNext.add(new MemoryVector(new Vec2(0, 1), Direction.SOUTH, 1, 0));

        outer:
        while (heatLoss < 1000) {
            heatLoss++;
            List<MemoryVector> nextProcessNext = new ArrayList<>();
            for (MemoryVector memoryVector : processNext) {
                if (memoryVector.position.x() < 0 || memoryVector.position.x() >= width) continue;
                if (memoryVector.position.y() < 0 || memoryVector.position.y() >= height) continue;
                DirectionalVector directionalVector = new DirectionalVector(memoryVector.position, memoryVector.direction, memoryVector.distance);
                if (visited.contains(directionalVector)) continue;
                int currentHeatLoss = memoryVector.heatLoss + heatLosses[memoryVector.position().x()][memoryVector.position().y()];
                if (currentHeatLoss > heatLoss) {
                    nextProcessNext.add(memoryVector);
                    continue;
                }
                visited.add(directionalVector);
                if (memoryVector.position.equals(target)) break outer;
                MemoryVector straight = new MemoryVector(memoryVector.position.plus(memoryVector.direction.vec2()), memoryVector.direction, memoryVector.distance + 1, currentHeatLoss);
                if (straight.distance <= maxStraight) nextProcessNext.add(straight);
                if (straight.distance <= minTurn) continue;
                Direction left = memoryVector.direction.turnLeft();
                nextProcessNext.add(new MemoryVector(memoryVector.position.plus(left.vec2()), left, 1, currentHeatLoss));
                Direction right = memoryVector.direction.turnRight();
                nextProcessNext.add(new MemoryVector(memoryVector.position.plus(right.vec2()), right, 1, currentHeatLoss));
            }

            processNext = nextProcessNext;

        }
        return heatLoss;
    }


    private record MemoryVector(Vec2 position, Direction direction, int distance, int heatLoss) {
    }

    private record DirectionalVector(Vec2 position, Direction direction, int distance){}
}
