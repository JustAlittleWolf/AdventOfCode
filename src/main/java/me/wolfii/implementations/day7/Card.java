package me.wolfii.implementations.day7;

public class Card {
    public static final int JOKER = 1;
    int value;

    Card(char name, boolean withJokers) {
        if (Character.isDigit(name)) {
            this.value = Character.getNumericValue(name);
            return;
        }
        this.value = switch (name) {
            case 'T' -> 10;
            case 'J' -> withJokers ? JOKER : 11;
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
