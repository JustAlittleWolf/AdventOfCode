package me.wolfii.implementations.day5;

import java.util.ArrayList;
import java.util.List;

class SourceMap {
    final List<SourceMapPart> sourceMapParts = new ArrayList<>();

    public void addSourceMapPart(SourceMapPart sourceMapPart) {
        sourceMapParts.add(sourceMapPart);
    }

    public long getDestination(long value) {
        for (SourceMapPart sourceMapPart : sourceMapParts) {
            if (sourceMapPart.contains(value)) return sourceMapPart.getDestination(value);
        }
        return value;
    }

    public SourceMap sorted() {
        sourceMapParts.sort((a, b) -> {
            long result = a.source().start() - b.source().start();
            if (result == 0) return 0;
            if (result > 0) return 1;
            return -1;
        });
        return this;
    }

    public List<Range> getDestinationRanges(List<Range> sourceRanges) {
        List<Range> destinationRanges = new ArrayList<>();
        for (Range sourceRange : sourceRanges) {
            long start = sourceRange.start();
            for (SourceMapPart sourceMap : sourceMapParts) {
                if (!sourceMap.overlaps(sourceRange)) continue;

                long overlapStart = Math.max(start, sourceMap.source().start());
                long overlapEnd = Math.min(sourceRange.end(), sourceMap.source().end());
                long overlapLength = overlapEnd - overlapStart + 1;

                if (start != sourceMap.source().start() && overlapStart - start > 0) {
                    destinationRanges.add(new Range(start, overlapStart - start));
                }

                long convertedOverlapStart = sourceMap.getDestination(overlapStart);
                if (overlapLength > 0) destinationRanges.add(new Range(convertedOverlapStart, overlapLength));
                start = overlapStart + overlapLength;
            }
            if (start <= sourceRange.end()) {
                long overlapLength = sourceRange.end() - start + 1;
                if (overlapLength > 0) destinationRanges.add(new Range(start, overlapLength));
            }
        }
        return destinationRanges;
    }
}
