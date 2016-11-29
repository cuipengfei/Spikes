package com.github.MoveZeroes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public void moveZeroes(int[] nums) {
        Map<Boolean, List<Integer>> zeroNoneZeroGroups = IntStream.of(nums).boxed()
                .collect(Collectors.groupingBy(n -> n == 0));

        List<Integer> zeros = zeroNoneZeroGroups.get(true);
        List<Integer> noneZeros = zeroNoneZeroGroups.get(false);

        int index = 0;
        if (noneZeros != null) {
            for (Integer n : noneZeros) {
                nums[index] = n;
                index++;
            }
        }
        if (zeros != null) {
            for (Integer n : zeros) {
                nums[index] = n;
                index++;
            }
        }
    }
}
