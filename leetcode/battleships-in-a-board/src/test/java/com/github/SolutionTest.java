package com.github;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void sanity() throws Exception {
        assertThat(1, is(1));
    }
}