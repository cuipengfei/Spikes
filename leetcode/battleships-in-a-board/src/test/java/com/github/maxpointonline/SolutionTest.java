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
    public void shouldHaveVerticalAngle() throws Exception {
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

    @Test
    public void bigArray() throws Exception {
        Point[] points = {new Point(40, -23),
                new Point(9, 138),
                new Point(429, 115),
                new Point(50, -17),
                new Point(-3, 80),
                new Point(-10, 33),
                new Point(5, -21),
                new Point(-3, 80),
                new Point(-6, -65),
                new Point(-18, 26),
                new Point(-6, -65),
                new Point(5, 72),
                new Point(0, 77),
                new Point(-9, 86),
                new Point(10, -2),
                new Point(-8, 85),
                new Point(21, 130),
                new Point(18, -6),
                new Point(-18, 26),
                new Point(-1, -15),
                new Point(10, -2),
                new Point(8, 69),
                new Point(-4, 63),
                new Point(0, 3),
                new Point(-4, 40),
                new Point(-7, 84),
                new Point(-8, 7),
                new Point(30, 154),
                new Point(16, -5),
                new Point(6, 90),
                new Point(18, -6),
                new Point(5, 77),
                new Point(-4, 77),
                new Point(7, -13),
                new Point(-1, -45),
                new Point(16, -5),
                new Point(-9, 86),
                new Point(-16, 11),
                new Point(-7, 84),
                new Point(1, 76),
                new Point(3, 77),
                new Point(10, 67),
                new Point(1, -37),
                new Point(-10, -81),
                new Point(4, -11),
                new Point(-20, 13),
                new Point(-10, 77),
                new Point(6, -17),
                new Point(-27, 2),
                new Point(-10, -81),
                new Point(10, -1),
                new Point(-9, 1),
                new Point(-8, 43),
                new Point(2, 2),
                new Point(2, -21),
                new Point(3, 82),
                new Point(8, -1),
                new Point(10, -1),
                new Point(-9, 1),
                new Point(-12, 42),
                new Point(16, -5),
                new Point(-5, -61),
                new Point(20, -7),
                new Point(9, -35),
                new Point(10, 6),
                new Point(12, 106),
                new Point(5, -21),
                new Point(-5, 82),
                new Point(6, 71),
                new Point(-15, 34),
                new Point(-10, 87),
                new Point(-14, -12),
                new Point(12, 106),
                new Point(-5, 82),
                new Point(-46, -45),
                new Point(-4, 63),
                new Point(16, -5),
                new Point(4, 1),
                new Point(-3, -53),
                new Point(0, -17),
                new Point(9, 98),
                new Point(-18, 26),
                new Point(-9, 86),
                new Point(2, 77),
                new Point(-2, -49),
                new Point(1, 76),
                new Point(-3, -38),
                new Point(-8, 7),
                new Point(-17, -37),
                new Point(5, 72),
                new Point(10, -37),
                new Point(-4, -57),
                new Point(-3, -53),
                new Point(3, 74),
                new Point(-3, -11),
                new Point(-8, 7),
                new Point(1, 88),
                new Point(-12, 42),
                new Point(1, -37),
                new Point(2, 77),
                new Point(-6, 77),
                new Point(5, 72),
                new Point(-4, -57),
                new Point(-18, -33),
                new Point(-12, 42),
                new Point(-9, 86),
                new Point(2, 77),
                new Point(-8, 77),
                new Point(-3, 77),
                new Point(9, -42),
                new Point(16, 41),
                new Point(-29, -37),
                new Point(0, -41),
                new Point(-21, 18),
                new Point(-27, -34),
                new Point(0, 77),
                new Point(3, 74),
                new Point(-7, -69),
                new Point(-21, 18),
                new Point(27, 146),
                new Point(-20, 13),
                new Point(21, 130),
                new Point(-6, -65),
                new Point(14, -4),
                new Point(0, 3),
                new Point(9, -5),
                new Point(6, -29),
                new Point(-2, 73),
                new Point(-1, -15),
                new Point(1, 76),
                new Point(-4, 77),
                new Point(6, -29)};

        Solution solution = new Solution();

        int maxPoints = solution.maxPoints(points);

        assertThat(maxPoints, is(25));
    }
}