package me.wolfii.implementations.day5;

import me.wolfii.automation.Solution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 implements Solution {
    public void solveFirst(List<String> lines) {
        ArrayList<Long> seeds = getSeeds(lines.get(0));

        ArrayList<FullSourceMap> sourceMaps = getSourceMaps(lines);

        long sum = Long.MAX_VALUE;
        for (long seed : seeds) {
            sum = Math.min(sum, getSeedLocationNumber(sourceMaps, seed));
        }

        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        ArrayList<Range> seedRanges = new ArrayList<>();
        ArrayList<Long> seeds = getSeeds(lines.get(0));
        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new Range(seeds.get(i), seeds.get(i + 1)));
        }
        ArrayList<FullSourceMap> sourceMaps = getSourceMaps(lines);

        System.out.println(new Range(9, 2).overlaps(new Range(10, 1)));

        System.out.println("Part 2: " );
    }

    private ArrayList<FullSourceMap> getSourceMaps(List<String> lines) {
        ArrayList<FullSourceMap> sourceMaps = new ArrayList<>();
        FullSourceMap currentSourceMap = new FullSourceMap();
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty()) {
                sourceMaps.add(currentSourceMap);
                currentSourceMap = new FullSourceMap();
                continue;
            }
            if (!Character.isDigit(line.charAt(0))) continue;
            currentSourceMap.addSourceMap(getSourceMap(line));
        }
        sourceMaps.add(currentSourceMap);
        return sourceMaps;
    }

    private ArrayList<Long> getSeeds(String firstLine) {
        String[] seedStrings = firstLine.split(":")[1].trim().split(" ");

        ArrayList<Long> seeds = new ArrayList<>();
        for (String seed : seedStrings) {
            seeds.add(Long.parseLong(seed));
        }
        return seeds;
    }

    private long getSeedLocationNumber(ArrayList<FullSourceMap> sourceMaps, long seed) {
        long value = seed;
        for (FullSourceMap sourceMap : sourceMaps) {
            value = sourceMap.getDestination(value);
        }
        return value;
    }

    private PartialSourceMap getSourceMap(String line) {
        String[] values = line.split(" ");

        long sourceStart = Long.parseLong(values[0]);
        long destinationStart = Long.parseLong(values[1]);
        long length = Long.parseLong(values[2]);

        return new PartialSourceMap(new Range(destinationStart, length), new Range(sourceStart, length));
    }

    private static class FullSourceMap {
        final ArrayList<PartialSourceMap> containedSourceMaps = new ArrayList<>();

        public void addSourceMap(PartialSourceMap partialSourceMap) {
            containedSourceMaps.add(partialSourceMap);
        }

        public long getDestination(long value) {
            for (PartialSourceMap sourceMap : containedSourceMaps) {
                if (sourceMap.contains(value)) return sourceMap.getDestination(value);
            }
            return value;
        }

        public List<Range> getRangeBlocks(Range seedRange) {
            for (PartialSourceMap sourceMap : containedSourceMaps) {

            }
            return List.of();
        }
    }

    private record PartialSourceMap(Range source, Range destination) {
        public long getDestination(long value) {
            if (this.source.contains(value)) {
                long index = value - source.start;
                return this.destination.start + index;
            } else {
                return value;
            }
        }

        public boolean contains(long value) {
            return this.source.contains(value);
        }

        public boolean overlaps(Range range) {
            return source.overlaps(range);
        }
    }

    private record Range(long start, long length) {
        public boolean contains(long value) {
            return start <= value && start + length > value;
        }

        public boolean overlaps(Range range) {
            return (start <= range.start && start + length > range.start) ||
                    (range.start <= start && range.start + range.length > start);
        }
    }
}
