package org.example;

public class InclusionReplacer extends Replacer {
    public InclusionReplacer(int patternNumber, String word) {
        super(patternNumber, word);
    }

    public InclusionReplacer(int patternNumber, String word, boolean stop) {
        super(patternNumber, word, stop);
    }

    @Override
    protected String tryReplace(int number, String appendableResult) {
        boolean isMatched = Integer.toString(number)
                .contains(Integer.toString(patternNumber));

        if (isMatched) {
            return word;
        }

        return appendableResult;
    }
}
