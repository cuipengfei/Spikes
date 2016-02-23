package cashregister;

public abstract class Discount {
    public abstract Double price(OrderLineItem lineItem);

    protected int savedByAmount() {
        return 0;
    }

    protected Double savedByPrice(OrderLineItem lineItem) {
        return lineItem.price() - this.price(lineItem);
    }
}
