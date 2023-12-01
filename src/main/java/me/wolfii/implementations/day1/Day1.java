package me.wolfii.implementations.day1;

import me.wolfii.solving.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day1 implements Solution {
    public void solveFirst(List<String> lines) {
        ArrayList<String> values = new ArrayList<>();
        for (String line : lines) {
            values.add(this.getLineValue(line));
        }
        int sum = 0;
        lines.get(100);
        for (String value : values) sum += Integer.parseInt(value);
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        ArrayList<String> values = new ArrayList<>();
        for (String line : lines) {
            line = line.replace("one", "one1one")
                    .replace("two", "two2two")
                    .replace("three", "three3three")
                    .replace("four", "four4four")
                    .replace("five", "five5five")
                    .replace("six", "six6six")
                    .replace("seven", "seven7seven")
                    .replace("eight", "eight8eight")
                    .replace("nine", "nine9nine");

            values.add(this.getLineValue(line));
        }
        int sum = 0;
        for (String value : values) sum += Integer.parseInt(value);
        System.out.println("Part 2: " + sum);
    }

    private String getLineValue(String line) {
        char firstNum = '0';
        char secondNum = '0';
        boolean foundFirst = false;
        for (char character : line.toCharArray()) {
            if (!Character.isDigit(character)) continue;
            if (foundFirst) {
                secondNum = character;
                continue;
            }
            firstNum = secondNum = character;
            foundFirst = true;
        }
        return String.valueOf(firstNum) + secondNum;
    }
}
