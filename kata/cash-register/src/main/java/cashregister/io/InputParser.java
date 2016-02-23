package cashregister.io;

import cashregister.models.OrderLineItem;
import cashregister.models.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cashregister.io.ConfigReader.loadProducts;

public final class InputParser {
    private static List<Product> products;

    public static List<OrderLineItem> parse(String json) {
        products = loadProducts(InputParser.class.getClassLoader().getResource("config.properties").getPath());
        String cleanItems = json.replace("[", "").replace("]", "").replaceAll(" ", "").replaceAll("'", "");
        String[] items = cleanItems.split(",");

        Map<String, List<String>> groups = Arrays.asList(items).stream()
                .collect(Collectors.groupingBy(item -> item));

        return groups.keySet().stream()
                .map(line -> stringToLineItem(line, groups))
                .sorted((x, y) -> x.product().code().compareTo(y.product().code()))
                .collect(Collectors.toList());
    }

    private static OrderLineItem stringToLineItem(String line, Map<String, List<String>> groups) {
        String code;
        int amount;
        if (line.contains("-")) {
            String[] codeAndAmount = line.split("-");
            code = codeAndAmount[0];
            amount = Integer.parseInt(codeAndAmount[1]);
        } else {
            code = line;
            amount = groups.get(line).size();
        }
        return createLineItem(code, amount);
    }

    private static OrderLineItem createLineItem(String code, int amount) {
        Product matchProduct = products.stream().filter(product -> Objects.equals(product.code(), code)).findFirst().get();
        return new OrderLineItem(matchProduct, amount);
    }
}
