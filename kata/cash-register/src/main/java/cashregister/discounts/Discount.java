package cashregister.discounts;

import cashregister.models.OrderLineItem;

import java.text.DecimalFormat;

public abstract class Discount {

    private int priority;

    public Discount(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }

    public abstract Double price(OrderLineItem lineItem);

    public int savedByProducts(OrderLineItem lineItem) {
        return (int) (savedByPrice(lineItem) / lineItem.product().singleUnitPrice());
    }

    public Double savedByPrice(OrderLineItem lineItem) {
        return lineItem.price() - this.price(lineItem);
    }

    public String output(OrderLineItem lineItem) {
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");
        return "名称：" + lineItem.product().name()
                + "，数量：" + lineItem.amount() + lineItem.product().unit()
                + "，单价：" + decimalFormat.format(lineItem.product().singleUnitPrice()) + "(元)"
                + "，小计：" + decimalFormat.format(price(lineItem)) + "(元)";
    }
}
