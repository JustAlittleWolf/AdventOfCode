package me.wolfii.implementations.day12;

public enum SpringStatus {
    WORKING,
    UNKNOWN,
    BROKEN;

    public boolean broken() {
        return this == UNKNOWN || this == BROKEN;
    }

    public boolean working() {
        return this == UNKNOWN || this == WORKING;
    }

    public static SpringStatus of(char character) {
        return switch (character) {
            case '#' -> WORKING;
            case '.' -> BROKEN;
            case '?' -> UNKNOWN;
            default -> null;
        };
    }
}