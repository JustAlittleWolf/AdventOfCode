package me.wolfii.implementations.day12;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day12 implements Solution {
    public void solveFirst(List<String> lines) {
        System.out.println("Part 1: " + getVariationSums(lines));
    }

    public void solveSecond(List<String> lines) {
        List<String> modifiedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            StringBuilder modifiedLine = new StringBuilder(line);
            for (int i = 0; i < 4; i++) {
                modifiedLine.insert(0, '?').insert(0, parts[0]);
                modifiedLine.append(',').append(parts[1]);
            }
            modifiedLines.add(modifiedLine.toString());
        }
        System.out.println("Part 2: " + getVariationSums(modifiedLines));
    }

    private long getVariationSums(List<String> lines) {
        long sum = 0;
        HashMap<VariationState, Long> cache = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            List<Integer> wordLengths = new ArrayList<>();
            for (String length : parts[1].split(",")) {
                wordLengths.add(Integer.parseInt(length));
            }
            sum += getVariations(parts[0], wordLengths, 0, cache);
            cache.clear();
        }
        return sum;
    }

    private long getVariations(String characters, List<Integer> wordLengths, int currentWord, HashMap<VariationState, Long> cache) {
        if (characters.isEmpty()) {
            if (wordLengths.isEmpty() && currentWord == 0) return 1;
            if (wordLengths.size() == 1 && wordLengths.get(0) == currentWord) return 1;
            return 0;
        }

        VariationState variationState = new VariationState(characters, wordLengths, currentWord);
        if (cache.containsKey(variationState)) return cache.get(variationState);
        
        char spring = characters.charAt(0);
        long count = 0;
        if (spring == '?' || spring == '.') {
            if (!wordLengths.isEmpty() && wordLengths.get(0) == currentWord) {
                count += getVariations(characters.substring(1), wordLengths.subList(1, wordLengths.size()), 0, cache);
            } else if (currentWord == 0) {
                count += getVariations(characters.substring(1), wordLengths, 0, cache);
            }
        }
        if (spring == '?' || spring == '#') {
            count += getVariations(characters.substring(1), wordLengths, currentWord + 1, cache);
        }

        cache.put(variationState, count);
        return count;
    }
}
