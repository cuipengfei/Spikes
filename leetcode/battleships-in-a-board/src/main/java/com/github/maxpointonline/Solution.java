package com.github.maxpointonline;

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
}

class Angle {
    private int numerator;
    private int denominator;

    public Angle(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public boolean isSameWith(Angle anotherAngle) {
        return numerator == anotherAngle.numerator && denominator == anotherAngle.denominator;
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

    public boolean isSameAngleWith(Segment anotherSeg) {
        return angle.isSameWith(anotherSeg.angle);
    }

    private void initAngle() {
        int xDelta = a.x - b.x;
        int yDelta = a.y - b.y;

        int gcd = GCD(xDelta, yDelta);

        angle = new Angle(yDelta / gcd, xDelta / gcd);
    }

    private int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }
}

public class Solution {
    public int maxPoints(Point[] points) {
        return 0;
    }
}
