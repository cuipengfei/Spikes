package fbw;

import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class IntegrationTests {
    @Test
    public void integration_test() throws Exception {

        Replacer replacer = new DivisibleReplacer(3, "Fizz")
                .chain(new DivisibleReplacer(5, "Buzz"))
                .chain(new DivisibleReplacer(7, "Whizz"))

                .chain(new InclusionReplacer(3, "Fizz"))
                .chain(new InclusionReplacer(5, "Buzz"))
                .chain(new InclusionReplacer(7, "Whizz"))

                .chain(new DefaultReplacer());

        List<String> replacedWords = IntStream
                .rangeClosed(1, 20).boxed()
                .collect(toList()).stream()
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