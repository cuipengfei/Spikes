package fbw;

public class DivisibleReplacer extends Replacer {

    public DivisibleReplacer(int dividend, String word) {
        super(dividend, word);
    }

    @Override
    public String tryReplace(int number) {
        if (number % patternNumber == 0) {
            return word;
        }
        return null;
    }

}
