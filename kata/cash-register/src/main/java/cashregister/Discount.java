package cashregister;

public abstract class Discount {
    public abstract Double price(OrderLineItem lineItem);

    protected int savedByProducts(OrderLineItem lineItem) {
        return (int) (savedByPrice(lineItem) / lineItem.product().singleUnitPrice());
    }

    protected Double savedByPrice(OrderLineItem lineItem) {
        return lineItem.price() - this.price(lineItem);
    }
}
