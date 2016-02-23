package cashregister;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PercentageDiscountTest {
    @Test
    public void shouldCalculateDiscountByPercentage() throws Exception {
        PercentageDiscount percentageDiscount = new PercentageDiscount(0.95);

        Double price = percentageDiscount.price(OrderLineItem.create(Product.create("abc", 123d), 10));

        assertThat(price, is(1168.5d));
    }
}