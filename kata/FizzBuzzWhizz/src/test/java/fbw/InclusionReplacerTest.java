package fbw;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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