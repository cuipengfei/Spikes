package org.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InclusionReplacerTest {
    @Test
    public void should_replace_when_number_includes_pattern_number() throws Exception {
        InclusionReplacer replacer = new InclusionReplacer(3, "Fizz");

        String word = replacer.replace(3);
        assertThat(word, is("Fizz"));

        word = replacer.replace(13);
        assertThat(word, is("Fizz"));
    }
}