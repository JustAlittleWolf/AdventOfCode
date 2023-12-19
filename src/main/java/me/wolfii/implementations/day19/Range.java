package me.wolfii.implementations.day19;

public record Range(int from, int to) {
    public long size() {
        return to - from + 1;
    }
}
