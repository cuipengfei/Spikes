package demo.k

data class Person(val name: String, val address: Address?)

data class Address(val city: String?)

fun main() {
    val person = Person("John", null)
    // Kotlin's null safety will prevent the NullPointerException
    println(person.address?.city)
}
