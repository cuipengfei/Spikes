package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

public class NoDiscount extends Discount {

    private static NoDiscount noDiscount = new NoDiscount(-100);//没有打折是 最低/默认 优先级

    public NoDiscount(int priority) {
        super(priority);
    }

    public static NoDiscount noDiscount() {
        return noDiscount;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel) {
        Double price = lineItem.price();

        plainTextViewModel.addToOriginalTotal(price);
        plainTextViewModel.addToDiscountedTotal(price);
        plainTextViewModel.addToLinesSection(lineItem.toString() + "，小计：" + decimalFormat.format(price) + "(元)");

        return price;
    }
}
