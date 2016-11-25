package com.github.maxpointonline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

class Point {
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}

class Angle {
    private int numerator;
    private int denominator;

    public Angle(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Angle angle = (Angle) o;

        if (numerator != angle.numerator) return false;
        return denominator == angle.denominator;

    }

    @Override
    public int hashCode() {
        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }
}

class Vertical extends Angle {

    private static Vertical vertical;

    private Vertical() {
        super(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static Vertical instance() {
        vertical = new Vertical();
        return vertical;
    }
}

class Horizontal extends Angle {

    private static Horizontal horizontal;

    private Horizontal() {
        super(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static Horizontal instance() {
        horizontal = new Horizontal();
        return horizontal;
    }
}

class Same extends Angle {

    private static Same same;

    private Same() {
        super(0, 0);
    }

    public static Same instance() {
        same = new Same();
        return same;
    }
}

class Segment {
    private final Point a;
    private final Point b;

    private Angle angle;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;

        initAngle();
    }

    public Angle getAngle() {
        return angle;
    }

    private void initAngle() {
        int xDelta = a.x - b.x;
        int yDelta = a.y - b.y;

        if (xDelta == 0 && yDelta == 0) {
            angle = Same.instance();
        } else if (xDelta == 0) {
            angle = Vertical.instance();
        } else if (yDelta == 0) {
            angle = Horizontal.instance();
        } else {
            int gcd = GCD(xDelta, yDelta);
            angle = new Angle(yDelta / gcd, xDelta / gcd);
        }
    }

    private int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }
}

public class Solution {
    public int maxPoints(Point[] points) {
        if (points.length == 0) {
            return 0;
        }

        int max = 1;

        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            List<Segment> segmentsStartWithA = new ArrayList<>();

            for (int j = i + 1; j < points.length; j++) {
                Point b = points[j];
                Segment seg = new Segment(a, b);
                segmentsStartWithA.add(seg);
            }

            Map<Angle, List<Segment>> sameAngleSegGroups = segmentsStartWithA.stream()
                    .collect(groupingBy(Segment::getAngle));

            final int samePointSegCount;
            if (sameAngleSegGroups.containsKey(Same.instance())) {
                samePointSegCount = sameAngleSegGroups.get(Same.instance()).size();
            } else {
                samePointSegCount = 0;
            }

            Optional<Integer> maxNumOptional = sameAngleSegGroups.values().stream().map(group -> {
                if (group.get(0).getAngle() instanceof Same) {
                    return samePointSegCount + 1;
                }
                return group.size() + 1 + samePointSegCount;
            }).max((i1, i2) -> i1 - i2);

            if (maxNumOptional.isPresent()) {
                int maxNum = maxNumOptional.get();

                if (maxNum > max) {
                    max = maxNum;
                }
            }
        }
        return max;
    }
}
