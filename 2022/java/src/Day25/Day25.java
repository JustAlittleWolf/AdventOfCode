package Day25;

import java.util.ArrayList;

public class Day25 {
    public static void solve(String input) {
        ArrayList<SnafuNumber> numberList = new ArrayList<>();
        long sum = 0;
        for (String line : input.split("\n")) {
            line = line.trim();
            SnafuNumber curNum = SnafuNumber.of(line);
            numberList.add(curNum);
            sum += curNum.getValue();
        }
        System.out.println(new SnafuNumber(sum).toSnafuEncoding());
    }
}
