package cashregister;

public class OrderLineItem {
    private final Product product;
    private final int amount;

    public OrderLineItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public static OrderLineItem create(Product product, int amount) {
        return new OrderLineItem(product, amount);
    }

    public Double price() {
        return product.singleUnitPrice() * amount;
    }
}
