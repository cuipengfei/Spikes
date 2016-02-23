package cashregister;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public final class ConfigReader {

    private static final String PREFIX = "Product.";
    private static List<Discount> discounts;

    public static List<Product> loadProducts(String configPath) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(configPath), "UTF-8"));
        return properties.stringPropertyNames().stream()
                .filter(ConfigReader::isProductNameLine)
                .map(ConfigReader::toProductCode)
                .map(productCode -> toProduct(productCode, properties))
                .collect(Collectors.toList());
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
            int buy = Integer.parseInt(properties.getProperty("FreeAdditionDiscount.buy"));
            int get = Integer.parseInt(properties.getProperty("FreeAdditionDiscount.get"));
            discounts = asList(new PercentageDiscount(percentage), new FreeAdditionDiscount(buy, get));
        }
        return discounts;
    }

    private static boolean isProductNameLine(String key) {
        return key.startsWith(PREFIX) && key.endsWith(".name");
    }

    private static String toProductCode(String key) {
        return key.replaceAll(".name", "").replace(PREFIX, "");
    }
}
