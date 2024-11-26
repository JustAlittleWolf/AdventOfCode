package Day20;

public class Number {
    int startIndex;
    long value;
    public Number(long value, int startIndex) {
        this.startIndex = startIndex;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
