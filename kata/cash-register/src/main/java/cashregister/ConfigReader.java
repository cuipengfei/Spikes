package cashregister;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public final class ConfigReader {

    public static List<Product> loadProducts(String configPath) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(configPath), "UTF-8"));
        return properties.stringPropertyNames().stream()
                .filter(name -> name.startsWith("Product.") && name.endsWith(".name"))
                .map(key -> keyToProduct(key, properties)).collect(Collectors.toList());
    }

    private static Product keyToProduct(String key, Properties properties) {
        String name = properties.getProperty(key);
        String code = key.replaceAll(".name", "").replace("Product.", "");
        double price = Double.parseDouble(properties.getProperty(key.replaceAll(".name", ".price")));
        String unit = properties.getProperty(key.replaceAll(".name", ".unit"));
        return new Product(name, code, price, unit);
    }
}
