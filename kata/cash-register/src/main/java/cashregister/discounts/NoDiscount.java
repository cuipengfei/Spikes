package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class NoDiscount extends Discount {

    private static NoDiscount noDiscount = new NoDiscount(0);

    public NoDiscount(int priority) {
        super(priority);
    }

    public static NoDiscount noDiscount() {
        return noDiscount;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        return lineItem.price();
    }
}
