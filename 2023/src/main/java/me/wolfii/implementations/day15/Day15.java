package me.wolfii.implementations.day15;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day15 implements Solution {
    public void solveFirst(List<String> lines) {
        long sum = 0;
        for (String part : lines.get(0).split(",")) {
            sum += hash(part);
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        HashMap<Short, List<Lens>> boxes = new HashMap<>();
        for (String part : lines.get(0).split(",")) {
            if (part.contains("=")) {
                String label = part.split("=")[0];
                int focalLength = Integer.parseInt(part.split("=")[1]);
                short box = hash(label);
                List<Lens> lenses = boxes.getOrDefault(box, new ArrayList<>());
                boolean lensFound = false;
                for (int i = 0; i < lenses.size(); i++) {
                    if (!lenses.get(i).label.equals(label)) continue;
                    lenses.set(i, new Lens(label, focalLength));
                    lensFound = true;
                }
                if(!lensFound) lenses.add(new Lens(label, focalLength));
                boxes.put(box, lenses);
            } else {
                String label = part.split("-")[0];
                short box = hash(label);
                List<Lens> lenses = boxes.getOrDefault(box, new ArrayList<>());
                lenses.removeIf(lens -> lens.label.equals(label));
                boxes.put(box, lenses);
            }
        }

        long sum = 0;
        for (short box = 0; box < 256; box++) {
            List<Lens> lenses = boxes.getOrDefault(box, List.of());
            for (int lens = 0; lens < lenses.size(); lens++) {
                sum += (long) (1 + box) * (1 + lens) * lenses.get(lens).focalLength;
            }
        }
        System.out.println("Part 2: " + sum);

    }

    private short hash(String string) {
        int sum = 0;
        for (char character : string.toCharArray()) {
            sum += character;
            sum *= 17;
            sum %= 256;
        }
        return (short) sum;
    }

    private record Lens(String label, int focalLength) {
    }
}
