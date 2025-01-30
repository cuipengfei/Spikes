package demo.j;

public class Example2InstanceOf {
    public static void main(String[] args) {
        Object obj = "Hello, Java 17!";

        // Pattern matching with instanceof
        if (obj instanceof String s) {
            // 's' is automatically cast to String
            System.out.println(s.toUpperCase());
        } else {
            System.out.println("Not a String");
        }
    }
}
