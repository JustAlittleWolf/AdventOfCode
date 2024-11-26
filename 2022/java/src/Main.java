import Day20.Day20;
import Day22.Day22;
import Day25.Day25;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        //Day1.solve(readFile("Day1.txt"));
        //Day2.solve(readFile("Day2.txt"));
        //Day3.solve(readFile("Day3.txt"));
        //Day4.solve(readFile("Day4.txt"));
        //Day20.solve(readFile("Day20.txt"));
        //Day25.solve(readFile("Day25.txt"));
        Day22.solve(readFile("Day22.txt"));
    }

    public static String readFile(String filename) {
        Path fileName = Path.of(filename);
        try {
            return Files.readString(fileName);
        } catch (Exception ignored) {
            return "";
        }
    }
}