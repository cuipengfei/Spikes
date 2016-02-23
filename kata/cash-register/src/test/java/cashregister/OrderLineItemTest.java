package cashregister;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderLineItemTest {
    @Test
    public void shouldCalculateItsOwnPrice() throws Exception {
        OrderLineItem orderLineItem = new OrderLineItem(new Product("a product", "ItemXXX", 5d, "meter", null), 5);

        Double linePrice = orderLineItem.price();

        assertThat(linePrice, is(25d));
    }
}