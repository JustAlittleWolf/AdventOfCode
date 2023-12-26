package me.wolfii.implementations.day25;

import me.wolfii.Main;
import me.wolfii.automation.Solution;

import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Day25 implements Solution {
    public void solveFirst(List<String> lines) {
        Map<String, List<String>> components = new HashMap<>();

        Set<Edge> edges = new HashSet<>();
        for (String line : lines) {
            String[] parts = line.split(": ");
            String[] comps = parts[1].split(" ");
            components.putIfAbsent(parts[0], new ArrayList<>());
            for (String component : comps) {
                components.putIfAbsent(component, new ArrayList<>());
                components.get(component).add(parts[0]);
                components.get(parts[0]).add(component);
                List<String> elements = new ArrayList<>(List.of(parts[0], component));
                elements.sort(String::compareTo);
                edges.add(new Edge(elements.get(0), elements.get(1)));
            }
        }
        Edge firstCut = findBestEdge(components, edges, 0);
        components.get(firstCut.from).remove(firstCut.to());
        components.get(firstCut.to).remove(firstCut.from());
        Edge secondCut = findBestEdge(components, edges, 1);
        components.get(secondCut.from).remove(secondCut.to());
        components.get(secondCut.to).remove(secondCut.from());
        Edge thirdCut = findBestEdge(components, edges, 2);
        components.get(thirdCut.from).remove(thirdCut.to());
        components.get(thirdCut.to).remove(thirdCut.from());

        List<Set<String>> groups = getGroups(components);
        assert groups.size() == 2;
        System.out.println(groups.get(0).size() * groups.get(1).size());
    }

    private List<Set<String>> getGroups(Map<String, List<String>> components) {
        Set<String> visited = new HashSet<>();
        List<Set<String>> groups = new ArrayList<>();

        while (visited.size() < components.size()) {
            Set<String> group = new HashSet<>();
            Deque<String> visitNext = new ArrayDeque<>();
            Set<String> remainingPoints = new HashSet<>(components.keySet());
            remainingPoints.removeAll(visited);
            visitNext.add(remainingPoints.iterator().next());
            while (!visitNext.isEmpty()) {
                String position = visitNext.pollFirst();
                if (visited.contains(position)) continue;
                visited.add(position);
                group.add(position);
                visitNext.addAll(components.get(position));
            }
            groups.add(group);
        }
        return groups;
    }

    private Edge findBestEdge(Map<String, List<String>> components, Set<Edge> edges, int part) {
        int NUM_THREADS = Runtime.getRuntime().availableProcessors();
        final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<Edge, Integer>>> futures = new ArrayList<>();
        AtomicInteger routesChecked = new AtomicInteger(0);
        double totalChecks = components.size() * components.size() / 2d;
        double checksPerThread = totalChecks / (double) NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int from = (int) Math.floor(components.size() - Math.sqrt(components.size() * components.size() - 2 * checksPerThread * i));
            int to = (int) Math.floor(components.size() - Math.sqrt(components.size() * components.size() - 2 * checksPerThread * (i + 1)));
            futures.add(executorService.submit(() -> findBestEdgePartial(components, from, to, routesChecked, part)));
        }
        List<Map<Edge, Integer>> visitedCounts = new ArrayList<>();
        try {
            for (Future<Map<Edge, Integer>> future : futures) {
                visitedCounts.add(future.get());
            }
        } catch (Exception ignored) {
        }
        executorService.shutdown();
        Edge bestEdge = null;
        int maximumVisited = 0;
        for (Edge edge : edges) {
            int numVisited = 0;
            for (Map<Edge, Integer> visitedCount : visitedCounts) {
                numVisited += visitedCount.getOrDefault(edge, 0);
            }
            if (numVisited <= maximumVisited) continue;
            bestEdge = edge;
            maximumVisited = numVisited;
        }
        return bestEdge;
    }

    private Map<Edge, Integer> findBestEdgePartial(Map<String, List<String>> components, int from, int to, AtomicInteger routesChecked, int part) {
        Map<Edge, Integer> visitedCount = new HashMap<>();
        List<String> keys = new ArrayList<>(components.keySet());
        for (int i = from; i < to; i++) {
            String start = keys.get(i);
            for (int j = i + 1; j < components.size(); j++) {
                String end = keys.get(j);
                updateVisitedCount(start, end, components, visitedCount);
            }
            int count = routesChecked.getAndIncrement();
            if (count % 16 == 15)
                System.out.println("Calculating...\t" + String.format("%.1f", 100f * (count + part * components.size()) / (components.size() * 3)) + "%");
        }
        return visitedCount;
    }

    private void updateVisitedCount(String start, String end, Map<String, List<String>> components, Map<Edge, Integer> visitedCount) {
        HashSet<String> visited = new HashSet<>();
        Deque<String> visitNext = new ArrayDeque<>();
        visitNext.addFirst(start);

        Map<String, String> bestPrevious = new HashMap<>();

        while (!visitNext.isEmpty()) {
            String position = visitNext.pollFirst();
            if (position.equals(end)) break;
            if (visited.contains(position)) continue;
            visited.add(position);
            for (String next : components.get(position)) {
                if (!bestPrevious.containsKey(next)) bestPrevious.put(next, position);
                visitNext.add(next);
            }
        }
        String pos = end;
        while (!start.equals(pos)) {
            String prev = pos;
            pos = bestPrevious.get(pos);
            Edge edge;
            if (pos.compareTo(prev) < 0) {
                edge = new Edge(pos, prev);
            } else {
                edge = new Edge(prev, pos);
            }
            int count = visitedCount.getOrDefault(edge, 0);
            visitedCount.put(edge, count + 1);
        }
    }

    public void solveSecond(List<String> lines) {

    }

    private record Edge(String from, String to) {
    }
}
