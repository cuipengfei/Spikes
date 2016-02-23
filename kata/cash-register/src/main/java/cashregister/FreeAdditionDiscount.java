package cashregister;

public class FreeAdditionDiscount extends Discount {
    private final int bought;
    private final int freeAdditional;

    public FreeAdditionDiscount(int bought, int freeAdditional) {
        this.bought = bought;
        this.freeAdditional = freeAdditional;
    }

    @Override
    public Double price(OrderLineItem lineItem) {
        Double priceWithoutDiscount = lineItem.price();
        int numberOfSets = lineItem.amount() / (bought + freeAdditional);
        return priceWithoutDiscount - numberOfSets * freeAdditional * lineItem.product().singleUnitPrice();
    }
}
