package me.wolfii.implementations.day5;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day5 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Long> seeds = getSeeds(lines.get(0));
        List<SourceMap> sourceMaps = getSourceMaps(lines);

        long bestSeed = Long.MAX_VALUE;
        for (long seed : seeds) {
            bestSeed = Math.min(bestSeed, getSeedLocation(sourceMaps, seed));
        }

        System.out.println("Part 1: " + bestSeed);
    }

    public void solveSecond(List<String> lines) {
        List<Long> seeds = getSeeds(lines.get(0));
        List<SourceMap> sourceMaps = getSourceMaps(lines);

        List<Range> seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new Range(seeds.get(i), seeds.get(i + 1)));
        }

        long bestSeed = Long.MAX_VALUE;
        List<Range> seedDestinations = seedRanges;
        for (SourceMap sourceMap : sourceMaps) {
            seedDestinations = sourceMap.getDestinationRanges(seedDestinations);
        }
        for (Range range : seedDestinations) {
            bestSeed = Math.min(range.start(), bestSeed);
        }

        System.out.println("Part 2: " + bestSeed);
    }

    private List<SourceMap> getSourceMaps(List<String> lines) {
        List<SourceMap> sourceMaps = new ArrayList<>();
        SourceMap sourceMap = new SourceMap();
        for (String line : lines.subList(2, lines.size())) {
            if (line.isEmpty()) {
                sourceMaps.add(sourceMap.sorted());
                sourceMap = new SourceMap();
                continue;
            }
            if (!Character.isDigit(line.charAt(0))) continue;
            sourceMap.addSourceMapPart(getSourceMapPart(line));
        }
        sourceMaps.add(sourceMap.sorted());
        return sourceMaps;
    }

    private List<Long> getSeeds(String firstLine) {
        String[] seedStrings = firstLine.split(":")[1].trim().split(" ");
        List<Long> seeds = new ArrayList<>();
        for (String seed : seedStrings) {
            seeds.add(Long.parseLong(seed));
        }
        return seeds;
    }

    private long getSeedLocation(List<SourceMap> sourceMaps, long seed) {
        long value = seed;
        for (SourceMap sourceMap : sourceMaps) {
            value = sourceMap.getDestination(value);
        }
        return value;
    }

    private SourceMapPart getSourceMapPart(String line) {
        String[] values = line.split(" ");

        long sourceStart = Long.parseLong(values[0]);
        long destinationStart = Long.parseLong(values[1]);
        long length = Long.parseLong(values[2]);

        return new SourceMapPart(new Range(destinationStart, length), new Range(sourceStart, length));
    }
}
