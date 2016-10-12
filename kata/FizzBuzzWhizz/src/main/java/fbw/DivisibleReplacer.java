package fbw;

public class DivisibleReplacer {

    private final int dividend;
    private final String word;

    public DivisibleReplacer(int dividend, String word) {
        this.dividend = dividend;
        this.word = word;
    }

    public String tryReplace(int number) {
        if (number % dividend == 0) {
            return word;
        }
        return null;
    }
}
