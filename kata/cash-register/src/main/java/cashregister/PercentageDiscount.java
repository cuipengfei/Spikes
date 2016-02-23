package cashregister;

public class PercentageDiscount extends Discount {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public Double price(OrderLineItem lineItem) {
        return lineItem.price() * percentage;
    }
}
