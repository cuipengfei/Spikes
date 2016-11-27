package com.github.SingleNumber;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution {
    public int singleNumber(int[] nums) {
        Map<Integer, List<Integer>> groups = Arrays.stream(nums)
                .boxed()
                .collect(Collectors.groupingBy(n -> n));

        Optional<Map.Entry<Integer, List<Integer>>> first = groups.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1).findFirst();

        return first.get().getKey();
    }
}