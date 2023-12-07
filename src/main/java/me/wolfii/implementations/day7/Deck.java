package me.wolfii.implementations.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Deck {
    static boolean withJokers = false;
    private final List<Card> cards = new ArrayList<>();
    private final int bidAmount;
    private final HashMap<Boolean, HandType> cachedResults = new HashMap<>();
    public Deck(char[] cards, int bidAmount) {
        for(char card : cards) {
            this.cards.add(new Card(card));
        }
        this.bidAmount = bidAmount;
    }
    public int getBidAmount() {
        return bidAmount;
    }
    public int compareTo(Deck other) {
        HandType myHandType = getHandTypeCached();
        HandType otherHandType = other.getHandTypeCached();
        if (myHandType.points != otherHandType.points) {
            return myHandType.points - otherHandType.points;
        }

        for (int card = 0; card < cards.size(); card++) {
            int myCard = cards.get(card).value;
            int otherCard = other.cards.get(card).value;
            if (myCard != otherCard) return myCard - otherCard;
        }
        return 0;
    }

    private HandType getHandTypeCached() {
        HandType cachedResult = cachedResults.get(withJokers);
        if(cachedResult != null) return cachedResult;
        HandType result = getHandType();
        cachedResults.put(withJokers, result);
        return result;
    }

    private HandType getHandType() {
        int duplicateMaxCount = getDuplicateMaxCount();
        if (duplicateMaxCount == 5) return HandType.FIVE_OF_A_KIND;
        if (duplicateMaxCount == 4) return HandType.FOUR_OF_A_KIND;
        if (isFullHouse()) return HandType.FULL_HOUSE;
        if (duplicateMaxCount == 3) return HandType.THREE_OF_A_KIND;
        if (isTwoPair()) return HandType.TWO_PAIR;
        if (duplicateMaxCount == 2) return HandType.ONE_PAIR;
        return HandType.HIGH_CARD;
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
        HashMap<Integer, Integer> cardNumbers = new HashMap<>();
        for (Card card : cards) {
            int alreadyPresent = cardNumbers.getOrDefault(card.value, 0);
            cardNumbers.put(card.value, alreadyPresent + 1);
        }
        if (withJokers) {
            int numJokers = cardNumbers.getOrDefault(1, 0);
            cardNumbers.remove(1);

            int index = -1;
            int max = 0;
            for (int i : cardNumbers.keySet()) {
                int count = cardNumbers.get(i);
                if (count > max) {
                    index = i;
                    max = count;
                }
            }
            if(index == -1) index = 14;
            cardNumbers.put(index, cardNumbers.getOrDefault(index, 0) + numJokers);
        }
        return cardNumbers;
    }
}
