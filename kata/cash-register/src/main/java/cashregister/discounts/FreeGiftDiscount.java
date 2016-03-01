package cashregister.discounts;

import cashregister.models.OrderLineItem;
import cashregister.models.viewmodels.PlainTextViewModel;

public class FreeGiftDiscount extends Discount {

    private final int bought;//买几个
    private final int freeGift;//送几个

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

    private void appendToViewModel(OrderLineItem lineItem, PlainTextViewModel plainTextViewModel, Double originalPrice, double discountedPrice) {
        plainTextViewModel.addToOriginalTotal(originalPrice);
        plainTextViewModel.addToDiscountedTotal(discountedPrice);
        plainTextViewModel.addToLinesSection(lineSummary(lineItem, discountedPrice));
        int savedByProducts = (int) ((originalPrice - discountedPrice) / lineItem.product().singleUnitPrice());
        if (savedByProducts > 0) {
            //写死了 如果未来出现买n赠m的需求 可以把 买几送几 转汉字(或者写到配置里) 就可以了
            plainTextViewModel.addToSection("买二赠一商品：", "名称：" + lineItem.product().name() + "，数量：" + savedByProducts + lineItem.product().unit());
        }
    }

}
