package com.github.MoveZeroes;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void exampleInput() throws Exception {
        Solution solution = new Solution();
        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);
        assertThat(Arrays.stream(nums).boxed()
                .collect(Collectors.toList()), hasItems(1, 3, 12, 0, 0));
    }

    @Test
    public void singleInput() throws Exception {
        Solution solution = new Solution();
        int[] nums = {0};
        solution.moveZeroes(nums);
        assertThat(Arrays.stream(nums).boxed()
                .collect(Collectors.toList()), hasItems(0));
    }
}