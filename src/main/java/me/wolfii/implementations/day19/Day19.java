package me.wolfii.implementations.day19;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day19 implements Solution {
    public void solveFirst(List<String> lines) {
        HashMap<String, Workflow> workflows = new HashMap<>();

        List<Part> parts = new ArrayList<>();
        boolean parsingParts = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                parsingParts = true;
                continue;
            }
            if (parsingParts) {
                String[] properties = line.substring(1, line.length() - 1).split(",");
                int x = Integer.parseInt(properties[0].substring(2));
                int m = Integer.parseInt(properties[1].substring(2));
                int a = Integer.parseInt(properties[2].substring(2));
                int s = Integer.parseInt(properties[3].substring(2));
                parts.add(new Part(x, m, a, s));
                continue;
            }
            String[] sections = line.substring(0, line.length() -1).split("\\{");
            workflows.put(sections[0], new Workflow(sections[1]));
        }

        String workflow;
        long result = 0;
        for(Part part : parts) {
            workflow = "in";
            while(workflow.charAt(0) != 'A' && workflow.charAt(0) != 'R') {
                workflow = workflows.get(workflow).testPart(part);
            }
            if(workflow.charAt(0) != 'A') continue;
            result += part.a() + part.m() + part.s() + part.x();
        }
        System.out.println("Part 1: " + result);
    }

    public void solveSecond(List<String> lines) {

    }
}
