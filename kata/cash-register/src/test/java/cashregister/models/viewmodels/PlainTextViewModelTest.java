package cashregister.models.viewmodels;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlainTextViewModelTest {
    @Test
    public void shouldOutPutLinesSection() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToLinesSection("what ever line");

        String linesSectionInString = plainTextViewModel.toString();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "what ever line\n" +
                "----------------------\n" +
                "总计: 0.00(元)\n" +
                "节省：0.00(元)\n" +
                "**********************"));
    }

    @Test
    public void shouldOutPutDiscountSection() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToSection("whatever discount", "blah blah");

        String linesSectionInString = plainTextViewModel.toString();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "----------------------\n" +
                "whatever discount\n" +
                "blah blah\n" +
                "----------------------\n" +
                "总计: 0.00(元)\n" +
                "节省：0.00(元)\n" +
                "**********************"));
    }

    @Test
    public void shouldOutPutFinalSummary() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToLinesSection("apple");
        plainTextViewModel.addToOriginalTotal(10d);
        plainTextViewModel.addToDiscountedTotal(9d);
        plainTextViewModel.addToSection("something discount", "apple something");

        plainTextViewModel.addToLinesSection("orange");
        plainTextViewModel.addToOriginalTotal(10d);
        plainTextViewModel.addToDiscountedTotal(9d);
        plainTextViewModel.addToSection("something discount", "orange something");

        String linesSectionInString = plainTextViewModel.toString();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "apple\n" +
                "orange\n" +
                "----------------------\n" +
                "something discount\n" +
                "apple something\n" +
                "orange something\n" +
                "----------------------\n" +
                "总计: 18.00(元)\n" +
                "节省：2.00(元)\n" +
                "**********************"));
    }

    @Test
    public void plainTextOutputIntegrationTest() throws Exception {
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        plainTextViewModel.addToOriginalTotal(10d);
        plainTextViewModel.addToDiscountedTotal(9d);

        plainTextViewModel.addToOriginalTotal(10d);
        plainTextViewModel.addToDiscountedTotal(9d);

        String linesSectionInString = plainTextViewModel.toString();
        assertThat(linesSectionInString, is("***<没钱赚商店>购物清单***\n" +
                "----------------------\n" +
                "总计: 18.00(元)\n" +
                "节省：2.00(元)\n" +
                "**********************"));
    }
}