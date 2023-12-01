package me.wolfii;

import me.wolfii.automation.SolveSelect;
import me.wolfii.automation.AutoSolver;
import me.wolfii.automation.SolveEnvironment;

public class Main {

    public static void main(String[] args) {
        AutoSolver.solve(1, SolveSelect.BOTH, SolveEnvironment.TEST_THEN_PROD);
    }
}