package demo.j;

public class RecordExample {
    public static void main(String[] args) {
        // Creating an instance of the Person record
        Person person = new Person("John Doe", 30, "john.doe@example.com");

        // Accessing record components
        System.out.println("Name: " + person.name());
        System.out.println("Age: " + person.age());
        System.out.println("Email: " + person.email());

        // Using the automatically generated toString() method
        System.out.println("Person: " + person);

        // Using the automatically generated equals() and hashCode() methods
        Person anotherPerson = new Person("John Doe", 30, "john.doe@example.com");
        System.out.println("Are they equal? " + person.equals(anotherPerson));
        System.out.println("Hash code: " + person.hashCode());
    }

    // Declaration of a record class
    public record Person(String name, int age, String email) {
    }
}

