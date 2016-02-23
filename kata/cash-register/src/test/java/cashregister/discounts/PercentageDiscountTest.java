package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PercentageDiscountTest {
    @Test
    public void shouldCalculateDiscountByPercentage() throws Exception {
        PercentageDiscount percentageDiscount = new PercentageDiscount(0.95, 0);

        Double price = percentageDiscount.price(new OrderLineItem(new Product("abc", "ItemXXX", 123d, "bottle", null), 10));

        assertThat(price, is(1168.5d));
    }

    @Test
    public void shouldConcatLineOutput() throws Exception {
        PercentageDiscount percentageDiscount = new PercentageDiscount(0.5, 0);

        String output = percentageDiscount.output(new OrderLineItem(new Product("abc", "ItemXXX", 10d, "bottle", null), 10));

        assertThat(output, is("名称：abc，数量：10bottle，单价：10.00(元)，小计：50.00(元)，节省50.00(元)"));
    }
}