package fbw;

public class DoNothingReplacer extends Replacer {
    public DoNothingReplacer() {
        this(0, "");
    }

    public DoNothingReplacer(int patternNumber, String word) {
        super(patternNumber, word);
    }

    @Override
    public String tryReplace(int number) {
        return Integer.toString(number);
    }
}
