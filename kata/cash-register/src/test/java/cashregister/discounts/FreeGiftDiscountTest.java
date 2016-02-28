package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;
import cashregister.models.viewmodels.PlainTextViewModel;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FreeGiftDiscountTest {
    @Test
    public void shouldCalculateDiscountByAmountBought() throws Exception {
        FreeGiftDiscount freeGiftDiscount = new FreeGiftDiscount(2, 1, 0);//buy 2 get 1 free

        Double price = freeGiftDiscount.discountedPrice(new OrderLineItem(new Product("xyz", "ItemXXX", 50d, "pill", null), 10), new PlainTextViewModel());

        assertThat(price, is(350d));
    }

    @Test
    public void shouldCalculateDiscountByAmountBoughtWithOddNumbers() throws Exception {//odd number: 买几送几都行
        FreeGiftDiscount freeGiftDiscount = new FreeGiftDiscount(7, 3, 0);//买7送3

        Double price = freeGiftDiscount.discountedPrice(new OrderLineItem(new Product("xyz", "ItemXXX", 6d, "piece", null), 13), new PlainTextViewModel());

        assertThat(price, is(60d));//买7送3,前10个7*6=42,剩下3个还要单独付款18,共计60
    }
}