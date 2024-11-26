package Day4;

public class Day4 {
    public static void solve(String input) {
        String[] inputs = input.split("\n");

        int completelyContain = 0;
        int overlaps = 0;
        for (String line : inputs) {
            RangePair pair = RangePair.of(line);
            completelyContain += pair.shouldBeReconsidered() ? 1 : 0;
            overlaps += pair.overlap() ? 1 : 0;
        }
        System.out.println(completelyContain);
        System.out.println(overlaps);
    }
}
