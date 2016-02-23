package cashregister;

import cashregister.discounts.Discount;
import cashregister.discounts.NoDiscount;
import cashregister.io.InputParser;
import cashregister.models.OrderLineItem;

import java.util.ArrayList;
import java.util.List;

public final class ReceiptPrinter {

    private static String newLine = System.getProperty("line.separator");

    public static String processOrder(String json) {
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        List<OrderLineItem> orderLineItems = InputParser.parse(json);
        Double sumPrice = 0d;
        Double sumSaved = 0d;
        for (OrderLineItem orderLineItem : orderLineItems) {
            Discount discount = findSuitableDiscount(orderLineItem);
            sumPrice += discount.price(orderLineItem);
            sumSaved += discount.savedByPrice(orderLineItem);
            stringBuilder.append(newLine)
                    .append(discount.output(orderLineItem));
        }

        stringBuilder.append(newLine)
                .append("----------------------")
                .append(newLine)
                .append("总计: " + sumPrice + "(元)")
                .append(newLine)
                .append("节省：" + sumSaved + "(元)")
                .append(newLine)
                .append("**********************");
        return stringBuilder.toString();
    }

    private static Discount findSuitableDiscount(OrderLineItem orderLineItem) {
        List<Discount> discounts = new ArrayList<>();
        discounts.add(new NoDiscount(0));
        discounts.addAll(orderLineItem.product().appliedDiscounts());
        return discounts.stream()
                .sorted((x, y) -> y.priority() - x.priority())
                .findFirst().get();
    }
}
