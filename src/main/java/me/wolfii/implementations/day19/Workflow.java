package me.wolfii.implementations.day19;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private final List<Rule> rules = new ArrayList<>();
    private final List<RangedPart> outputRanges = new ArrayList<>();

    public Workflow(String section) {
        for (String rule : section.split(",")) {
            rules.add(new Rule(rule));
        }
    }

    public String testPart(Part part) {
        for (Rule rule : rules) {
            String result = rule.test(part);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public void addInputRanges(RangedPart inputRanges) {
        Range x = inputRanges.x();
        Range m = inputRanges.m();
        Range a = inputRanges.a();
        Range s = inputRanges.s();

        for (Rule rule : rules) {
            RangedPart outputRangedPart = new RangedPart(x, m, a, s, rule.result);
            if (rule.examinedProperty == Rule.ExaminedProperty.NONE) {
                outputRanges.add(outputRangedPart);
                continue;
            }

            Range range = switch (rule.examinedProperty) {
                case X -> x;
                case M -> m;
                case A -> a;
                case S -> s;
                default -> new Range(0, 0);
            };

            Range insertedRange = switch (rule.comparator) {
                case LESS_THAN -> new Range(range.from(), rule.comparingValue - 1);
                case GREATER_THAN -> new Range(rule.comparingValue + 1, range.to());
            };
            outputRanges.add(outputRangedPart.modify(rule.examinedProperty, insertedRange));

            Range updatedRange = switch (rule.comparator) {
                case LESS_THAN -> new Range(rule.comparingValue, range.to());
                case GREATER_THAN -> new Range(range.from(), rule.comparingValue);
            };
            switch (rule.examinedProperty) {
                case X -> x = updatedRange;
                case M -> m = updatedRange;
                case A -> a = updatedRange;
                case S -> s = updatedRange;
            }
        }
    }

    public List<RangedPart> getOutputRanges() {
        return this.outputRanges;
    }
}
