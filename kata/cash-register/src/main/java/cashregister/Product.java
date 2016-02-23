package cashregister;

public class Product {

    private final String name;
    private final Double singleUnitPrice;

    public Product(String name, Double singleUnitPrice) {
        this.name = name;
        this.singleUnitPrice = singleUnitPrice;
    }

    public static Product create(String name, Double singleUnitPrice) {
        return new Product(name, singleUnitPrice);
    }

    public Double singleUnitPrice() {
        return singleUnitPrice;
    }
}
