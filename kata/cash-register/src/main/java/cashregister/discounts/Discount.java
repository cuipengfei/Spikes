package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

import java.text.DecimalFormat;

public abstract class Discount {

    private int priority;

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public Discount(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public abstract Double discountedPrice(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel);

    protected String lineSummary(OrderLineItem lineItem, double discountedPrice) {
        return lineItem.toString() + "，小计：" + DECIMAL_FORMAT.format(discountedPrice) + "(元)";
    }
}
