package demo.j;

public class Example6BetterNPE {
    public static void main(String[] args) {
        Person person = new Person("John", null);

        // This will cause a NullPointerException
        String city = person.address().city();

        // this is more of a JVM change than a Java one –
        // as the bytecode analysis to build the detailed message is performed at runtime JVM
    }

    record Person(String name, Address address) {
    }

    record Address(String city) {
    }

}
