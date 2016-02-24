package cashregister.discounts;

import cashregister.models.OrderLineItem;

import java.util.HashMap;
import java.util.Map;

public class FreeGiftDiscount extends Discount {
    private static String newLine = System.getProperty("line.separator");

    private final int bought;
    private final int freeGift;

    private Map<String, String> discountCache = new HashMap<>();//可变状态,不安全

    public FreeGiftDiscount(int bought, int freeGift, int priority) {
        super(priority);
        this.bought = bought;
        this.freeGift = freeGift;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem) {
        Double originalPrice = lineItem.price();

        int numberOfSets = lineItem.amount() / (bought + freeGift);
        int numberOfFreeGift = numberOfSets * freeGift;

        double freeGiftWorth = numberOfFreeGift * lineItem.product().singleUnitPrice();
        double discountedPrice = originalPrice - freeGiftWorth;

        saveCache(lineItem, originalPrice, discountedPrice);

        return discountedPrice;
    }

    @Override
    public String outputDiscountSummary() {
        if (discountCache.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("----------------------");
            stringBuilder.append(newLine).append("买二赠一商品：");//todo: 买几送几 转汉字
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
