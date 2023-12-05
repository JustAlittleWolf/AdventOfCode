package me.wolfii.implementations.day4;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Day4 implements Solution {
    public void solveFirst(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            String data = line.split(":")[1].trim();

            int matches = this.getNumMatches(data);
            sum += (int) Math.pow(2, matches - 1);
        }
        System.out.println("Part 1: " + sum);
    }

    public void solveSecond(List<String> lines) {
        int[] collectibleCards = new int[lines.size()];
        for(int i = lines.size() - 1; i >= 0; i--) {
            int totalCards = 1;
            String data = lines.get(i).split(":")[1].trim();
            int matches = this.getNumMatches(data);
            for(int card = i + 1; card < i + matches + 1 && card < lines.size(); card++) {
                totalCards += collectibleCards[card];
            }
            collectibleCards[i] = totalCards;
        }

        int sum = 0;
        for(Integer totalCards : collectibleCards) sum += totalCards;
        System.out.println("Part 2: " + sum);
    }

    private int getNumMatches(String data) {
        int matches = 0;
        String[] winningNums = data.split("\\|")[0].trim().split(" ");
        String myNums = " " + data.split("\\|")[1] + " ";

        for (String winningNum : winningNums) {
            if (winningNum.isEmpty()) continue;
            if (myNums.contains(" " + winningNum + " ")) matches++;
        }
        return matches;
    }
}
