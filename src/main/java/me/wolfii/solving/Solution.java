package me.wolfii.solving;

import java.util.List;

public interface Solution {
    default void solve(List<String> lines) {
        solveFirst(lines);
        solveSecond(lines);
    }

    void solveFirst(List<String> lines);

    void solveSecond(List<String> lines);
}
