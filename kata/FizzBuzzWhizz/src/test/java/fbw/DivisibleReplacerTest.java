package fbw;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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