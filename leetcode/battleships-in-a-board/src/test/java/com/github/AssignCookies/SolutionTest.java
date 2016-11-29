package com.github.AssignCookies;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void exampleInput() throws Exception {
        Solution solution = new Solution();
        int contentChildren = solution.findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3});
        assertThat(contentChildren, is(2));
    }
}