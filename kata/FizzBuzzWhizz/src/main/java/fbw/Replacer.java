package fbw;

public abstract class Replacer {

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
        return replace(number, "");
    }

    private String replace(int number, String appendableResult) {
        String replaceResult = tryReplace(number, appendableResult);
        if (this.next != null) {
            return this.next.replace(number, replaceResult);
        } else {
            return replaceResult;
        }
    }

    protected abstract String tryReplace(int number, String appendableResult);
}
