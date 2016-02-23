package cashregister;

public interface Discount {
    Double price(OrderLineItem lineItem);
}
