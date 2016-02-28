package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;
import cashregister.models.viewmodels.PlainTextViewModel;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoDiscountTest {

    @Test
    public void shouldConcatLineOutput() throws Exception {
        NoDiscount noDiscount = new NoDiscount(0);

        double price = noDiscount.discountedPrice(new OrderLineItem(new Product("abc", "ItemXXX", 10d, "bottle", null), 10), new PlainTextViewModel());

        assertThat(price, is(100d));
    }
}