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

    @Test
    public void shouldCompareSamePointContactAngleHistory() throws Exception {
        Point startPoint = new Point(0, 0);
        Point point1 = new Point(3, 5);
        Point point2 = new Point(6, 10);

        new Segment(startPoint, point1);
        new Segment(startPoint, point2);

        assertTrue(point1.hasSameHistoryWith(point2));

        point1 = new Point(3, 5);
        point2 = new Point(7, 10);

        new Segment(startPoint, point1);
        new Segment(startPoint, point2);

        assertFalse(point1.hasSameHistoryWith(point2));
    }
}