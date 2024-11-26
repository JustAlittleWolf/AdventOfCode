package Day1;

import java.util.ArrayList;

public class Day1 {
    public static void solve(String input) {
        String[] lines = input.split("\n");
        ArrayList<Integer> calories = new ArrayList<>();

        int curSum = 0;
        for(String line : lines) {
            if(line.isEmpty()) {
                calories.add(curSum);
                curSum = 0;
                continue;
            }
            curSum += Integer.parseInt(line);
        }
        calories.add(curSum);

        calories.sort((a, b) -> b - a);

        System.out.println(calories.get(0));
        System.out.println(calories.get(0) + calories.get(1) + calories.get(2));
    }
}
