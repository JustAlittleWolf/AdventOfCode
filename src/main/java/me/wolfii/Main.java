package me.wolfii;

import me.wolfii.solving.SolveSelect;
import me.wolfii.solving.AutoSolver;
import me.wolfii.solving.SolveEnvironment;

public class Main {

    public static void main(String[] args) {
        AutoSolver.solve(1, SolveSelect.BOTH, SolveEnvironment.TEST_THEN_PROD);
    }
}