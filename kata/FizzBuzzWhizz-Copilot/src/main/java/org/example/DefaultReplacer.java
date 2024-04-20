package org.example;

public class DefaultReplacer extends Replacer {
    public DefaultReplacer() {
        this(0, "");
    }

    public DefaultReplacer(int patternNumber, String word) {
        super(patternNumber, word);
    }

    @Override
    protected String tryReplace(int number, String appendableResult) {
        if (appendableResult.isEmpty()) {
            return Integer.toString(number);
        }
        return appendableResult;
    }
}
