package com.github.SingleNumber;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void findInMiddle() throws Exception {
        Solution solution = new Solution();
        int single = solution.singleNumber(new int[]{1, 1, 2, 2, 3, 4, 4, 5, 5});
        assertThat(single, is(3));
    }
}