package cashregister.models;

import cashregister.discounts.Discount;

import java.text.DecimalFormat;

public class OrderLineItem {
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private final Product product;
    private final int amount;

    public OrderLineItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }

    public Product product() {
        return product;
    }

    public Double price() {
        return product.singleUnitPrice() * amount;
    }

    public String toString() {
        return "名称：" + product().name()
                + "，数量：" + amount() + product().unit()
                + "，单价：" + decimalFormat.format(product().singleUnitPrice()) + "(元)";
    }
}
