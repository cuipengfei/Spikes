package cashregister.io;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cashregister.io.ConfigReader.loadProducts;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public final class InputParser {
    private static List<Product> products;//可变状态,不安全
    private static String configFilePath = InputParser.class.getClassLoader().getResource("config.properties").getPath();

    public static List<OrderLineItem> parse(String json) {
        products = loadProducts(configFilePath);
        String cleanItems = json.replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("'", "");
        String[] jsonLines = cleanItems.split(",");

        Map<String, List<String>> jsonLineGroups = Arrays.asList(jsonLines).stream()
                .collect(groupingBy(item -> item));

        return jsonLineGroups.keySet().stream()
                .map(jsonLine -> jsonLineToOrderLineItem(jsonLine, jsonLineGroups))
                .sorted((x, y) -> x.product().code().compareTo(y.product().code()))
                .collect(toList());
    }

    private static OrderLineItem jsonLineToOrderLineItem(String line, Map<String, List<String>> groups) {
        if (line.contains("-")) {
            return jsonLineWithDashToOrderLineItem(line);
        } else {
            return createLineItem(line, groups.get(line).size());
        }
    }

    private static OrderLineItem jsonLineWithDashToOrderLineItem(String line) {
        String[] codeAndAmount = line.split("-");
        String code = codeAndAmount[0];
        int amount = Integer.parseInt(codeAndAmount[1]);
        return createLineItem(code, amount);
    }

    private static OrderLineItem createLineItem(String code, int amount) {
        Product matchProduct = products.stream().filter(product -> Objects.equals(product.code(), code)).findFirst().get();
        return new OrderLineItem(matchProduct, amount);
    }
}
