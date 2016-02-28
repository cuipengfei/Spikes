package cashregister.models;

import static cashregister.discounts.Discount.DECIMAL_FORMAT;

public class OrderLineItem {

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

    @Override
    public String toString() {
        return "名称：" + product().name()
                + "，数量：" + amount() + product().unit()
                + "，单价：" + DECIMAL_FORMAT.format(product().singleUnitPrice()) + "(元)";
    }
}
