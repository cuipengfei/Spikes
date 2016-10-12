package fbw;

import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class IntegrationTests {
    @Test
    public void integration_test() throws Exception {

        Replacer replacer = new ReplacerBuilder()
                .whenDivisibleBy(3).replaceWith("Fizz")
                .whenDivisibleBy(5).replaceWith("Buzz")
                .whenDivisibleBy(7).replaceWith("Whizz")

                .whenIncludes(3).replaceWith("Fizz")
                .whenIncludes(5).replaceWith("Buzz")
                .whenIncludes(7).replaceWith("Whizz")

                .build();

        List<String> replacedWords = rangeClosed(1, 20)
                .boxed().collect(toList()).stream()
                .map(replacer::replace).collect(toList());

        assertThat(replacedWords, contains(
                "1",
                "2",
                "Fizz",
                "4",
                "Buzz",
                "Fizz",
                "Whizz",
                "8",
                "Fizz",
                "Buzz",
                "11",
                "Fizz",
                "Fizz",
                "Whizz",
                "FizzBuzz",
                "16",
                "Whizz",
                "Fizz",
                "19",
                "Buzz"
        ));
    }
}