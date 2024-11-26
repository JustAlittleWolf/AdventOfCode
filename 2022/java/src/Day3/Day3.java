package Day3;

import java.util.ArrayList;

public class Day3 {
    public static void solve(String input) {
        String[] lines = input.split("\n");
        ArrayList<Backpack> backpacks = new ArrayList<>();

        int duplicateSum = 0;
        for (String line : lines) {
            Backpack backpack = new Backpack(line);
            backpacks.add(backpack);
            duplicateSum += valueFromChar(backpack.getDuplicateItem());
        }
        int badgeSum = 0;
        for (int i = 0; i < backpacks.size(); i += 3) {
            Backpack firstBackpack = new Backpack(lines[i]);
            Backpack secondBackpack = new Backpack(lines[i + 1]);
            Backpack thirdBackpack = new Backpack(lines[i + 2]);
            badgeSum += valueFromChar(Backpack.getCommonItem(firstBackpack, secondBackpack, thirdBackpack));
        }
        System.out.println(duplicateSum);
        System.out.println(badgeSum);
    }

    private static int valueFromChar(char character) {
        return character - (character <= 90 ? 38 : 96);
    }
}
