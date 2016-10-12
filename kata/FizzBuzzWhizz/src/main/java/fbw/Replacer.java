package fbw;

public abstract class Replacer {

    protected final int patternNumber;
    protected final String word;

    public Replacer(int patternNumber, String word) {
        this.patternNumber = patternNumber;
        this.word = word;
    }

    public abstract String tryReplace(int number);
}
