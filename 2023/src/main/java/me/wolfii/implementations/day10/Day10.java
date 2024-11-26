package me.wolfii.implementations.day10;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Direction;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day10 implements Solution {
    public void solveFirst(List<String> lines) {
        HashMap<Vec2, Pipe> pipes = getPipes(lines);
        Vec2 start = getStart(lines);

        List<Vec2> loop = getLoop(start, pipes);
        System.out.println("Part 1: " + loop.size() / 2);
    }


    public void solveSecond(List<String> lines) {
        HashMap<Vec2, Pipe> pipes = getPipes(lines);
        Vec2 start = getStart(lines);

        List<Vec2> loop = getLoop(start, pipes);

        int width = lines.get(0).length();
        int height = lines.size();

        Set<Vec2> containedTiles = getContainedTiles(start, loop, pipes, width, height);
        System.out.println("Part 2: " + containedTiles.size());
    }

    private HashMap<Vec2, Pipe> getPipes(List<String> lines) {
        HashMap<Vec2, Pipe> pipes = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                pipes.put(new Vec2(x, y), new Pipe(line.charAt(x)));
            }
        }
        return pipes;
    }

    private Vec2 getStart(List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == 'S') return new Vec2(x, y);
            }
        }
        return new Vec2(0, 0);
    }

    private List<Vec2> getLoop(Vec2 start, HashMap<Vec2, Pipe> pipes) {
        List<Vec2> upLoop = getLoop(start, pipes, Direction.NORTH);
        if (!upLoop.isEmpty()) return upLoop;

        List<Vec2> rightLoop = getLoop(start, pipes, Direction.EAST);
        if (!rightLoop.isEmpty()) return rightLoop;

        List<Vec2> downLoop = getLoop(start, pipes, Direction.SOUTH);
        if (!downLoop.isEmpty()) return downLoop;

        return List.of();
    }

    private List<Vec2> getLoop(Vec2 start, HashMap<Vec2, Pipe> pipes, Direction startDirection) {
        List<Vec2> tiles = new ArrayList<>();
        tiles.add(start);
        Vec2 currentTile = start.plus(startDirection.vec2());
        Direction to = startDirection;
        while (!currentTile.equals(start)) {
            if (!pipes.containsKey(currentTile)) return List.of();

            Pipe currentPipe = pipes.get(currentTile);
            if (!currentPipe.canEnterFrom(to.inverted())) return List.of();

            tiles.add(currentTile);
            to = currentPipe.getExit(to.inverted());

            currentTile = currentTile.plus(to.vec2());
        }
        return tiles;
    }

    private Set<Vec2> getContainedTiles(Vec2 start, List<Vec2> loop, HashMap<Vec2, Pipe> pipes, int width, int height) {
        Direction direction = Direction.of(loop.get(1).plus(start.inverted()));
        Set<Vec2> rightwardsTiles = new HashSet<>();
        Set<Vec2> leftwardsTiles = new HashSet<>();

        for (int i = 1; i < loop.size(); i++) {
            Vec2 currentTile = loop.get(i);
            Pipe currentPipe = pipes.get(currentTile);
            if (currentPipe.isStraight()) {
                rightwardsTiles.add(currentTile.plus(direction.turnRight().vec2()));
                leftwardsTiles.add(currentTile.plus(direction.turnLeft().vec2()));
                continue;
            }
            for (Vec2 neighbour : currentPipe.disconnectedNeighbours()) {
                Set<Vec2> targetTiles = currentPipe.isRightTurn(direction.inverted()) ? leftwardsTiles : rightwardsTiles;
                targetTiles.add(currentTile.plus(neighbour));
            }
            direction = currentPipe.getExit(direction.inverted());
        }

        loop.forEach(rightwardsTiles::remove);
        loop.forEach(leftwardsTiles::remove);

        rightwardsTiles = propagate(rightwardsTiles, loop, width, height);
        leftwardsTiles = propagate(leftwardsTiles, loop, width, height);

        return rightwardsTiles.isEmpty() ? leftwardsTiles : rightwardsTiles;
    }

    private Set<Vec2> propagate(Set<Vec2> tiles, List<Vec2> loop, int width, int height) {
        Set<Vec2> propagatedTiles = new HashSet<>(tiles);
        Set<Vec2> checked = new HashSet<>(tiles);
        checked.addAll(loop);
        Deque<Vec2> toCheck = new ArrayDeque<>(tiles);

        while (!toCheck.isEmpty()) {
            Vec2 element = toCheck.pollLast();
            checked.add(element);
            for (Vec2 neighbour : element.neighbours()) {
                if (neighbour.x() < 0 || neighbour.x() > width) return Set.of();
                if (neighbour.y() < 0 || neighbour.y() > height) return Set.of();
                if (checked.contains(neighbour)) continue;
                propagatedTiles.add(neighbour);
                toCheck.add(neighbour);
            }
        }
        return propagatedTiles;
    }
}
