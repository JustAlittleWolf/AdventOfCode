package me.wolfii.automation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AutoSolver {
    private static String packagePath = "me.wolfii.implementations";
    private static String inputsPath = "inputs";

    public static void solve(int day) {
        AutoSolver.solve(day, SolveSelect.BOTH, SolveEnvironment.PROD);
    }

    public static void solve(int day, SolveSelect solveSelect) {
        AutoSolver.solve(day, solveSelect, SolveEnvironment.PROD);
    }

    public static void solve(int day, SolveEnvironment solveEnvironment) {
        AutoSolver.solve(day, SolveSelect.BOTH, solveEnvironment);
    }

    public static void solve(int day, SolveSelect solveSelect, SolveEnvironment solveEnvironment) {
        List<String> lines = null;
        boolean isTest = solveEnvironment == SolveEnvironment.TEST || solveEnvironment == SolveEnvironment.TEST_THEN_PROD;
        try {
            lines = getInput(day, isTest);
        } catch (Exception e) {
            if (solveEnvironment != SolveEnvironment.TEST_THEN_PROD) {
                System.err.println(e.getClass().getName() + ": Could not read " + (isTest ? "test" : "production") + " file of day " + day);
                return;
            }
            System.out.println("No test input found. Skipping tests.");
        }

        Solution solution;
        try {
            Class<?> dayClass = Class.forName(AutoSolver.packagePath + ".day" + day + ".Day" + day);
            if (!Solution.class.isAssignableFrom(dayClass)) throw new ClassCastException();
            solution = (Solution) dayClass.getConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Could not solve day " + day);
            return;
        }

        if (lines != null) {
            if (isTest) System.out.println("Running tests");

            trySolve(solution, lines, solveSelect, day);
        }

        if (solveEnvironment == SolveEnvironment.TEST_THEN_PROD) {
            System.out.println();
            System.out.println("Running production");
            AutoSolver.solve(day, solveSelect, SolveEnvironment.PROD);
        }
    }

    private static void trySolve(Solution solution, List<String> lines, SolveSelect solveSelect, int day) {
        first:
        try {
            if (solveSelect == SolveSelect.SECOND) break first;
            solution.solveFirst(lines);
        } catch (Exception e) {
            System.err.println("Exception while running solution for part 1 of day " + day);
            e.printStackTrace();
        }

        try {
            if (solveSelect == SolveSelect.FIRST) return;
            solution.solveSecond(lines);
        } catch (Exception e) {
            System.err.println("Exception while running solution for part 2 of day " + day);
            e.printStackTrace();
        }
    }

    private static List<String> getInput(int day, boolean test) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        String fileName = AutoSolver.inputsPath + File.separator + "day" + day + (test ? ".test" : "") + ".txt";
        InputStream inputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }

    public static void setPackagePath(String packagePath) {
        AutoSolver.packagePath = packagePath;
    }

    public static void setInputsPath(String inputsPath) {
        AutoSolver.inputsPath = inputsPath;
    }
}
