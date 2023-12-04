package me.wolfii.implementations.day4;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
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
        ArrayList<Integer> winningNumbers = new ArrayList<>();
        for(String line : lines) {
            String data = line.split(":")[1].trim();
            winningNumbers.add(this.getNumMatches(data));
        }

        HashMap<Integer, Integer> collectableCards = new HashMap<>();
        for(int i = winningNumbers.size() - 1; i >= 0; i--) {
            int totalCards = 1;
            int matches = winningNumbers.get(i);
            for(int card = i + 1; card < i + matches + 1 && card < winningNumbers.size(); card++) {
                totalCards += collectableCards.get(card);
            }
            collectableCards.put(i, totalCards);
        }

        int sum = 0;
        for(Integer totalCards : collectableCards.values()) sum += totalCards;
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
