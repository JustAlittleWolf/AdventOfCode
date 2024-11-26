package me.wolfii.implementations.day3;

import java.util.ArrayList;
import java.util.List;

record Symbol(int x, int y) {
    int getGearValue(List<Value> values) {
        List<Value> adjacentValues = new ArrayList<>();
        for (Value value : values) {
            if (value.isValid(List.of(this))) adjacentValues.add(value);
        }
        if (adjacentValues.size() != 2) return 0;
        return adjacentValues.get(0).value() * adjacentValues.get(1).value();
    }
}
