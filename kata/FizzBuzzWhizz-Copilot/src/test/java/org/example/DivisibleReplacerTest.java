package org.example;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DivisibleReplacerTest {
    @Test
    public void should_replace_number_with_word_when_divisible() throws Exception {
        DivisibleReplacer divisibleReplacer = new DivisibleReplacer(3, "Fizz");

        String word = divisibleReplacer.replace(3);
        assertThat(word, is("Fizz"));

        word = divisibleReplacer.replace(6);
        assertThat(word, is("Fizz"));
    }
}