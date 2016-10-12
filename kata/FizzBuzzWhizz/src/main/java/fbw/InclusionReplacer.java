package fbw;

public class InclusionReplacer extends Replacer {
    public InclusionReplacer(int patternNumber, String word) {
        super(patternNumber, word);
    }

    @Override
    public String tryReplace(int number) {
        if (Integer.toString(number).contains(Integer.toString(patternNumber))) {
            return word;
        }
        return NOT_REPLACED;
    }
}
