package me.wolfii.implementations.day22;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec3;

import java.util.*;

public class Day22 implements Solution {
    public void solveFirst(List<String> lines) {
        Set<Brick> bricks = getFallenBricks(lines);

        int bricksToDisintegrate = 0;
        brickChecking:
        for (Brick brick : bricks) {
            for (Brick supportedBrick : brick.supportedBricks) {
                if(supportedBrick.supportingBricks.size() <= 1) continue brickChecking;
            }
            bricksToDisintegrate++;
        }
        System.out.println("Part 1: " + bricksToDisintegrate);
    }

    public void solveSecond(List<String> lines) {
        Set<Brick> bricks = getFallenBricks(lines);

        int sum = 0;
        for(Brick brick : bricks) {
            sum += getMaximumFallingBricks(brick, new ArrayList<>(bricks));
        }
        System.out.println("Part 2: " + sum);
    }

    private int getMaximumFallingBricks(Brick brick, List<Brick> bricks) {
        bricks.remove(brick);
        int fallingBricks = 0;
        Set<Brick> checkNext = brick.supportedBricks;
        while(!checkNext.isEmpty()) {
            Set<Brick> nextCheckNext = new HashSet<>();
            brickChecking:
            for(Brick checking : checkNext) {
                if(!bricks.contains(checking)) continue;
                for(Brick supportingBrick : checking.supportingBricks) {
                    if(bricks.contains(supportingBrick)) continue brickChecking;
                }
                fallingBricks++;
                bricks.remove(checking);
                nextCheckNext.addAll(checking.supportedBricks);
            }
            checkNext = nextCheckNext;
        }
        return fallingBricks;
    }

    private Set<Brick> getFallenBricks(List<String> lines) {
        List<Brick> bricks = new ArrayList<>();
        for (String line : lines) {
            Brick brick = Brick.of(line);
            bricks.add(brick);
        }

        bricks.sort(Comparator.comparingInt(Brick::getMinHeight));

        Map<Vec3, Brick> cubes = new HashMap<>();
        Set<Brick> fallenBricks = new HashSet<>();

        while (!bricks.isEmpty()) {
            Brick brick = bricks.remove(0);
            moveDown:
            while (brick.getMinHeight() > 1) {
                Brick nextBrick = brick.moveDown();
                for (Vec3 cube : nextBrick.getAllCubes()) {
                    if (cubes.containsKey(cube)) break moveDown;
                }
                brick = nextBrick;
            }
            fallenBricks.add(brick);
            for (Vec3 cube : brick.getAllCubes()) {
                cubes.put(cube, brick);
            }
        }

        for (Brick brick : fallenBricks) {
            for (Vec3 cube : brick.getHorizontalCubes()) {
                Vec3 pos = new Vec3(cube.x(), cube.y(), cube.z() + 1);
                if (!cubes.containsKey(pos)) continue;
                cubes.get(pos).supportingBricks.add(brick);
                brick.supportedBricks.add(cubes.get(pos));
            }
        }
        return fallenBricks;
    }
}
