package Day4;

public record Range(int from, int to) {
    public static Range of(String input) {
        String[] parts = input.split("-");
        return new Range(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public boolean fullyContains(Range other) {
        return this.from <= other.from && this.to >= other.to;
    }

    public boolean hasMembersIn(Range other) {
        return (this.from >= other.from && this.from <= other.to) || (this.to <= other.to && this.to >= other.from);
    }
}
