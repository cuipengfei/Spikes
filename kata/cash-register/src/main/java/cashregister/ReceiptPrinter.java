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
        StringBuilder stringBuilder = new StringBuilder("***<没钱赚商店>购物清单***");
        Summarizer summarizer = new Summarizer(json, stringBuilder).summarize();
        List<Discount> discounts = ConfigReader.discounts();
        for (Discount discount : discounts) {
            stringBuilder.append(discount.outputDiscountSummary());
        }
        finalSummary(stringBuilder, summarizer.sumDiscountPrice(), summarizer.sumOriginalPrice());
        return stringBuilder.toString();
    }

    private static void finalSummary(StringBuilder stringBuilder, Double sumDiscountPrice, Double sumOriginalPrice) {
        stringBuilder.append(newLine)
                .append("----------------------")
                .append(newLine)
                .append("总计: " + sumDiscountPrice + "(元)")
                .append(newLine)
                .append("节省：" + (sumOriginalPrice - sumDiscountPrice) + "(元)")
                .append(newLine)
                .append("**********************");
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

    private static class Summarizer {
        private String json;
        private StringBuilder stringBuilder;
        private Double sumDiscountPrice = 0d;
        private Double sumOriginalPrice = 0d;

        public Summarizer(String json, StringBuilder stringBuilder) {
            this.json = json;
            this.stringBuilder = stringBuilder;
        }

        public Double sumDiscountPrice() {
            return sumDiscountPrice;
        }

        public Double sumOriginalPrice() {
            return sumOriginalPrice;
        }

        public Summarizer summarize() {
            List<OrderLineItem> orderLineItems = InputParser.parse(json);
            for (OrderLineItem orderLineItem : orderLineItems) {
                Discount discount = findSuitableDiscount(orderLineItem);
                sumDiscountPrice += discount.discountedPrice(orderLineItem);
                sumOriginalPrice += orderLineItem.price();
                stringBuilder.append(newLine)
                        .append(discount.output(orderLineItem));
            }
            stringBuilder.append(newLine)
                    .append("----------------------")
                    .append(newLine);
            return this;
        }
    }
}
