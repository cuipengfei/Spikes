package cashregister.discounts;

import cashregister.models.OrderLineItem;

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

    public abstract Double discountedPrice(OrderLineItem lineItem);

    public String output(OrderLineItem lineItem) {
        return "名称：" + lineItem.product().name()
                + "，数量：" + lineItem.amount() + lineItem.product().unit()
                + "，单价：" + decimalFormat.format(lineItem.product().singleUnitPrice()) + "(元)"
                + "，小计：" + decimalFormat.format(discountedPrice(lineItem)) + "(元)";
    }

    //就是这个设计导致了可变性,不好
    public String outputDiscountSummary() {
        return "";
    }
}
