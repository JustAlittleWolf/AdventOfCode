package me.wolfii.implementations.day14;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.Vec2;

import java.util.HashMap;
import java.util.List;

public class Day14 implements Solution {
    public void solveFirst(List<String> lines) {
        HashMap<Vec2, Character> locations = getLocations(lines);

        locations = tiltNorth(locations, lines.size());
        int totalLoad = getTotalLoad(locations, lines.size());

        System.out.println("Part 1: " + totalLoad);
    }

    public void solveSecond(List<String> lines) {
        HashMap<Vec2, Character> locations = getLocations(lines);

        HashMap<Vec2, Character> cycleMap = new HashMap<>();
        int cycleStart = 0;
        int size = lines.size();
        for (int i = 0; i < 1_000_000_000; i++) {
            if (i % 100 == 0) {
                cycleMap = locations;
                cycleStart = i;
            }
            locations = doCycle(locations, size);
            if (cycleMap.equals(locations)) {
                int cycleLength = i - cycleStart + 1;
                i += (1_000_000_000 - i) / cycleLength * cycleLength;
            }
        }

        int totalLoad = getTotalLoad(locations, size);
        System.out.println("Part 2: " + totalLoad);
    }

    private HashMap<Vec2, Character> getLocations(List<String> lines) {
        HashMap<Vec2, Character> locations = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                Vec2 pos = new Vec2(x, y);
                if(lines.get(y).charAt(x) == '.') continue;
                locations.put(pos, lines.get(y).charAt(x));
            }
        }
        return locations;
    }

    private HashMap<Vec2, Character> doCycle(HashMap<Vec2, Character> locations, int size) {
        locations = tiltNorth(locations, size);
        locations = tiltWest(locations, size);
        locations = tiltSouth(locations, size);
        return tiltEast(locations, size);
    }

    private HashMap<Vec2, Character> tiltSouth(HashMap<Vec2, Character> locations, int size) {
        return tiltOutwards(locations, size, true);
    }

    private HashMap<Vec2, Character> tiltEast(HashMap<Vec2, Character> locations, int size) {
        return tiltOutwards(locations, size, false);
    }

    private HashMap<Vec2, Character> tiltNorth(HashMap<Vec2, Character> locations, int size) {
        return tiltInwards(locations, size, true);
    }

    private HashMap<Vec2, Character> tiltWest(HashMap<Vec2, Character> locations, int size) {
        return tiltInwards(locations, size, false);
    }

    private HashMap<Vec2, Character> tiltOutwards(HashMap<Vec2, Character> locations, int size, boolean south) {
        HashMap<Vec2, Character> tilted = new HashMap<>();
        for (int a = 0; a < size; a++) {
            int outwardRock = size;
            int mirrorCount = 0;
            for (int b = size - 1; b >= 0; b--) {
                Vec2 pos = south ? new Vec2(a, b) : new Vec2(b, a);
                char character = locations.getOrDefault(pos, '.');
                if (character == '#') {
                    tilted.put(pos, '#');
                    for (int mirrorPos = outwardRock - 1; mirrorPos >= outwardRock - mirrorCount; mirrorPos--) {
                        Vec2 putPos = south ? new Vec2(a, mirrorPos) : new Vec2(mirrorPos, a);
                        tilted.put(putPos, 'O');
                    }
                    mirrorCount = 0;
                    outwardRock = b;
                }
                if (character == 'O') mirrorCount++;
            }
            for (int mirrorPos = outwardRock - 1; mirrorPos >= outwardRock - mirrorCount; mirrorPos--) {
                Vec2 putPos = south ? new Vec2(a, mirrorPos) : new Vec2(mirrorPos, a);
                tilted.put(putPos, 'O');
            }
        }
        return tilted;
    }

    private HashMap<Vec2, Character> tiltInwards(HashMap<Vec2, Character> locations, int size, boolean north) {
        HashMap<Vec2, Character> tilted = new HashMap<>();
        for (int a = 0; a < size; a++) {
            int inwardRock = -1;
            int mirrorCount = 0;
            for (int b = 0; b < size; b++) {
                Vec2 pos = north ? new Vec2(a, b) : new Vec2(b, a);
                char character = locations.getOrDefault(pos, '.');
                if (character == '#') {
                    tilted.put(pos, '#');
                    for (int mirrorPos = inwardRock + 1; mirrorPos <= inwardRock + mirrorCount; mirrorPos++) {
                        Vec2 putPos = north ? new Vec2(a, mirrorPos) : new Vec2(mirrorPos, a);
                        tilted.put(putPos, 'O');
                    }
                    mirrorCount = 0;
                    inwardRock = b;
                }
                if (character == 'O') mirrorCount++;
            }
            for (int mirrorPos = inwardRock + 1; mirrorPos <= inwardRock + mirrorCount; mirrorPos++) {
                Vec2 putPos = north ? new Vec2(a, mirrorPos) : new Vec2(mirrorPos, a);
                tilted.put(putPos, 'O');
            }
        }
        return tilted;
    }

    private int getTotalLoad(HashMap<Vec2, Character> locations, int size) {
        int sum = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Vec2 pos = new Vec2(x, y);
                if (locations.getOrDefault(pos, '.') != 'O') continue;
                sum += size - y;
            }
        }
        return sum;
    }
}
