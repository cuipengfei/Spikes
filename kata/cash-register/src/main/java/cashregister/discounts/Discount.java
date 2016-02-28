package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

import java.text.DecimalFormat;

public abstract class Discount {

    private int priority;

    protected DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public Discount(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public abstract Double discountedPrice(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel);

    protected String lineSummary(OrderLineItem lineItem, double discountedPrice) {
        return lineItem.toString() + "，小计：" + decimalFormat.format(discountedPrice) + "(元)";
    }
}
