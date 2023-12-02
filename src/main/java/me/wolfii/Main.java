package me.wolfii;

import me.wolfii.automation.EnvReader;
import me.wolfii.automation.SolveSelect;
import me.wolfii.automation.AutoSolver;
import me.wolfii.automation.SolveEnvironment;

public class Main {

    public static void main(String[] args) {
        AutoSolver.setSessionToken(EnvReader.get("ADVENT_OF_CODE_SESSION_TOKEN"));

        AutoSolver.solve(2, SolveSelect.BOTH, SolveEnvironment.TEST_THEN_PROD);
    }
}