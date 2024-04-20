package org.example;

public class InclusionReplacer extends Replacer {
    public InclusionReplacer(int patternNumber, String word) {
        super(patternNumber, word);
    }

    @Override
    protected String tryReplace(int number, String appendableResult) {
        boolean notReplacedYet = appendableResult.length() == 0;
        boolean isMatched = Integer.toString(number).contains(Integer.toString(patternNumber));

        if (notReplacedYet && isMatched) {
            return word;
        }

        return appendableResult;
    }
}
