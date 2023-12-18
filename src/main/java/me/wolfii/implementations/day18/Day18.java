package me.wolfii.implementations.day18;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Direction;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day18 implements Solution {
    public void solveFirst(List<String> lines) {
        HashSet<Vec2> dugSpaces = new HashSet<>();

        Vec2 position = new Vec2(0, 0);
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        dugSpaces.add(position);
        for (String line : lines) {
            String[] parts = line.split(" ");

            Direction direction = switch (parts[0]) {
                case "R" -> Direction.EAST;
                case "L" -> Direction.WEST;
                case "U" -> Direction.NORTH;
                case "D" -> Direction.SOUTH;
                default -> Direction.NONE;
            };
            int count = Integer.parseInt(parts[1]);

            for (int i = 0; i < count; i++) {
                position = position.plus(direction.vec2());
                minY = Math.min(position.y(), minY);
                maxY = Math.max(position.y(), maxY);
                minX = Math.min(position.x(), minX);
                maxX = Math.max(position.x(), maxX);
                dugSpaces.add(position);
            }
        }

        for (int y = minY; y <= maxY; y++) {
            int passThroughCount = 0;
            for (int x = minX; x <= maxX; x++) {
                if (dugSpaces.contains(new Vec2(x, y)) && dugSpaces.contains(new Vec2(x, y + 1))) {
                    passThroughCount++;
                    continue;
                }
                if (passThroughCount % 2 == 1) dugSpaces.add(new Vec2(x, y));

            }
        }

        System.out.println("Part 1: " + dugSpaces.size());
    }

    public void solveSecond(List<String> lines) {
    }
}
