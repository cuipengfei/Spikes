package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class PercentageDiscount extends Discount {
    private double percentage;

    public PercentageDiscount(double percentage, int priority) {
        super(priority);
        this.percentage = percentage;
    }

    @Override
    public Double price(OrderLineItem lineItem) {
        return lineItem.price() * percentage;
    }
}
