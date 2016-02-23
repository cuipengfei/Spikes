package cashregister;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FreeAdditionDiscountTest {
    @Test
    public void shouldCalculateDiscountByAmountBought() throws Exception {
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(2, 1);//buy 2 get 1 free

        Double price = freeAdditionDiscount.price(OrderLineItem.create(Product.create("xyz", 50d), 10));

        assertThat(price, is(350d));
    }
}