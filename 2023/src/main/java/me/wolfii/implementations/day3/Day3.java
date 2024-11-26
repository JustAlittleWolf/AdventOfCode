package me.wolfii.implementations.day3;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day3 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Value> values = this.getValues(lines);
        List<Symbol> symbols = this.getSymbols(lines, false);

        int sum = 0;
        for (Value value : values) {
            if (value.isValid(symbols)) sum += value.value();
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        List<Value> values = this.getValues(lines);
        List<Symbol> gears = this.getSymbols(lines, true);

        int sum = 0;
        for (Symbol gear : gears) {
            sum += gear.getGearValue(values);
        }
        System.out.println("Part 2: " + sum);
    }

    private List<Value> getValues(List<String> lines) {
        ArrayList<Value> values = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            int curValue = 0;
            boolean hasValue = false;
            int startX = 0;
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                if (Character.isDigit(character)) {
                    if (!hasValue) startX = x;
                    curValue *= 10;
                    curValue += Character.getNumericValue(character);
                    hasValue = true;
                    continue;
                }
                if (hasValue) values.add(new Value(curValue, startX, x - 1, y));
                hasValue = false;
                curValue = 0;
            }
            if (hasValue) values.add(new Value(curValue, startX, line.length() - 1, y));
        }
        return values;
    }

    private List<Symbol> getSymbols(List<String> lines, boolean gearsOnly) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                if(Character.isDigit(character)) continue;
                if (!gearsOnly && character != '.') {
                    symbols.add(new Symbol(x, y));
                }
                if(gearsOnly && character == '*') {
                    symbols.add(new Symbol(x, y));
                }
            }
        }
        return symbols;
    }
}
