package cashregister;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FreeAdditionDiscountTest {
    @Test
    public void shouldCalculateDiscountByAmountBought() throws Exception {
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(2, 1);//buy 2 get 1 free

        Double price = freeAdditionDiscount.price(OrderLineItem.create(Product.create("xyz", "ItemXXX", "pill", 50d), 10));

        assertThat(price, is(350d));
    }

    @Test
    public void shouldCalculateDiscountByAmountBoughtWithOddNumbers() throws Exception {//odd number: 买几送几都行
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(7, 3);//买7送3

        Double price = freeAdditionDiscount.price(OrderLineItem.create(Product.create("xyz", "ItemXXX", "piece", 6d), 13));

        assertThat(price, is(60d));//买7送3,前10个7*6=42,剩下3个还要单独付款18,共计60
    }

    @Test
    public void shouldCalculateSavedByProducts() throws Exception {
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(2, 1);//buy 2 get 1 free

        int saved = freeAdditionDiscount.savedByProducts(OrderLineItem.create(Product.create("xyz", "ItemXXX", "kilogram", 50d), 10));

        assertThat(saved, is(3));//相当于白送3个
    }
}