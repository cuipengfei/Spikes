package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

import java.util.HashMap;
import java.util.Map;

public class FreeGiftDiscount extends Discount {
    private static String newLine = System.getProperty("line.separator");

    private final int bought;//买几个
    private final int freeGift;//送几个

    private Map<String, String> discountCache = new HashMap<>();//可变状态,不安全

    public FreeGiftDiscount(int bought, int freeGift, int priority) {
        super(priority);
        this.bought = bought;
        this.freeGift = freeGift;
    }

    @Override
    public Double discountedPrice(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel) {
        Double originalPrice = lineItem.price();

        int numberOfSets = lineItem.amount() / (bought + freeGift);
        int numberOfFreeGift = numberOfSets * freeGift;

        double freeGiftWorth = numberOfFreeGift * lineItem.product().singleUnitPrice();
        double discountedPrice = originalPrice - freeGiftWorth;

        appendToViewModel(lineItem, plainTextViewModel, originalPrice, discountedPrice);

        return discountedPrice;
    }

    @Override
    public String outputDiscountSummary() {
        if (discountCache.size() == 0) {
            return super.outputDiscountSummary();
        } else {
            StringBuilder stringBuilder = new StringBuilder("----------------------");
            stringBuilder.append(newLine).append("买二赠一商品：");//写死了 有必要的话 买几送几 转汉字
            for (String key : discountCache.keySet()) {
                stringBuilder.append(newLine)
                        .append("名称：" + key + "，数量：" + discountCache.get(key))
                        .append(newLine);
            }
            return stringBuilder.toString();
        }
    }

    private void appendToViewModel(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel, Double originalPrice, double discountedPrice) {
        plainTextViewModel.addToOriginalTotal(originalPrice);
        plainTextViewModel.addToDiscountedTotal(discountedPrice);
        plainTextViewModel.addToLinesSection(lineItem.toString() + "，小计：" + decimalFormat.format(discountedPrice) + "(元)");
        int savedByProducts = (int) ((originalPrice - discountedPrice) / lineItem.product().singleUnitPrice());
        if (savedByProducts > 0) {
            plainTextViewModel.addToSection("买二赠一商品：", "名称：" + lineItem.product().name() + "，数量：" + savedByProducts + lineItem.product().unit());//写死了 有必要的话 买几送几 转汉字
        }
    }

}
