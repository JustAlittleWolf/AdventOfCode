package me.wolfii.solving;

import me.wolfii.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static me.wolfii.Main.LOGGER;

@SuppressWarnings("unused")
public class AutoSolver {
    private static String packagePath = "me.wolfii.implementations";
    private static String inputsPath = "inputs";

    public static void solve(int day) {
        AutoSolver.solve(day, AutoSolve.BOTH);
    }

    public static void solve(int day, AutoSolve autoSolve) {
        List<String> lines;

        try {
            lines = getInput(day);
        } catch (Exception e) {
            LOGGER.severe(e.getClass().getName() + ": Could not read file of day " + day);
            return;
        }

        try {
            Class<?> dayClass = Class.forName(AutoSolver.packagePath + ".day" + day + ".Day" + day);
            if (!Solution.class.isAssignableFrom(dayClass)) throw new ClassCastException();
            Solution solution = (Solution) dayClass.getConstructor().newInstance();
            switch (autoSolve) {
                case BOTH -> solution.solve(lines);
                case FIRST -> solution.solveFirst(lines);
                case SECOND -> solution.solveSecond(lines);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getClass().getName() + ": Could not solve day " + day);
        }
    }

    private static List<String> getInput(int day) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(AutoSolver.inputsPath + File.separator + "day" + day + ".txt");
        assert inputStream != null;

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
