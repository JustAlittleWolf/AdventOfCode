package Day20;

public class Day20 {

    public static void solve(String input) {
        String[] lines = input.split("\n");
        Number[] values = new Number[lines.length];
        long decryptionKey = 1L;
        for (int i = 0; i < lines.length; i++) values[i] = new Number(Long.parseLong(lines[i]), i);
        NumberList numberList = new NumberList(values);
        numberList.shift();
        System.out.println(numberList.getGroveCoordinates());
        decryptionKey = 811589153L;
        for (int i = 0; i < lines.length; i++) values[i] = new Number(Long.parseLong(lines[i]) * decryptionKey, i);
        numberList = new NumberList(values);
        for (int i = 0; i < 10; i++) {
            numberList.shift();
        }
        System.out.println(numberList.getGroveCoordinates());
    }
}
