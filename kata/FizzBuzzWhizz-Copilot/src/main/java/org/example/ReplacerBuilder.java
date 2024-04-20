package org.example;

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

    public ReplacerBuilder replaceThenStop(String word) {
        return replaceWith(word, true);
    }

    public ReplacerBuilder replaceThenContinue(String word) {
        return replaceWith(word, false);
    }

    private ReplacerBuilder replaceWith(String word, boolean stop) {
        if (isBuildingDivisible) {
            appendToChain(new DivisibleReplacer(number, word, stop));
            isBuildingDivisible = false;
        } else if (isBuildingInclusion) {
            appendToChain(new InclusionReplacer(number, word, stop));
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
