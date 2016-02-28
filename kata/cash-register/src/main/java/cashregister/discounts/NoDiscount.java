package cashregister.discounts;

import cashregister.models.OrderLineItem;

public class NoDiscount extends Discount {

    private static NoDiscount noDiscount = new NoDiscount(-100);//没有打折是 最低/默认 优先级

    public NoDiscount(int priority) {
        super(priority);
    }

    public static NoDiscount noDiscount() {
        return noDiscount;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        return lineItem.price();
    }
}
