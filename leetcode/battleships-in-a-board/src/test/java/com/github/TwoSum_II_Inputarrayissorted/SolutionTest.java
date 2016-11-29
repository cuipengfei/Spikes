package com.github.TwoSum_II_Inputarrayissorted;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void exampleInput() throws Exception {
        Solution solution = new Solution();

        int[] ints = solution.twoSum(new int[]{2, 7, 11, 15}, 9);

        assertThat(ints[0], is(1));
        assertThat(ints[1], is(2));
    }
}