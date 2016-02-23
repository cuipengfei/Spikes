package cashregister.discounts;

import cashregister.models.OrderLineItem;

import java.text.DecimalFormat;

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
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");
        return super.output(lineItem)
                + "，节省" + decimalFormat.format(lineItem.price() - this.discountedPrice(lineItem)) + "(元)";
    }

}
