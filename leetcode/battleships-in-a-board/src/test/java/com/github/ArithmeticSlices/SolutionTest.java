package com.github.ArithmeticSlices;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void noSlicesWhenArraySmallerThan4() throws Exception {
        Solution solution = new Solution();
        int slices = solution.numberOfArithmeticSlices(new int[]{});
        assertThat(slices, is(0));
    }

    @Test
    public void fourContinuousIs3() throws Exception {
        Solution solution = new Solution();
        int slices = solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 4});
        assertThat(slices, is(3));
    }

    @Test
    public void FiveContinuousIs6() throws Exception {
        Solution solution = new Solution();
        int slices = solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 4, 5});
        assertThat(slices, is(6));
    }

    @Test
    public void sixContinuousIs10() throws Exception {
        Solution solution = new Solution();
        int slices = solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 4, 5, 6});
        assertThat(slices, is(10));
    }

    @Test
    public void shouldHandleSeparation() throws Exception {
        Solution solution = new Solution();
        int slices = solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 4, 5, 100, 1, 2, 3, 4, 5});
        assertThat(slices, is(12));
    }
}