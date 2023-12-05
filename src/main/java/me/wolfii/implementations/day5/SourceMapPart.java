package me.wolfii.implementations.day5;

record SourceMapPart(Range source, Range destination) {
    public long getDestination(long value) {
        if (contains(value)) {
            long index = value - source.start();
            return destination.start() + index;
        } else {
            return value;
        }
    }

    public boolean contains(long value) {
        return source.contains(value);
    }

    public boolean overlaps(Range range) {
        return source.overlaps(range);
    }
}
