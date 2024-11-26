package me.wolfii.implementations.day11;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Integer> emptyRows = getEmptyRows(lines);
        List<Integer> emptyCols = getEmptyCols(lines);
        List<Vec2> galaxiesList = getGalaxies(lines);

        long sum = getMinDistanceSum(galaxiesList, emptyRows, emptyCols, 1);
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        List<Integer> emptyRows = getEmptyRows(lines);
        List<Integer> emptyCols = getEmptyCols(lines);
        List<Vec2> galaxiesList = getGalaxies(lines);

        long sum = getMinDistanceSum(galaxiesList, emptyRows, emptyCols, 999_999);
        System.out.println("Part 2: " + sum);
    }

    private List<Integer> getEmptyRows(List<String> lines) {
        List<Integer> emptyRows = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            if (lines.get(y).contains("#")) continue;
            emptyRows.add(y);
        }
        return emptyRows;
    }

    private List<Integer> getEmptyCols(List<String> lines) {
        List<Integer> emptyCols = new ArrayList<>();
        outer:
        for (int x = 0; x < lines.get(0).length(); x++) {
            for (String line : lines) {
                if(line.charAt(x) == '#') continue outer;
            }
            emptyCols.add(x);
        }
        return emptyCols;
    }

    private List<Vec2> getGalaxies(List<String> lines) {
        List<Vec2> galaxies = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) != '#') continue;
                galaxies.add(new Vec2(x, y));
            }
        }
        return galaxies;
    }

    private long getMinDistanceSum(List<Vec2> galaxies, List<Integer> emptyRows, List<Integer> emptyCols, int expandBy) {
        List<Vec2> modifiedGalaxies = new ArrayList<>();
        for (Vec2 galaxy : galaxies) {
            int shiftY = 0;
            for (int row : emptyRows) {
                if (galaxy.y() < row) continue;
                shiftY += expandBy;
            }
            int shiftX = 0;
            for (int col : emptyCols) {
                if (galaxy.x() > col) continue;
                shiftX += expandBy;
            }
            modifiedGalaxies.add(galaxy.plus(shiftX, shiftY));
        }

        long sum = 0;
        for (Vec2 fromGalaxy : modifiedGalaxies) {
            for (Vec2 toGalaxy : modifiedGalaxies) {
                if (toGalaxy.equals(fromGalaxy)) continue;
                long distance = Math.abs(fromGalaxy.x() - toGalaxy.x()) + Math.abs(fromGalaxy.y() - toGalaxy.y());
                sum += distance;
            }
        }
        return sum / 2;
    }
}
