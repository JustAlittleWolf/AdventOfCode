package me.wolfii.implementations.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hand {
    private final boolean withJokers;
    private final List<Card> cards = new ArrayList<>();
    public final int bidAmount;
    private final HandType handType;

    public Hand(char[] cards, int bidAmount, boolean withJokers) {
        this.withJokers = withJokers;
        this.bidAmount = bidAmount;
        for (char card : cards) this.cards.add(new Card(card, withJokers));
        this.handType = getHandType();
    }

    public int compareTo(Hand other) {
        if (handType.points != other.handType.points) {
            return handType.points - other.handType.points;
        }

        for (int card = 0; card < cards.size(); card++) {
            int myCard = cards.get(card).value;
            int otherCard = other.cards.get(card).value;
            if (myCard != otherCard) return myCard - otherCard;
        }
        return 0;
    }

    private HandType getHandType() {
        return switch (getDuplicateMaxCount()) {
            case 2 -> isTwoPair() ? HandType.TWO_PAIR : HandType.ONE_PAIR;
            case 3 -> isFullHouse() ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
            case 4 -> HandType.FOUR_OF_A_KIND;
            case 5 -> HandType.FIVE_OF_A_KIND;
            default -> HandType.HIGH_CARD;
        };
    }

    private boolean isTwoPair() {
        HashMap<Integer, Integer> cardCounts = getCardCounts();
        int numPairs = 0;
        for (int count : cardCounts.values()) {
            if (count == 2) numPairs++;
        }
        return numPairs == 2;
    }

    private boolean isFullHouse() {
        HashMap<Integer, Integer> cardCounts = getCardCounts();
        boolean hasPair = false;
        boolean hasTriple = false;
        for (int count : cardCounts.values()) {
            if (count == 2) hasPair = true;
            if (count == 3) hasTriple = true;
        }
        return hasTriple && hasPair;
    }

    private int getDuplicateMaxCount() {
        HashMap<Integer, Integer> cardCounts = getCardCounts();
        int duplicateMaxCount = 0;
        for (int count : cardCounts.values()) {
            duplicateMaxCount = Math.max(count, duplicateMaxCount);
        }
        return duplicateMaxCount;
    }

    private HashMap<Integer, Integer> getCardCounts() {
        HashMap<Integer, Integer> cardCounts = new HashMap<>();
        for (Card card : cards) {
            int currentCount = cardCounts.getOrDefault(card.value, 0);
            cardCounts.put(card.value, currentCount + 1);
        }
        if (withJokers) {
            int numJokers = cardCounts.getOrDefault(Card.JOKER, 0);
            cardCounts.remove(Card.JOKER);

            int index = 14;
            int max = 0;
            for (int i : cardCounts.keySet()) {
                int count = cardCounts.get(i);
                if (count > max) {
                    index = i;
                    max = count;
                }
            }
            int currentCount = cardCounts.getOrDefault(index, 0);
            cardCounts.put(index, currentCount + numJokers);
        }
        return cardCounts;
    }
}
