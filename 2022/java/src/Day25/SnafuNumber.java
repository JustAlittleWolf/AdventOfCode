package Day25;

import java.util.HashMap;

public class SnafuNumber {
    private long value;
    private static HashMap<Character, Integer> valueTable = new HashMap<>();
    private static HashMap<Integer, Character> reverseValueTable = new HashMap<>();

    static {
        valueTable.put('2', 2);
        valueTable.put('1', 1);
        valueTable.put('0', 0);
        valueTable.put('-', -1);
        valueTable.put('=', -2);

        valueTable.forEach((key, value) -> {
            reverseValueTable.put(value, key);
        });
    }

    public SnafuNumber(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public String toSnafuEncoding() {
        StringBuilder encoded = new StringBuilder();

        int highestPower = 0;
        while (Math.pow(5, highestPower) / 2f < Math.abs(this.value)) {
            highestPower++;
        }

        long trackedValue = this.value;

        for (int power = highestPower - 1; power >= 0; power--) {
            if(trackedValue == 0) {
                encoded.append("0");
                continue;
            }

            int curDigitValue = 0;
            int sign = (int) (trackedValue / Math.abs(trackedValue));
            long restMaxAbsoluteValue = (long) Math.floor(Math.pow(5, power) / 2);
            while(Math.abs(trackedValue) > restMaxAbsoluteValue) {
                curDigitValue += sign;
                trackedValue -= sign * (long) Math.pow(5, power);
            }
            encoded.append(reverseValueTable.get(curDigitValue));
        }
        return encoded.toString();
    }

    public static SnafuNumber of(String toConvert) {
        long totalValue = 0;
        for (int i = 0; i < toConvert.length(); i++) {
            char curChar = toConvert.charAt(toConvert.length() - 1 - i);
            totalValue += (long) Math.pow(5, i) * valueTable.get(curChar);
        }
        return new SnafuNumber(totalValue);
    }
}
