package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class PercentageDiscount extends Discount {
    private double percentage;

    public PercentageDiscount(double percentage, int priority) {
        super(priority);
        this.percentage = percentage;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        return lineItem.price() * percentage;
    }

    @Override
    public String output(OrderLineItem lineItem) {
        return super.output(lineItem)
                + "，节省" + decimalFormat.format(lineItem.price() - this.discountedPrice(lineItem)) + "(元)";
    }

}
