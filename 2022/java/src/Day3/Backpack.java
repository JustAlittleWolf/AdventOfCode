package Day3;

public class Backpack {
    private final String firstCompartment;
    private final String secondCompartment;
    private final String totalContent;
    public Backpack(String contents) {
        this.totalContent = contents;
        this.firstCompartment = contents.substring(0, contents.length() / 2);
        this.secondCompartment = contents.substring(contents.length() / 2);
    }

    public char getDuplicateItem() {
        for(char curChar : firstCompartment.toCharArray()) {
            if(secondCompartment.indexOf(curChar) != -1) return curChar;
        }
        return firstCompartment.charAt(0);
    }

    public static char getCommonItem(Backpack first, Backpack second, Backpack third) {
        for(char curChar : first.totalContent.toCharArray()) {
            if(second.totalContent.indexOf(curChar) != -1 && third.totalContent.indexOf(curChar) != -1) return curChar;
        }
        return first.totalContent.charAt(0);
    }
}
