package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class NoDiscount extends Discount {
    public NoDiscount(int priority) {
        super(priority);
    }

    @Override
    public Double price(OrderLineItem lineItem) {
        return lineItem.price();
    }
}
