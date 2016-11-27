package com.github.fizzbuzz;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public List<String> fizzBuzz(int n) {
        return IntStream.rangeClosed(1, n).boxed().map(i -> {
            boolean isFizz = i % 3 == 0;
            boolean isBuzz = i % 5 == 0;

            if (isFizz && isBuzz) {
                return "FizzBuzz";
            } else if (isBuzz) {
                return "Buzz";
            } else if (isFizz) {
                return "Fizz";
            }
            return i.toString();
        }).collect(Collectors.toList());
    }
}
