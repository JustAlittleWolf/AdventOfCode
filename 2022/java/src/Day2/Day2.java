package Day2;

public class Day2 {
    public static void solve(String input) {
        String[] inputs = input.split("\n");

        Match[] matches = new Match[inputs.length];
        for(int i = 0; i < inputs.length; i++) matches[i] = new Match(inputs[i]);

        int firstSum = 0;
        for (Match match : matches) firstSum += match.getFirstResult();
        int secondSum = 0;
        for (Match match : matches) secondSum += match.getSecondResult();
        System.out.println(firstSum);
        System.out.println(secondSum);
    }
}
