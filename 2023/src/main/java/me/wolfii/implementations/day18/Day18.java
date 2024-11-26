package me.wolfii.implementations.day18;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Direction;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day18 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Vec2> corners = getCorners(lines, false);
        long area = getArea(corners);
        long circumference = getCircumference(corners);

        System.out.println("Part 1: " + (area + (circumference / 2) + 1));
    }

    public void solveSecond(List<String> lines) {
        List<Vec2> corners = getCorners(lines, true);
        long area = getArea(corners);
        long circumference = getCircumference(corners);

        System.out.println("Part 2: " + (area + (circumference / 2) + 1));
    }

    private List<Vec2> getCorners(List<String> lines, boolean hexadecimal) {
        List<Vec2> corners = new ArrayList<>(List.of(new Vec2(0, 0)));
        for (String line : lines) {
            String[] parts = line.split(" ");
            int distance = hexadecimal ? Integer.parseInt(parts[2].substring(2, 7), 16) : Integer.parseInt(parts[1]);
            Direction direction = switch (hexadecimal ? parts[2].charAt(7) : parts[0].charAt(0)) {
                case 'R', '0' -> Direction.EAST;
                case 'D', '1' -> Direction.SOUTH;
                case 'L', '2' -> Direction.WEST;
                case 'U', '3' -> Direction.NORTH;
                default -> Direction.NONE;
            };
            Vec2 lastCorner = corners.get(corners.size() - 1);
            corners.add(lastCorner.plus(direction.vec2().multiply(distance)));
        }
        return corners;
    }

    private long getCircumference(List<Vec2> corners) {
        long circumference = 0;
        for (int i = 0; i < corners.size() - 1; i++) {
            Vec2 first = corners.get(i);
            Vec2 second = corners.get(i + 1);
            circumference += Math.abs(first.x() - second.x()) + Math.abs(first.y() - second.y());
        }
        return circumference;
    }

    private long getArea(List<Vec2> corners) {
        long area = 0;
        for (int i = 1; i < corners.size() - 1; i++) {
            Vec2 first = corners.get(i);
            Vec2 second = corners.get(i + 1);
            area += (long) first.x() * second.y() - (long) second.x() * first.y();
        }
        return Math.abs(area / 2);
    }
}
