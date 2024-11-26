package Day2;

public enum Choice {
    ROCK(1 ),
    PAPER(2),
    SCISSORS(3);

    public final int value;
    Choice(int value) {
        this.value = value;
    }
}
