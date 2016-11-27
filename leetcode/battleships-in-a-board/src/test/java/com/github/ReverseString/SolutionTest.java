package com.github.ReverseString;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void emptyStringForEmptyString() throws Exception {
        Solution solution = new Solution();
        assertThat(solution.reverseString(""), is(""));
    }

    @Test
    public void shouldReverse() throws Exception {
        Solution solution = new Solution();
        assertThat(solution.reverseString("123"), is("321"));
    }
}