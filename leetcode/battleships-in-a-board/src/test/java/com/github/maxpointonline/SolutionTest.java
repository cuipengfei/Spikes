package com.github.maxpointonline;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void shouldCompareAngel() throws Exception {
        Segment segment = new Segment(new Point(0, 0), new Point(3, 5));
        Segment anotherSegment = new Segment(new Point(0, 0), new Point(6, 10));

        assertTrue(segment.isSameAngleWith(anotherSegment));


        segment = new Segment(new Point(0, 0), new Point(3, 5));
        anotherSegment = new Segment(new Point(0, 0), new Point(7, 10));

        assertFalse(segment.isSameAngleWith(anotherSegment));
    }
}