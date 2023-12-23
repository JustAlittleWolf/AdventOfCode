package me.wolfii.implementations.day23;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.InputUtil;
import me.wolfii.implementations.common.Vec2;

import java.util.*;

public class Day23 implements Solution {
    public void solveFirst(List<String> lines) {
        char[][] maze = InputUtil.getCharArray(lines);

        Vec2 start = new Vec2(1, 0);
        int longestPath = getLongestPath(start, new HashSet<>(), maze);
        System.out.println("Part 1: " + longestPath);
    }

    public void solveSecond(List<String> lines) {
        char[][] maze = InputUtil.getCharArray(lines);

        Map<Vec2, List<Path>> paths = getPaths(maze);
        Vec2 start = new Vec2(1, 0);
        long maxLength = getLongestPathFast(start, new HashSet<>(), paths, 1, maze);

        System.out.println("Part 2: " + maxLength);
    }

    private int getLongestPath(Vec2 start, Set<Vec2> visitedTiles, char[][] maze) {
        Deque<Vec2> visitNext = new ArrayDeque<>(List.of(start));
        Vec2 endPos = new Vec2(maze.length - 2, maze[0].length - 1);
        while (!visitNext.isEmpty()) {
            Vec2 pos = visitNext.pollFirst();
            if (pos.equals(endPos)) return visitedTiles.size();
            visitedTiles.add(pos);
            switch (maze[pos.x()][pos.y()]) {
                case '#' -> {
                }
                case '>' -> visitNext.add(pos.plus(1, 0));
                case 'v' -> visitNext.add(pos.plus(0, 1));
                case '<' -> visitNext.add(pos.plus(-1, 0));
                case '^' -> visitNext.add(pos.plus(0, -1));
                default -> {
                    for (Vec2 neigh : pos.neighbours()) {
                        if (isBlocked(neigh, maze)) continue;
                        visitNext.add(neigh);
                    }
                }
            }
            visitNext.removeAll(visitedTiles);

            if (visitNext.size() > 1) {
                int maximum = 0;
                for (Vec2 nextPos : visitNext) {
                    maximum = Math.max(maximum, getLongestPath(nextPos, new HashSet<>(visitedTiles), maze));
                }
                return maximum;
            }
        }
        return -1;
    }

    //"Fast", runs in a couple of secons
    private int getLongestPathFast(Vec2 start, Set<Vec2> visitedTiles, Map<Vec2, List<Path>> paths, int steps, char[][] maze) {
        int maxLength = 0;
        Vec2 endPos = new Vec2(maze.length - 2, maze[0].length - 1);
        Vec2 prevToEnd = paths.get(endPos).get(0).to();
        visitedTiles.add(start);
        for (Path path : paths.get(start)) {
            if (visitedTiles.contains(path.to())) continue;
            if (path.to().equals(prevToEnd)) {
                maxLength = Math.max(maxLength, steps + path.length() + paths.get(endPos).get(0).length());
                continue;
            }
            maxLength = Math.max(maxLength, getLongestPathFast(path.to(), new HashSet<>(visitedTiles), paths, steps + path.length(), maze));
        }
        return maxLength;
    }

    private Map<Vec2, List<Path>> getPaths(char[][] maze) {
        Map<Vec2, List<Path>> paths = new HashMap<>();
        Deque<Vec2> startingPositions = new ArrayDeque<>(List.of(new Vec2(1, 0)));
        HashSet<Vec2> visitedPositions = new HashSet<>();
        Vec2 endPos = new Vec2(maze.length - 2, maze[0].length - 1);

        while (!startingPositions.isEmpty()) {
            Vec2 start = startingPositions.pollFirst();
            visitedPositions.add(start);
            Deque<Vec2> walkNext = new ArrayDeque<>();
            for (Vec2 neigh : start.neighbours()) {
                if (isBlocked(neigh, maze)) continue;
                if (visitedPositions.contains(neigh)) continue;
                if (walkNext.isEmpty()) {
                    walkNext.add(neigh);
                    continue;
                }
                startingPositions.add(start);
                break;
            }
            int steps = 0;
            while (!walkNext.isEmpty()) {
                Vec2 pos = walkNext.pollFirst();
                visitedPositions.add(pos);
                if (pos.equals(endPos)) {
                    paths.putIfAbsent(start, new ArrayList<>());
                    paths.putIfAbsent(pos, new ArrayList<>());
                    paths.get(start).add(new Path(pos, steps));
                    paths.get(pos).add(new Path(start, steps));
                    break;
                }
                for (Vec2 neigh : pos.neighbours()) {
                    if (isBlocked(neigh, maze)) continue;
                    walkNext.add(neigh);
                }
                steps++;
                if (walkNext.size() > 2) {
                    visitedPositions.remove(pos);
                    paths.putIfAbsent(start, new ArrayList<>());
                    paths.putIfAbsent(pos, new ArrayList<>());
                    paths.get(start).add(new Path(pos, steps));
                    paths.get(pos).add(new Path(start, steps));
                    startingPositions.add(pos);
                    break;
                }
                walkNext.removeAll(visitedPositions);
            }
        }
        return paths;
    }

    private boolean isBlocked(Vec2 pos, char[][] maze) {
        if (pos.x() < 0 || pos.x() >= maze.length) return true;
        if (pos.y() < 0 || pos.y() >= maze[0].length) return true;
        return maze[pos.x()][pos.y()] == '#';
    }
}
