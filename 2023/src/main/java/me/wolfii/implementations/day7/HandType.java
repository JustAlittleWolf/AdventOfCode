package me.wolfii.implementations.day7;

public enum HandType {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0);

    final int points;

    HandType(int points) {
        this.points = points;
    }
}
