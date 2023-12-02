package me.wolfii.implementations.day2;

import me.wolfii.automation.Solution;

import java.util.List;

public class Day2 implements Solution {
    public void solveFirst(List<String> lines) {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            CubeSet cubes = this.getCubesOfLine(lines.get(i));
            if (cubes.red <= 12 && cubes.green <= 13 && cubes.blue <= 14) {
                sum += i + 1;
            }
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            CubeSet cubes = this.getCubesOfLine(line);
            sum += (cubes.red * cubes.blue * cubes.green);
        }
        System.out.println("Part 2: " + sum);
    }

    private CubeSet getCubesOfLine(String line) {
        CubeSet cubes = new CubeSet(0, 0, 0);
        String dataPart = line.split(":")[1];
        for (String match : dataPart.split(";")) {
            for (String cube : match.split(",")) {
                cube = cube.trim();
                if (cube.endsWith("red")) {
                    cubes.red = Math.max(Integer.parseInt(cube.split(" ")[0]), cubes.red);
                }
                if (cube.endsWith("blue")) {
                    cubes.blue = Math.max(Integer.parseInt(cube.split(" ")[0]), cubes.blue);
                }
                if (cube.endsWith("green")) {
                    cubes.green = Math.max(Integer.parseInt(cube.split(" ")[0]), cubes.green);
                }
            }
        }
        return cubes;
    }

    private static class CubeSet {
        int red;
        int green;
        int blue;

        public CubeSet(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}
