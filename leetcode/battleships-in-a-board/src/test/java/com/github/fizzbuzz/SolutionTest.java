package com.github.fizzbuzz;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class SolutionTest {

    @Test
    public void fizzFor3() throws Exception {
        Solution solution = new Solution();
        List<String> strings = solution.fizzBuzz(3);
        assertThat(strings, hasItems("1", "2", "Fizz"));
    }

    @Test
    public void buzzFor5() throws Exception {
        Solution solution = new Solution();
        List<String> strings = solution.fizzBuzz(5);
        assertThat(strings, hasItems("1", "2", "Fizz", "4", "Buzz"));
    }

    @Test
    public void fizzBuzzFor15() throws Exception {
        Solution solution = new Solution();
        List<String> strings = solution.fizzBuzz(15);
        assertThat(strings, hasItems("1",
                "2",
                "Fizz",
                "4",
                "Buzz",
                "Fizz",
                "7",
                "8",
                "Fizz",
                "Buzz",
                "11",
                "Fizz",
                "13",
                "14",
                "FizzBuzz"));
    }
}