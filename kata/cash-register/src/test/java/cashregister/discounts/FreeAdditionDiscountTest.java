package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FreeAdditionDiscountTest {
    @Test
    public void shouldCalculateDiscountByAmountBought() throws Exception {
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(2, 1, 0);//buy 2 get 1 free

        Double price = freeAdditionDiscount.discountedPrice(new OrderLineItem(new Product("xyz", "ItemXXX", 50d, "pill", null), 10));

        assertThat(price, is(350d));
    }

    @Test
    public void shouldCalculateDiscountByAmountBoughtWithOddNumbers() throws Exception {//odd number: 买几送几都行
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(7, 3, 0);//买7送3

        Double price = freeAdditionDiscount.discountedPrice(new OrderLineItem(new Product("xyz", "ItemXXX", 6d, "piece", null), 13));

        assertThat(price, is(60d));//买7送3,前10个7*6=42,剩下3个还要单独付款18,共计60
    }

    @Test
    public void shouldConcatDiscountSummary() throws Exception {
        FreeAdditionDiscount freeAdditionDiscount = new FreeAdditionDiscount(2, 1, 0);//buy 2 get 1 free

        freeAdditionDiscount.discountedPrice(new OrderLineItem(new Product("xyz", "ItemXXX", 50d, "kilogram", null), 10));
        String discountSummary = freeAdditionDiscount.outputDiscountSummary();

        assertThat(discountSummary, is("----------------------\n" +
                "买二赠一商品：\n" +
                "名称：xyz，数量：3kilogram"));
    }
}