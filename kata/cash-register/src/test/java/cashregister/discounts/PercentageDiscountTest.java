package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;
import cashregister.models.viewmodels.PlainTextViewModel;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PercentageDiscountTest {
    @Test
    public void shouldCalculateDiscountByPercentage() throws Exception {
        PercentageDiscount percentageDiscount = new PercentageDiscount(0.95, 0);

        Double price = percentageDiscount.discountedPrice(new OrderLineItem(new Product("abc", "ItemXXX", 123d, "bottle", null), 10), new PlainTextViewModel());

        assertThat(price, is(1168.5d));
    }
}