package org.example;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class IntegrationTests {

    @Test
    public void integration_test_all_special_cases() throws Exception {

        Replacer replacer = new ReplacerBuilder()
                .whenIncludes(3).replaceThenStop("Fizz")
                .whenDivisibleBy(3).replaceThenContinue("Fizz")
                .whenDivisibleBy(5).replaceThenContinue("Buzz")
                .whenDivisibleBy(7).replaceThenContinue("Whizz")
                .build();

        List<String> replacedWords = Stream.of(
                        1, 2, 4, 8, // 不变
                        3, 5, 7, // 一倍
                        6, 10, 14, // 两倍

                        15, // 3x5
                        21, // 3x7
                        70, // 5x7x2 (不能用35来测因为35包含3)
                        105, //3x5x7

                        33, 35 // 包含3，且是5或7的倍数
                )
                .map(replacer::replace).collect(toList());

        assertThat(replacedWords, contains(
                "1", "2", "4", "8",
                "Fizz", "Buzz", "Whizz",
                "Fizz", "Buzz", "Whizz",
                "FizzBuzz", "FizzWhizz", "BuzzWhizz", "FizzBuzzWhizz",
                "Fizz", "Fizz"
        ));
    }
}