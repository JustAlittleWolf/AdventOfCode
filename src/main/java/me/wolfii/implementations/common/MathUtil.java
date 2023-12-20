package me.wolfii.implementations.common;

import java.util.Collection;

public class MathUtil {
    public static long lcm(long number1, long number2) {
        long absHigherNumber = Math.max(number1, number2);
        long absLowerNumber = Math.min(number1, number2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    public static <Number extends java.lang.Number> long lcm(Collection<Number> values) {
        long lcm = 1;
        for (Number value : values) {
            lcm = lcm(lcm, value.longValue());
        }
        return lcm;
    }

    public static long lcm(int... values) {
        long lcm = 1;
        for (int value : values) {
            lcm = lcm(lcm, value);
        }
        return lcm;
    }
}
