package com.github.IslandPerimeter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void emptyCellsShouldBeZero() throws Exception {
        Solution solution = new Solution();
        int perimeter = solution.islandPerimeter(new int[][]{
        });

        assertThat(perimeter, is(0));
    }

    @Test
    public void example16() throws Exception {
        Solution solution = new Solution();
        
        int perimeter = solution.islandPerimeter(new int[][]{
                {0, 1, 0, 0},
                {1, 1, 1, 0},
                {0, 1, 0, 0},
                {1, 1, 0, 0}
        });

        assertThat(perimeter, is(16));
    }
}