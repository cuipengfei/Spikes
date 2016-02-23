package cashregister.discounts;

import cashregister.models.OrderLineItem;

public abstract class Discount {

    private int priority;

    public Discount(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public abstract Double price(OrderLineItem lineItem);

    protected int savedByProducts(OrderLineItem lineItem) {
        return (int) (savedByPrice(lineItem) / lineItem.product().singleUnitPrice());
    }

    protected Double savedByPrice(OrderLineItem lineItem) {
        return lineItem.price() - this.price(lineItem);
    }
}
