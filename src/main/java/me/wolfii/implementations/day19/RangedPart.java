package me.wolfii.implementations.day19;

public record RangedPart(Range x, Range m, Range a, Range s, String result) {
    public RangedPart modify(Rule.ExaminedProperty property, Range range) {
        return switch (property) {
            case X -> new RangedPart(range, m, a, s, result);
            case M -> new RangedPart(x, range, a, s, result);
            case A -> new RangedPart(x, m, range, s, result);
            case S -> new RangedPart(x, m, a, range, result);
            case NONE -> new RangedPart(x, m, a, s, result);
        };
    }
}
