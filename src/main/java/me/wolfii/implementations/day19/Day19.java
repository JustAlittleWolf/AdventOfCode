package me.wolfii.implementations.day19;

import me.wolfii.automation.Solution;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

public class Day19 implements Solution {
    private final String startWorkflow = "in";

    public void solveFirst(List<String> lines) {
        Map<String, Workflow> workflows = getWorkflows(lines);
        List<Part> parts = getParts(lines);

        long result = 0;
        for (Part part : parts) {
            String workflow = startWorkflow;
            while (workflow.charAt(0) != 'A' && workflow.charAt(0) != 'R') {
                workflow = workflows.get(workflow).testPart(part);
            }
            if (workflow.charAt(0) != 'A') continue;
            result += part.a() + part.m() + part.s() + part.x();
        }
        System.out.println("Part 1: " + result);
    }

    public void solveSecond(List<String> lines) {
        Map<String, Workflow> workflows = getWorkflows(lines);

        Deque<String> checkNext = new ArrayDeque<>(List.of(startWorkflow));
        workflows.get(startWorkflow).addInputRanges(new RangedPart(new Range(1, 4000), new Range(1, 4000), new Range(1, 4000), new Range(1, 4000), startWorkflow));

        long sum = 0;
        while (!checkNext.isEmpty()) {
            String toCheck = checkNext.pollFirst();
            for (RangedPart rangedPart : workflows.get(toCheck).getOutputRanges()) {
                String result = rangedPart.result();
                if (result.charAt(0) == 'R') continue;
                if (result.charAt(0) == 'A') {
                    sum += rangedPart.x().size() * rangedPart.m().size() * rangedPart.a().size() * rangedPart.s().size();
                    continue;
                }
                workflows.get(result).addInputRanges(rangedPart);
                checkNext.add(result);
            }
        }

        System.out.println("Part 2: " + sum);
    }

    private Map<String, Workflow> getWorkflows(List<String> lines) {
        Map<String, Workflow> workflows = new HashMap<>();
        for (String line : lines) {
            if (line.isEmpty()) break;
            String[] sections = line.substring(0, line.length() - 1).split("\\{");
            workflows.put(sections[0], new Workflow(sections[1]));
        }
        return workflows;
    }

    private List<Part> getParts(List<String> lines) {
        List<Part> parts = new ArrayList<>();
        boolean parsingParts = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                parsingParts = true;
                continue;
            }
            if (!parsingParts) continue;
            String[] properties = line.substring(1, line.length() - 1).split(",");
            int x = Integer.parseInt(properties[0].substring(2));
            int m = Integer.parseInt(properties[1].substring(2));
            int a = Integer.parseInt(properties[2].substring(2));
            int s = Integer.parseInt(properties[3].substring(2));
            parts.add(new Part(x, m, a, s));
        }
        return parts;
    }
}
