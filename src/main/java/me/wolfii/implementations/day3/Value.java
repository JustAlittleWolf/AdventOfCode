package me.wolfii.implementations.day3;

import java.util.List;

public record Value(int value, int startX, int endX, int y) {
    boolean isValid(List<Symbol> symbols) {
        for (Symbol symbol : symbols) {
            if (Math.abs(y - symbol.y()) > 1) continue;
            if ((startX <= symbol.x() - 1 && endX >= symbol.x() + 1) || Math.abs(startX - symbol.x()) <= 1 || Math.abs(endX - symbol.x()) <= 1)
                return true;
        }
        return false;
    }
}
