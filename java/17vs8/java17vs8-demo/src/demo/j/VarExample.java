package demo.j;

import java.util.HashMap;
import java.util.List;

public class VarExample {
    public static void main(String[] args) {
        var message = "Hello, World!";
        var number = 42;

        var myMap = new HashMap<String, List<String>>();
        var myList = myMap.values();

        System.out.println(message);
        System.out.println(number);

        System.out.println(myMap);
        System.out.println(myList);
    }
}
