package me.wolfii.automation;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileAlreadyExistsException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AutoSolver {
    private static String packagePath = "me.wolfii.implementations";
    private static String inputsPath = "inputs";
    private static String sessionToken = null;

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
            if (solveEnvironment != SolveEnvironment.TEST) AutoSolver.downloadInput(day);
        } catch (ConnectionException e) {
            AutoSolver.setSessionToken(null);
            System.err.println("Error downloading input of day " + day + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Error downloading input of day " + day);
        }

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
        try {
            if (solveSelect != SolveSelect.SECOND) solution.solveFirst(lines);
        } catch (Exception e) {
            System.err.println("Exception while running solution for part 1 of day " + day);
            e.printStackTrace();
        }

        try {
            if (solveSelect != SolveSelect.FIRST) solution.solveSecond(lines);
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
        bufferedReader.close();
        return lines;
    }

    private static void downloadInput(int day) throws Exception {
        if (sessionToken == null) return;

        File outFile = new File(AutoSolver.inputsPath + File.separator + "day" + day + ".txt");
        File testFile = new File(AutoSolver.inputsPath + File.separator + "day" + day + ".test.txt");
        if (outFile.exists()) return;

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Cookie", "session=" + AutoSolver.sessionToken)
                .uri(new URI("https://adventofcode.com/2023/day/"+day+"/input"))
                .GET()
                .timeout(Duration.of(3, ChronoUnit.SECONDS))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() == 404)
            throw new ConnectionException("Day " + day + " does not exists or is not yet accessible");
        if (httpResponse.statusCode() == 400 || httpResponse.statusCode() == 500)
            throw new ConnectionException("Invalid session token");
        if (httpResponse.statusCode() != 200)
            throw new ConnectionException("Request unsuccessful with status code " + httpResponse.statusCode());

        outFile.getParentFile().mkdirs();
        if (!outFile.createNewFile()) {
            throw new FileAlreadyExistsException("File already Exists");
        }
        testFile.createNewFile();

        OutputStream outputStream = new FileOutputStream(outFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(httpResponse.body());
        bufferedWriter.close();

        System.out.println("Downloaded input for day " + day + "\n");
    }

    public static void setPackagePath(String packagePath) {
        AutoSolver.packagePath = packagePath;
    }

    public static void setInputsPath(String inputsPath) {
        AutoSolver.inputsPath = inputsPath;
    }

    public static void setSessionToken(String sessionToken) {
        AutoSolver.sessionToken = sessionToken;
    }
}
