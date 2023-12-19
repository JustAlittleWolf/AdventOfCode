package me.wolfii.implementations.day19;

public class Rule {
    private final ExaminedProperty examinedProperty;
    private final Comparator comparator;
    private final int comparingValue;
    private final String result;

    public Rule(String rule) {
        String[] parts = rule.split(":");
        if (parts.length == 1) {
            examinedProperty = null;
            comparator = null;
            comparingValue = 0;
            result = parts[0];
            return;
        }
        examinedProperty = switch (rule.charAt(0)) {
            case 'x' -> ExaminedProperty.X;
            case 'm' -> ExaminedProperty.M;
            case 'a' -> ExaminedProperty.A;
            case 's' -> ExaminedProperty.S;
            default -> null;
        };
        comparator = switch (rule.charAt(1)) {
            case '<' -> Comparator.LESS_THAN;
            case '>' -> Comparator.GREATER_THAN;
            default -> null;
        };
        comparingValue = Integer.parseInt(parts[0].substring(2));
        result = parts[1];
    }

    public String test(Part part) {
        if (examinedProperty == null) return result;
        int value = switch (examinedProperty) {
            case X -> part.x();
            case M -> part.m();
            case A -> part.a();
            case S -> part.s();
        };
        boolean valid = switch (comparator) {
            case GREATER_THAN -> value > comparingValue;
            case LESS_THAN -> value < comparingValue;
        };
        if (valid) return result;
        return null;
    }

    private enum ExaminedProperty {
        X, M, A, S
    }

    private enum Comparator {
        GREATER_THAN, LESS_THAN
    }
}
