package cashregister;

import cashregister.discounts.Discount;
import cashregister.io.InputParser;
import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

import java.util.List;

public final class ReceiptPrinter {
    public static String processOrder(String json) {
        List<OrderLineItem> orderLineItems = InputParser.parse(json);
        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();

        for (OrderLineItem orderLineItem : orderLineItems) {
            Discount discount = findSuitableDiscount(orderLineItem);
            discount.discountedPrice(orderLineItem, plainTextViewModel);
        }

        return plainTextViewModel.toString();
    }

    private static Discount findSuitableDiscount(OrderLineItem orderLineItem) {
        return orderLineItem.product().appliedDiscounts().stream()
                .sorted((x, y) -> y.priority() - x.priority())
                .findFirst().get();
    }

}
