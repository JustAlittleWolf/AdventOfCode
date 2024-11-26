package me.wolfii.implementations.day9;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day9 implements Solution {
    public void solveFirst(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            List<Integer> values = getLineValues(line);
            sum += extrapolateHistory(values);
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            List<Integer> values = getLineValues(line);
            sum += extrapolateHistoryBackwards(values);
        }
        System.out.println("Part 2: " + sum);
    }

    private List<Integer> getLineValues(String line) {
        List<Integer> values = new ArrayList<>();
        for (String value : line.split(" ")) {
            values.add(Integer.parseInt(value));
        }
        return values;
    }

    private int extrapolateHistory(List<Integer> values) {
        List<Integer> differences = getValueDifferences(values);
        if (differences.isEmpty()) return values.get(values.size() - 1);
        return values.get(values.size() - 1) + extrapolateHistory(differences);
    }

    private int extrapolateHistoryBackwards(List<Integer> values) {
        List<Integer> differences = getValueDifferences(values);
        if (differences.isEmpty()) return values.get(0);
        return values.get(0) - extrapolateHistoryBackwards(differences);
    }

    private List<Integer> getValueDifferences(List<Integer> values) {
        List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < values.size() - 1; i++) {
            differences.add(values.get(i + 1) - values.get(i));
        }
        if (differences.stream().allMatch((val) -> val == 0)) return List.of();
        return differences;
    }
}
