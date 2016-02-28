package cashregister.models.viewmodels;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlainTextViewModelTest {
    @Test
    public void shouldOutPutLinesSection() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToLinesSection("what ever line");

        String linesSectionInString = plainTextViewModel.output();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "what ever line\n" +
                "----------------------\n" +
                "**********************"));
    }

    @Test
    public void shouldOutPutDiscountSection() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.createSection("whatever discount");
        plainTextViewModel.addToSection("whatever discount", "blah blah");

        String linesSectionInString = plainTextViewModel.output();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "----------------------\n" +
                "whatever discount\n" +
                "blah blah\n" +
                "----------------------\n" +
                "**********************"));
    }
}