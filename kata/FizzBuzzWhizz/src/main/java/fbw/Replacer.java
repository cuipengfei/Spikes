package fbw;

public abstract class Replacer {

    public static final String NOT_REPLACED = "this replacer can not replace the number";

    protected final int patternNumber;
    protected final String word;

    public Replacer(int patternNumber, String word) {
        this.patternNumber = patternNumber;
        this.word = word;
    }

    public abstract String tryReplace(int number);
}
