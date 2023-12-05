package me.wolfii.implementations.day5;

record Range(long start, long length) {
    public boolean contains(long value) {
        return start <= value && start + length > value;
    }

    public boolean overlaps(Range range) {
        return (start <= range.start && start + length > range.start) ||
                (range.start <= start && range.start + range.length > start);
    }

    public long end() {
        return start + length - 1;
    }
}