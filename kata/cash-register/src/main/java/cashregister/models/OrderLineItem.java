package cashregister.models;

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
}
