package fbw;

import java.util.Objects;

public abstract class Replacer {

    public static final String NOT_REPLACED = "this replacer can not replace the number";

    protected final int patternNumber;
    protected final String word;

    private Replacer next;

    public Replacer(int patternNumber, String word) {
        this.patternNumber = patternNumber;
        this.word = word;
    }

    public Replacer chain(Replacer node) {
        if (this.next != null) {
            this.next.chain(node);
        } else {
            this.next = node;
        }
        return this;
    }

    public String replace(int number) {
        String replaceResult = this.tryReplace(number);
        if (Objects.equals(replaceResult, NOT_REPLACED)) {
            return this.next.replace(number);
        } else {
            return replaceResult;
        }
    }

    public abstract String tryReplace(int number);
}
