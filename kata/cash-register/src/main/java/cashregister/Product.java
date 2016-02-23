package cashregister;

public class Product {

    private final String name;
    private String code;
    private final Double singleUnitPrice;
    private String unit;

    public static Product create(String name, String code, String unit, Double singleUnitPrice) {
        return new Product(name, code, singleUnitPrice, unit);
    }

    public Product(String name, String code, Double singleUnitPrice, String unit) {
        this.name = name;
        this.code = code;
        this.singleUnitPrice = singleUnitPrice;
        this.unit = unit;
    }

    public Double singleUnitPrice() {
        return singleUnitPrice;
    }

    public String name() {
        return name;
    }

    public String unit() {
        return unit;
    }

    public String code() {
        return code;
    }
}
