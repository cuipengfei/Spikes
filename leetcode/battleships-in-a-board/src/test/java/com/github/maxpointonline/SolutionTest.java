package com.github.maxpointonline;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void shouldCompareAngel() throws Exception {
        Segment segment = new Segment(new Point(0, 0), new Point(3, 5));
        Segment anotherSegment = new Segment(new Point(0, 0), new Point(6, 10));

        assertTrue(segment.getAngle().equals(anotherSegment.getAngle()));


        segment = new Segment(new Point(0, 0), new Point(3, 5));
        anotherSegment = new Segment(new Point(0, 0), new Point(7, 10));

        assertFalse(segment.getAngle().equals(anotherSegment.getAngle()));
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

    @Test
    public void shouldCountOneStraightLine() throws Exception {
        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2)
        });

        assertThat(maxPoints, is(3));
    }


    @Test
    public void shouldCountTwoStraightLines() throws Exception {
        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),

                new Point(2, 3),
                new Point(3, 5),
                new Point(4, 7)
        });

        assertThat(maxPoints, is(4));
    }

    @Test
    public void shouldHaveInvalidAngle() throws Exception {
        Segment segment = new Segment(new Point(0, 0), new Point(0, 1));
        assertThat(segment.getAngle(), CoreMatchers.instanceOf(Vertical.class));
    }

    @Test
    public void onePointIsOneLine() throws Exception {
        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(new Point[]{
                new Point(5, 5)
        });

        assertThat(maxPoints, is(1));
    }

    @Test
    public void noPointIsNoLine() throws Exception {
        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(new Point[]{});

        assertThat(maxPoints, is(0));
    }

    @Test
    public void samePointShouldBeCounted() throws Exception {
        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(0, 0)});

        assertThat(maxPoints, is(3));
    }
}