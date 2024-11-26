package Day4;

public record RangePair(Range first, Range second) {
    public RangePair(int fromFirst, int toFirst, int fromSecond, int toSecond) {
        this(new Range(fromFirst, toFirst), new Range(fromSecond, toSecond));
    }

    public static RangePair of(String input) {
        String[] parts = input.split(",");
        return new RangePair(Range.of(parts[0]), Range.of(parts[1]));
    }

    public boolean shouldBeReconsidered() {
        return first.fullyContains(second) || second.fullyContains(first);
    }

    public boolean overlap() {
        return first.hasMembersIn(second) || second.hasMembersIn(first);
    }
}
