package me.wolfii.implementations.day7;

import me.wolfii.automation.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day7 implements Solution {
    public void solveFirst(List<String> lines) {
        List<Deck> decks = getDecks(lines);
        Deck.withJokers = false;
        int winnings = getWinnings(decks);
        System.out.println("Part 1: " + winnings);
    }

    public void solveSecond(List<String> lines) {
        List<Deck> decks = getDecks(lines);
        Deck.withJokers = true;
        int winnings = getWinnings(decks);
        System.out.println("Part 2: " + winnings);
    }

    private List<Deck> getDecks(List<String> lines) {
        List<Deck> decks = new ArrayList<>();
        for (String line : lines) {
            String unparsedCards = line.split(" ")[0];
            int bidAmount = Integer.parseInt(line.split(" ")[1]);
            decks.add(new Deck(unparsedCards.toCharArray(), bidAmount));
        }
        return decks;
    }

    private int getWinnings(List<Deck> decks) {
        decks.sort(Deck::compareTo);
        int winnings = 0;
        for (int i = 0; i < decks.size(); i++) {
            Deck deck = decks.get(i);
            winnings += deck.getBidAmount() * (i + 1);
        }
        return winnings;
    }
}
