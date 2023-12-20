package me.wolfii.implementations.day20;

public enum State {
    HIGH,
    LOW;

    public State inverted() {
        return switch (this) {
            case HIGH -> LOW;
            case LOW -> HIGH;
        };
    }
}
