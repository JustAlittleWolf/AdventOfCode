package Day20;

import java.util.ArrayList;
import java.util.Arrays;

public class NumberList {
    private final ArrayList<Number> arrayList;
    public NumberList(Number[] input) {
        arrayList = new ArrayList<>(Arrays.asList(input));
    }

    public void shift() {
        for (int startIndex = 0; startIndex < arrayList.size(); startIndex++) {
            // GOOD IMPLEMENTATION MMMM
            shift(getIndexOf(startIndex));
        }
    }

    private int getIndexOf(int startIndex) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).startIndex == startIndex) return i;
        }
        System.out.println("ERROR, didnt find element");
        return -1;
    }

    public void shift(int index) {
        Number num = arrayList.get(index);
        arrayList.remove(index);
        index = (int) ((index + (num.value% arrayList.size()) + arrayList.size()) % arrayList.size());
        if (index == 0 && num.value < 0) index = arrayList.size();
        arrayList.add(index, num);
    }

    public long getGroveCoordinates() {
        int zeroIndex = getIndexOfZero();
        return getValue(1000 + zeroIndex) + getValue(2000 + zeroIndex) + getValue(3000 + zeroIndex);
    }

    private long getValue(int index) {
        return arrayList.get(index % arrayList.size()).value;
    }

    private int getIndexOfZero() {
        for(int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).value == 0) return i;
        }
        return -1;
    }
}
