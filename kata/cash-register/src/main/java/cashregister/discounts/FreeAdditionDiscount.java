package cashregister.discounts;

import cashregister.models.OrderLineItem;

import java.util.HashMap;
import java.util.Map;

public class FreeAdditionDiscount extends Discount {
    private static String newLine = System.getProperty("line.separator");
    private final int bought;
    private final int freeAdditional;
    private Map<String, String> discountCache = new HashMap<>();

    public FreeAdditionDiscount(int bought, int freeAdditional, int priority) {
        super(priority);
        this.bought = bought;
        this.freeAdditional = freeAdditional;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        Double originalPrice = lineItem.price();
        int numberOfSets = lineItem.amount() / (bought + freeAdditional);
        double discountedPrice = originalPrice - numberOfSets * freeAdditional * lineItem.product().singleUnitPrice();
        int savedByProducts = (int) ((originalPrice - discountedPrice) / lineItem.product().singleUnitPrice());
        discountCache.put(lineItem.product().name(), savedByProducts + lineItem.product().unit());
        return discountedPrice;
    }

    @Override
    public String outputDiscountSummary() {
        if (discountCache.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("买二赠一商品：");
            for (String key : discountCache.keySet()) {
                stringBuilder.append(newLine)
                        .append("名称：" + key +
                                "，数量：" + discountCache.get(key));
            }
            return stringBuilder.toString();
        } else {
            return super.outputDiscountSummary();
        }
    }
}
