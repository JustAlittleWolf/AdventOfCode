package me.wolfii.implementations.day7;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day7 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Hand> hands = getHands(lines, false);
        System.out.println("Part 1: " + getWinnings(hands));
    }

    public void solveSecond(List<String> lines) {
        List<Hand> hands = getHands(lines, true);
        System.out.println("Part 2: " + getWinnings(hands));
    }

    private List<Hand> getHands(List<String> lines, boolean withJokers) {
        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            String unparsedCards = line.split(" ")[0];
            int bidAmount = Integer.parseInt(line.split(" ")[1]);
            hands.add(new Hand(unparsedCards.toCharArray(), bidAmount, withJokers));
        }
        return hands;
    }

    private int getWinnings(List<Hand> hands) {
        hands.sort(Hand::compareTo);
        int winnings = 0;
        for (int i = 0; i < hands.size(); i++) {
            winnings += hands.get(i).bidAmount * (i + 1);
        }
        return winnings;
    }
}
