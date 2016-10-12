package fbw;

public class DivisibleReplacer extends Replacer {

    public DivisibleReplacer(int dividend, String word) {
        super(dividend, word);
    }

    @Override
    protected String tryReplace(int number, String appendableResult) {
        if (number % patternNumber == 0) {
            return appendableResult + word;
        }
        return appendableResult;
    }
}
