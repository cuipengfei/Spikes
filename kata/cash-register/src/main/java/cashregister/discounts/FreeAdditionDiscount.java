package cashregister.discounts;

import cashregister.models.OrderLineItem;

import java.util.HashMap;
import java.util.Map;

public class FreeAdditionDiscount extends Discount {
    private static String newLine = System.getProperty("line.separator");
    private final int bought;
    private final int freeAdditional;
    private Map<String, String> discountCache = new HashMap<>();

    public FreeAdditionDiscount(int bought, int freeAddition, int priority) {
        super(priority);
        this.bought = bought;
        this.freeAdditional = freeAddition;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        Double originalPrice = lineItem.price();
        int numberOfSets = lineItem.amount() / (bought + freeAdditional);
        double discountedPrice = originalPrice - numberOfSets * freeAdditional * lineItem.product().singleUnitPrice();
        saveCache(lineItem, originalPrice, discountedPrice);
        return discountedPrice;
    }

    @Override
    public String outputDiscountSummary() {
        if (discountCache.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("----------------------");
            stringBuilder.append(newLine).append("买二赠一商品：");//todo: bought, freeAddition 转汉字
            for (String key : discountCache.keySet()) {
                stringBuilder.append(newLine)
                        .append("名称：" + key + "，数量：" + discountCache.get(key))
                        .append(newLine);
            }
            return stringBuilder.toString();
        } else {
            return super.outputDiscountSummary();
        }
    }

    private void saveCache(OrderLineItem lineItem, Double originalPrice, double discountedPrice) {
        if (originalPrice != discountedPrice) {
            int savedByProducts = (int) ((originalPrice - discountedPrice) / lineItem.product().singleUnitPrice());
            discountCache.put(lineItem.product().name(), savedByProducts + lineItem.product().unit());
        }
    }
}
