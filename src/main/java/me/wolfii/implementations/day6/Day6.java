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
            int numWins = 0;
            for (int i = 1; i < raceDuration; i++) {
                long distance = getDistance(i, raceDuration);
                if (distance > winningDistance) numWins++;
            }
            sum *= numWins;
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        long raceDuration = getValues(lines.get(0).replace(" ", "")).get(0);
        long winningDistance = getValues(lines.get(1).replace(" ", "")).get(0);

        long lowerBound = findBoundBinarySearch(raceDuration, winningDistance, Bound.LOWER);
        long upperBound = findBoundBinarySearch(raceDuration, winningDistance, Bound.UPPER);
        long numWins = (upperBound + 1) - lowerBound;
        System.out.println("Part 2: " + numWins);
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

    private long findBoundBinarySearch(long raceDuration, long winningDistance, Bound bound) {
        long stepSize = raceDuration / 2;
        long median = raceDuration / 2;

        while (stepSize > 0) {
            boolean currentWin = getDistance(median, raceDuration) > winningDistance;
            if (currentWin) {
                boolean neighbourWin = getDistance(median + bound.signum, raceDuration) > winningDistance;
                if(!neighbourWin) return median;
            }
            stepSize /= 2;
            if (currentWin) {
                median += bound.signum * stepSize;
            } else {
                median -= bound.signum * stepSize;
            }
        }
        System.out.println("ERR");
        return 0;
    }

    private long getDistance(long heldTime, long raceDuration) {
        return heldTime * (raceDuration - heldTime);
    }
}
