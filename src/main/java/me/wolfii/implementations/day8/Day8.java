package me.wolfii.implementations.day8;

import me.wolfii.automation.Solution;

import java.util.*;

public class Day8 implements Solution {

    public void solveFirst(List<String> lines) {
        String path = lines.get(0).trim();
        Map<String, NetworkNode> nodes = new HashMap<>();

        NetworkNode currentNode = null;
        for (String line : lines.subList(2, lines.size())) {
            NetworkNode networkNode = new NetworkNode(line);
            if (networkNode.name.equals("AAA")) currentNode = networkNode;
            nodes.put(networkNode.name, networkNode);
        }

        long steps = 0;
        int pathIndex = 0;
        if(currentNode == null) {
            System.out.println("Part 1: No start node found");
            return;
        }
        while (!currentNode.isTargetNode) {
            char instruction = path.charAt(pathIndex);
            pathIndex = (pathIndex + 1) % path.length();

            if (instruction == 'R') currentNode = nodes.get(currentNode.rightNode);
            if (instruction == 'L') currentNode = nodes.get(currentNode.leftNode);
            steps++;
        }
        System.out.println("Part 1: " + steps);
    }

    public void solveSecond(List<String> lines) {
        String path = lines.get(0).trim();
        Map<String, NetworkNode> nodes = new HashMap<>();

        List<NetworkNode> currentNodes = new ArrayList<>();
        for (String line : lines.subList(2, lines.size())) {
            NetworkNode networkNode = new NetworkNode(line);
            if(networkNode.name.endsWith("A")) currentNodes.add(networkNode);
            nodes.put(networkNode.name, networkNode);
        }

        int steps = 0;
        int pathIndex = 0;
        int[] cycleStarts = new int[currentNodes.size()];
        int[] cycleDurations = new int[currentNodes.size()];
        while (Arrays.stream(cycleDurations).anyMatch((duration) -> duration == 0)) {
            char instruction = path.charAt(pathIndex);
            pathIndex = (pathIndex + 1) % path.length();

            for(int i = 0; i < currentNodes.size(); i++) {
                NetworkNode currentNode = currentNodes.get(i);
                if (instruction == 'R') currentNodes.set(i, nodes.get(currentNode.rightNode));
                if (instruction == 'L') currentNodes.set(i, nodes.get(currentNode.leftNode));
                if(currentNode.isGhostEndNode) {
                    if(cycleStarts[i] != 0 && cycleDurations[i] == 0) cycleDurations[i] = steps - cycleStarts[i];
                    if(cycleStarts[i] == 0) cycleStarts[i] = steps;
                }
            }
            steps++;
        }
        long leastCommonMultiple = 1;
        for(int cycleDuration : cycleDurations) {
            leastCommonMultiple = leastCommonMultiple(leastCommonMultiple, cycleDuration);
        }

        System.out.println("Part 2: " + leastCommonMultiple);
    }

    private long leastCommonMultiple(long number1, long number2) {
        long absHigherNumber = Math.max(number1, number2);
        long absLowerNumber = Math.min(number1, number2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
