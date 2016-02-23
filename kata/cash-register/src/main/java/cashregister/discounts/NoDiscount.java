package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class NoDiscount extends Discount {
    public NoDiscount(int priority) {
        super(priority);
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        return lineItem.price();
    }
}
