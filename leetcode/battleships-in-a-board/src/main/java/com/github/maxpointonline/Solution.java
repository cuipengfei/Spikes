package com.github.maxpointonline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class Point {
    int x;
    int y;

    private List<Angle> angleHistory = new ArrayList<>();

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }

    public boolean hasSameHistoryWith(Point anotherPoint) {
        for (int i = 0; i < angleHistory.size(); i++) {
            if (angleHistory.get(i).equals(anotherPoint.angleHistory.get(i))) {
                return true;
            }
        }

        return false;
    }

    public void addAngleHistory(Angle angle) {
        angleHistory.add(angle);
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

class InvalidAngle extends Angle {

    public InvalidAngle() {
        super(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        return false;
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

    public boolean isSameAngleWith(Segment anotherSeg) {
        return angle.equals(anotherSeg.angle);
    }

    private void initAngle() {
        int xDelta = a.x - b.x;
        int yDelta = a.y - b.y;

        int gcd = GCD(xDelta, yDelta);

        if (gcd == 0) {
            angle = new InvalidAngle();
        } else {
            angle = new Angle(yDelta / gcd, xDelta / gcd);
        }

        b.addAngleHistory(angle);
    }

    private int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }
}

public class Solution {
    public int maxPoints(Point[] points) {
        int max = 0;

        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            List<Segment> segmentsStartWithA = new ArrayList<>();

            for (int j = i + 1; j < points.length; j++) {
                Point b = points[j];

                if (!a.hasSameHistoryWith(b)) {
                    segmentsStartWithA.add(new Segment(a, b));
                }
            }

            Map<Angle, List<Segment>> sameAngleSegGroups = segmentsStartWithA.stream()
                    .collect(Collectors.groupingBy(Segment::getAngle));

            Optional<List<Segment>> maxSegGroup = sameAngleSegGroups.values()
                    .stream()
                    .max((g1, g2) -> g1.size() - g2.size());

            if (maxSegGroup.isPresent()) {
                int numberOfPointInMaxSegGroup = maxSegGroup.get().size() + 1;
                if (numberOfPointInMaxSegGroup > max) {
                    max = numberOfPointInMaxSegGroup;
                }
            }
        }
        return max;
    }
}
