package cashregister;

import cashregister.discounts.Discount;
import cashregister.discounts.NoDiscount;
import cashregister.io.ConfigReader;
import cashregister.io.InputParser;
import cashregister.models.OrderLineItem;

import java.util.List;

public final class ReceiptPrinter {

    private static String newLine = System.getProperty("line.separator");

    public static String processOrder(String json) {
        List<OrderLineItem> orderLineItems = InputParser.parse(json);
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");

        LinesSum linesSum = processLineItems(stringBuilder, orderLineItems);
        discountSummary(stringBuilder);
        finalSummary(stringBuilder, linesSum);
        return stringBuilder.toString();
    }

    private static void discountSummary(StringBuilder stringBuilder) {
        for (Discount discount : ConfigReader.discounts()) {
            stringBuilder.append(discount.outputDiscountSummary());
        }
    }

    private static void finalSummary(StringBuilder stringBuilder, LinesSum linesSum) {
        stringBuilder.append(newLine)
                .append("----------------------")
                .append(newLine)
                .append("总计: " + linesSum.sumDiscountPrice + "(元)")
                .append(newLine)
                .append("节省：" + (linesSum.sumOriginalPrice - linesSum.sumDiscountPrice) + "(元)")
                .append(newLine)
                .append("**********************");
    }

    private static LinesSum processLineItems(StringBuilder stringBuilder, List<OrderLineItem> parse) {
        LinesSum linesSum = new LinesSum();
        for (OrderLineItem orderLineItem : parse) {
            Discount discount = findSuitableDiscount(orderLineItem);
            linesSum.sumDiscountPrice += discount.discountedPrice(orderLineItem);
            linesSum.sumOriginalPrice += orderLineItem.price();
            stringBuilder.append(newLine)
                    .append(discount.output(orderLineItem));
        }
        stringBuilder.append(newLine)
                .append("----------------------")
                .append(newLine);
        return linesSum;
    }

    private static Discount findSuitableDiscount(OrderLineItem orderLineItem) {
        if (orderLineItem.product().appliedDiscounts().size() == 0) {
            return new NoDiscount(0);
        } else {
            return orderLineItem.product().appliedDiscounts().stream()
                    .sorted((x, y) -> y.priority() - x.priority())
                    .findFirst().get();
        }
    }

    private static class LinesSum {
        public Double sumDiscountPrice = 0d;
        public Double sumOriginalPrice = 0d;
    }
}
