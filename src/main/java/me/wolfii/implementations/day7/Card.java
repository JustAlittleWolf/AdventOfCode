package me.wolfii.implementations.day7;

public class Card {
    int value;

    Card(char name) {
        if (Character.isDigit(name)) {
            this.value = Character.getNumericValue(name);
            return;
        }
        this.value = switch (name) {
            case 'T' -> 10;
            case 'J' -> Deck.withJokers ? 1 : 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            case 'A' -> 14;
            default -> {
                System.out.println("Unidentified Card");
                yield 0;
            }
        };
    }
}
