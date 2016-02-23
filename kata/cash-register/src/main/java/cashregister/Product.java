package cashregister;

import java.util.List;

public class Product {

    private final String name;
    private String code;
    private final Double singleUnitPrice;
    private String unit;
    private List<Discount> appliedDiscounts;

    public static Product create(String name, String code, String unit, Double singleUnitPrice, List<Discount> appliedDiscounts) {
        return new Product(name, code, singleUnitPrice, unit, appliedDiscounts);
    }

    public Product(String name, String code, Double singleUnitPrice, String unit, List<Discount> appliedDiscounts) {
        this.name = name;
        this.code = code;
        this.singleUnitPrice = singleUnitPrice;
        this.unit = unit;
        this.appliedDiscounts = appliedDiscounts;
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

    public List<Discount> appliedDiscounts() {
        return appliedDiscounts;
    }
}
