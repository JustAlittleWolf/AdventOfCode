package me.wolfii.implementations.day6;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day6 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Long> times = getValues(lines.get(0));
        List<Long> distances = getValues(lines.get(1));

        long sum = 1;
        for (int race = 0; race < times.size(); race++) {
            long raceDuration = times.get(race);
            long winningDistance = distances.get(race);

            double discriminant = Math.sqrt(raceDuration * raceDuration / 4d - winningDistance);
            long lowerBound = (long) Math.ceil(raceDuration / 2d - discriminant);
            long upperBound = (long) (raceDuration / 2d + discriminant);
            sum *= (upperBound + 1) - lowerBound;
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        long raceDuration = getValues(lines.get(0).replace(" ", "")).get(0);
        long winningDistance = getValues(lines.get(1).replace(" ", "")).get(0);

        double discriminant = Math.sqrt(raceDuration * raceDuration / 4d - winningDistance);
        long lowerBound = (long) Math.ceil(raceDuration / 2d - discriminant);
        long upperBound = (long) (raceDuration / 2d + discriminant);
        System.out.println("Part 2: " + ((upperBound + 1) - lowerBound));
    }

    private List<Long> getValues(String line) {
        String data = line.split(":")[1];
        List<Long> values = new ArrayList<>();
        for(String value : data.split(" ")) {
            if(value.isEmpty()) continue;
            values.add(Long.parseLong(value));
        }
        return values;
    }
}
