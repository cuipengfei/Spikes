package fbw;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ReplacerTest {

    static class StubReplacer extends Replacer {
        public StubReplacer() {
            super(0, "fake string");
        }

        public StubReplacer(int patternNumber, String word) {
            super(patternNumber, word);
        }

        @Override
        public String tryReplace(int number) {
            return NOT_REPLACED;
        }
    }

    @Test
    public void should_connect_replacers_in_a_chain_and_call_in_order() throws Exception {
        //given
        Replacer replacer1 = Mockito.spy(StubReplacer.class);
        Replacer replacer2 = Mockito.spy(StubReplacer.class);
        Replacer replacer3 = Mockito.spy(StubReplacer.class);

        when(replacer3.tryReplace(123)).thenReturn("some word");

        //when
        replacer1.chain(replacer2).chain(replacer3);
        String word = replacer1.replace(123);

        //then
        assertThat(word, is("some word"));
        Mockito.verify(replacer1).tryReplace(123);
        Mockito.verify(replacer2).tryReplace(123);
        Mockito.verify(replacer3).tryReplace(123);
    }
}