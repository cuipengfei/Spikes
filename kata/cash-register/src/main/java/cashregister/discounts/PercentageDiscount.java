package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

public class PercentageDiscount extends Discount {
    private double percentage;

    public PercentageDiscount(double percentage, int priority) {
        super(priority);
        this.percentage = percentage;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel) {
        double discountedPrice = lineItem.price() * percentage;

        plainTextViewModel.addToOriginalTotal(lineItem.price());
        plainTextViewModel.addToDiscountedTotal(discountedPrice);
        plainTextViewModel.addToLinesSection(lineItem.toString() + "，小计：" + decimalFormat.format(discountedPrice) + "(元)" + "，节省" + decimalFormat.format(lineItem.price() - discountedPrice) + "(元)");

        return discountedPrice;
    }
}
