package cashregister;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReceiptPrinterIntegrationTest {
    @Test
    public void shouldPrintReceipt() throws Exception {
        String receiptString = ReceiptPrinter.processOrder("[" +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000002-2'," +
                " 'ITEM000003'," +
                " 'ITEM000003'," +
                " 'ITEM000003'" +
                "]");

        assertThat(receiptString, is("***<没钱赚商店>购物清单***\n" +
                "名称：可口可乐，数量：5瓶，单价：3.00(元)，小计：14.25(元)，节省0.75(元)\n" +
                "名称：羽毛球，数量：2个，单价：1.00(元)，小计：2.00(元)\n" +
                "名称：苹果，数量：3斤，单价：5.50(元)，小计：11.00(元)\n" +
                "----------------------\n" +
                "总计: 27.25(元)\n" +
                "节省：6.25(元)\n" +
                "**********************"));
    }
}