package fbw;

public class ReplacerBuilder {

    private boolean isBuildingDivisible;
    private boolean isBuildingInclusion;

    private int number;
    private Replacer rootReplacer;

    public ReplacerBuilder whenDivisibleBy(int number) {
        this.number = number;
        isBuildingDivisible = true;
        return this;
    }

    public ReplacerBuilder whenIncludes(int number) {
        this.number = number;
        isBuildingInclusion = true;
        return this;
    }

    public ReplacerBuilder replaceWith(String word) {
        if (isBuildingDivisible) {
            appendToChain(new DivisibleReplacer(number, word));
            isBuildingDivisible = false;
        } else if (isBuildingInclusion) {
            appendToChain(new InclusionReplacer(number, word));
            isBuildingInclusion = false;
        }
        return this;
    }

    public Replacer build() {
        appendToChain(new DefaultReplacer());
        Replacer finalProduct = rootReplacer;
        rootReplacer = null;
        return finalProduct;
    }

    private void appendToChain(Replacer replacer) {
        if (rootReplacer == null) {
            rootReplacer = replacer;
        } else {
            rootReplacer.chain(replacer);
        }
    }
}
