package me.wolfii.implementations.day14;

import me.wolfii.automation.Solution;

import java.util.Arrays;
import java.util.List;

public class Day14 implements Solution {
    public void solveFirst(List<String> lines) {
        char[] locations = getLocations(lines);

        tiltNorth(locations, lines.size());
        int totalLoad = getTotalLoad(locations, lines.size());

        System.out.println("Part 1: " + totalLoad);
    }

    public void solveSecond(List<String> lines) {
        char[] locations = getLocations(lines);

        char[] cycleMap = new char[lines.size() * lines.size()];
        int cycleStart = 0;
        int size = lines.size();
        for (int i = 0; i < 1_000_000_000; i++) {
            if (i % 100 == 0) {
                System.arraycopy(locations, 0, cycleMap, 0, lines.size() * lines.size());
                cycleStart = i;
            }
            doCycle(locations, size);
            if (Arrays.equals(cycleMap, locations)) {
                int cycleLength = i - cycleStart + 1;
                i += (1_000_000_000 - i) / cycleLength * cycleLength;
            }
        }

        int totalLoad = getTotalLoad(locations, size);
        System.out.println("Part 2: " + totalLoad);
    }

    private char[] getLocations(List<String> lines) {
        char[] locations = new char[lines.size() * lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                locations[x + y * lines.size()] = lines.get(y).charAt(x);
            }
        }
        return locations;
    }

    private void doCycle(char[] locations, int size) {
        tiltNorth(locations, size);
        tiltWest(locations, size);
        tiltSouth(locations, size);
        tiltEast(locations, size);
    }

    private void tiltSouth(char[] locations, int size) {
        tiltOutwards(locations, size, true);
    }

    private void tiltEast(char[] locations, int size) {
        tiltOutwards(locations, size, false);
    }

    private void tiltNorth(char[] locations, int size) {
        tiltInwards(locations, size, true);
    }

    private void tiltWest(char[] locations, int size) {
        tiltInwards(locations, size, false);
    }

    private void tiltOutwards(char[] locations, int size, boolean south) {
        for (int a = 0; a < size; a++) {
            int outwardRock = size;
            int mirrorCount = 0;
            for (int b = size - 1; b >= 0; b--) {
                int pos = south ? a + b * size : b + a * size;
                if (locations[pos] == '#') {
                    for (int mirrorPos = outwardRock - 1; mirrorPos >= outwardRock - mirrorCount; mirrorPos--) {
                        int putPos = south ? a + mirrorPos * size : mirrorPos + a * size;
                        locations[putPos] = 'O';
                    }
                    mirrorCount = 0;
                    outwardRock = b;
                }
                if (locations[pos] == 'O') {
                    locations[pos] = 0;
                    mirrorCount++;
                }
            }
            for (int mirrorPos = outwardRock - 1; mirrorPos >= outwardRock - mirrorCount; mirrorPos--) {
                int putPos = south ? a + mirrorPos * size : mirrorPos + a * size;
                locations[putPos] = 'O';
            }
        }
    }

    private void tiltInwards(char[] locations, int size, boolean north) {
        for (int a = 0; a < size; a++) {
            int inwardRock = -1;
            int mirrorCount = 0;
            for (int b = 0; b < size; b++) {
                int pos = north ? a + b * size : b + a * size;
                if (locations[pos] == '#') {
                    for (int mirrorPos = inwardRock + 1; mirrorPos <= inwardRock + mirrorCount; mirrorPos++) {
                        int putPos = north ? a + mirrorPos * size : mirrorPos + a * size;
                        locations[putPos] = 'O';
                    }
                    mirrorCount = 0;
                    inwardRock = b;
                }
                if (locations[pos] == 'O') {
                    locations[pos] = 0;
                    mirrorCount++;
                }
            }
            for (int mirrorPos = inwardRock + 1; mirrorPos <= inwardRock + mirrorCount; mirrorPos++) {
                int putPos = north ? a + mirrorPos * size : mirrorPos + a * size;
                locations[putPos] = 'O';
            }
        }
    }

    private int getTotalLoad(char[] boulderLocations, int size) {
        int sum = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (boulderLocations[x + y * size] != 'O') continue;
                sum += size - y;
            }
        }
        return sum;
    }
}
