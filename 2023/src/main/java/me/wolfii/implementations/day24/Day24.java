package me.wolfii.implementations.day24;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day24 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Hailstone> hailstones = new ArrayList<>();
        for (String line : lines) {
            hailstones.add(Hailstone.of(line));
        }

        final long AREA_MIN = 200000000000000L;
        final long AREA_MAX = 400000000000000L;

        int crossedPaths = 0;
        for (Hailstone hailstone : hailstones) {
            for (Hailstone other : hailstones) {
                if (hailstone == other) continue;
                if (hailstone.pathsIntersect(other, AREA_MIN, AREA_MAX, AREA_MIN, AREA_MAX)) crossedPaths++;
            }
        }
        System.out.println(crossedPaths / 2);
    }

    public void solveSecond(List<String> lines) {
        // Wasn't able to find a solver library for java, see
        // https://github.com/hyper-neutrino/advent-of-code/blob/main/2023/day24p2.py for an implementation in python
        // by HyperNeutrino
        System.out.println("Part 2 not implemented");
    }
}
