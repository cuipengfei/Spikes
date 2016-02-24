package cashregister.io;

import cashregister.discounts.Discount;
import cashregister.discounts.FreeGiftDiscount;
import cashregister.discounts.PercentageDiscount;
import cashregister.models.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public final class ConfigReader {

    private static final String PREFIX = "Product.";
    private static List<Discount> discounts;//可变状态,不安全

    public static List<Product> loadProducts(String configPath) {
        discounts = null;
        Properties properties = loadProperties(configPath);
        return properties.stringPropertyNames().stream()
                .filter(ConfigReader::isProductNameLine)
                .map(ConfigReader::toProductCode)
                .map(productCode -> toProduct(productCode, properties))
                .collect(Collectors.toList());
    }

    public static List<Discount> discounts() {
        return discounts;
    }

    private static Product toProduct(String productCode, Properties properties) {
        String name = properties.getProperty(PREFIX + productCode + ".name");
        double price = Double.parseDouble(properties.getProperty(PREFIX + productCode + ".price"));
        String unit = properties.getProperty(PREFIX + productCode + ".unit");

        String appliedDiscountNames = properties.getProperty(PREFIX + productCode + ".appliedDiscounts", "");
        List<Discount> appliedDiscounts = loadDiscounts(properties).stream()
                .filter(discount -> appliedDiscountNames.contains(discount.getClass().getSimpleName()))
                .collect(Collectors.toList());

        return new Product(name, productCode, price, unit, appliedDiscounts);
    }

    private static List<Discount> loadDiscounts(Properties properties) {
        if (discounts == null) {
            double percentage = Double.parseDouble(properties.getProperty("PercentageDiscount.percentage"));
            int buy = Integer.parseInt(properties.getProperty("FreeGiftDiscount.buy"));
            int get = Integer.parseInt(properties.getProperty("FreeGiftDiscount.get"));
            int percentagePriority = Integer.parseInt(properties.getProperty("PercentageDiscount.priority"));
            int freeAdditionPriority = Integer.parseInt(properties.getProperty("FreeGiftDiscount.priority"));

            discounts = asList(new PercentageDiscount(percentage, percentagePriority), new FreeGiftDiscount(buy, get, freeAdditionPriority));
        }
        return discounts;
    }

    private static Properties loadProperties(String configPath) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(configPath), "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private static boolean isProductNameLine(String key) {
        return key.startsWith(PREFIX) && key.endsWith(".name");
    }

    private static String toProductCode(String key) {
        return key.replaceAll(".name", "").replace(PREFIX, "");
    }
}
