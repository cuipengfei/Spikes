package cashregister.models.viewmodels;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlainTextViewModelTest {
    @Test
    public void shouldOutPutLinesSection() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToLines("what ever line");

        String linesSectionInString = plainTextViewModel.output();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "what ever line\n" +
                "**********************"));
    }
}