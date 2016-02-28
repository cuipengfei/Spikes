package cashregister;

import cashregister.discounts.Discount;
import cashregister.io.ConfigReader;
import cashregister.io.InputParser;
import cashregister.models.OrderLineItem;

import java.text.DecimalFormat;
import java.util.List;

public final class ReceiptPrinter {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static String newLine = System.getProperty("line.separator");

    public static String processOrder(String json) {
        List<OrderLineItem> orderLineItems = InputParser.parse(json);

        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        LinesSum linesSum = processLineItems(stringBuilder, orderLineItems);
        discountSummary(stringBuilder);
        finalSummary(stringBuilder, linesSum);

        return stringBuilder.toString();
    }

    //消费可变状态,不安全
    private static void discountSummary(StringBuilder stringBuilder) {
        for (Discount discount : ConfigReader.discounts()) {
            String discountSummary = discount.outputDiscountSummary();
            if (discountSummary.length() > 0) {
                stringBuilder.append(discountSummary);
            }
        }
    }

    private static void finalSummary(StringBuilder stringBuilder, LinesSum linesSum) {
        stringBuilder.append("----------------------")
                .append(newLine)
                .append("总计: " + decimalFormat.format(linesSum.sumDiscountPrice) + "(元)")
                .append(newLine)
                .append("节省：" + decimalFormat.format(linesSum.sumOriginalPrice - linesSum.sumDiscountPrice) + "(元)")
                .append(newLine)
                .append("**********************");
    }

    private static LinesSum processLineItems(StringBuilder stringBuilder, List<OrderLineItem> orderLineItems) {
        LinesSum linesSum = new LinesSum();
        for (OrderLineItem orderLineItem : orderLineItems) {
            Discount discount = findSuitableDiscount(orderLineItem);
            linesSum.sumDiscountPrice += discount.discountedPrice(orderLineItem);
            linesSum.sumOriginalPrice += orderLineItem.price();
            stringBuilder.append(newLine)
                    .append(discount.output(orderLineItem));
        }
        stringBuilder.append(newLine);
        return linesSum;
    }

    private static Discount findSuitableDiscount(OrderLineItem orderLineItem) {
        return orderLineItem.product().appliedDiscounts().stream()
                .sorted((x, y) -> y.priority() - x.priority())
                .findFirst().get();
    }

    private static class LinesSum {
        public Double sumDiscountPrice = 0d;
        public Double sumOriginalPrice = 0d;
    }
}
