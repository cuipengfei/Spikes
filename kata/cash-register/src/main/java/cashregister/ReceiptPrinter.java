package cashregister;

import cashregister.discounts.Discount;
import cashregister.io.ConfigReader;
import cashregister.io.InputParser;
import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

import java.text.DecimalFormat;
import java.util.List;

public final class ReceiptPrinter {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static String newLine = System.getProperty("line.separator");

    public static String processOrder(String json) {
        List<OrderLineItem> orderLineItems = InputParser.parse(json);

        PlainTextViewModel plainTextViewModel = new PlainTextViewModel();
        for (OrderLineItem orderLineItem : orderLineItems) {
            Discount discount = findSuitableDiscount(orderLineItem);
            discount.discountedPrice(orderLineItem, plainTextViewModel);
        }

        return plainTextViewModel.output();
    }

    private static Discount findSuitableDiscount(OrderLineItem orderLineItem) {
        return orderLineItem.product().appliedDiscounts().stream()
                .sorted((x, y) -> y.priority() - x.priority())
                .findFirst().get();
    }

}
