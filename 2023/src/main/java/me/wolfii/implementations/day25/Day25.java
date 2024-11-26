package me.wolfii.implementations.day25;

import me.wolfii.automation.Solution;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        List<VisitedEdge> visitedEdges = calculateTimesVisited(components, edges);
        for(VisitedEdge visitedEdge : visitedEdges.subList(0, 3)) {
            components.get(visitedEdge.edge.from).remove(visitedEdge.edge.to());
            components.get(visitedEdge.edge.to).remove(visitedEdge.edge.from());
        }

        List<Set<String>> groups = getGroups(components);
        if(groups.size() != 2) {
            System.out.println("No subgroups found");
            return;
        }
        System.out.println("Part 1: " + groups.get(0).size() * groups.get(1).size());
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

    private List<VisitedEdge> calculateTimesVisited(Map<String, List<String>> components, Set<Edge> edges) {
        int NUM_THREADS = Runtime.getRuntime().availableProcessors() - 1;
        final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<Edge, Integer>>> futures = new ArrayList<>();
        double totalChecks = components.size() * components.size() / 2d;
        double checksPerThread = totalChecks / (double) NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int from = (int) Math.floor(components.size() - Math.sqrt(components.size() * components.size() - 2 * checksPerThread * i));
            int to = (int) Math.floor(components.size() - Math.sqrt(components.size() * components.size() - 2 * checksPerThread * (i + 1)));
            futures.add(executorService.submit(() -> calculateTimesVisitedPartial(components, from, to)));
        }
        List<Map<Edge, Integer>> visitedCounts = new ArrayList<>();
        try {
            for (Future<Map<Edge, Integer>> future : futures) {
                visitedCounts.add(future.get());
            }
        } catch (Exception ignored) {
        }
        executorService.shutdown();
        List<VisitedEdge> visitedEdges = new ArrayList<>();
        for (Edge edge : edges) {
            int numVisited = 0;
            for (Map<Edge, Integer> visitedCount : visitedCounts) {
                numVisited += visitedCount.getOrDefault(edge, 0);
            }
            visitedEdges.add(new VisitedEdge(edge, numVisited));
        }
        visitedEdges.sort(Comparator.comparingInt(VisitedEdge::timesVisited));
        Collections.reverse(visitedEdges);
        return visitedEdges;
    }

    private Map<Edge, Integer> calculateTimesVisitedPartial(Map<String, List<String>> components, int from, int to) {
        Map<Edge, Integer> visitedCount = new HashMap<>();
        List<String> keys = new ArrayList<>(components.keySet());
        double chanceToIgnore = 1d - Math.min(1d, 2000d / (components.size() * components.size()));
        for (int i = from; i < to; i++) {
            String start = keys.get(i);
            for (int j = i + 1; j < components.size(); j++) {
                if(Math.random() < chanceToIgnore) continue;
                String end = keys.get(j);
                updateVisitedCount(start, end, components, visitedCount);
            }
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

    private record VisitedEdge(Edge edge, int timesVisited) {}
}
