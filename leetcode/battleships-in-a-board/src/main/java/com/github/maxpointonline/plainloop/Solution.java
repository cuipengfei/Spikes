package com.github.maxpointonline.plainloop;

import java.util.Collections;
import java.util.HashMap;

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

public class Solution {

    public int maxPoints(Point[] points) {
        if (points.length == 0) {
            return 0;
        } else {
            int max = 1;

            for (int i = 0; i < points.length; i++) {
                int samePoints = 0;
                Point a = points[i];
                HashMap<Double, Integer> linesStartWithA = new HashMap<>();

                for (int j = i + 1; j < points.length; j++) {
                    Point b = points[j];

                    int deltaX = a.x - b.x;
                    int deltaY = a.y - b.y;

                    if (deltaX == 0 && deltaY == 0) {
                        samePoints++;
                        continue;
                    }

                    Double angle = calcAngle(deltaX, deltaY);
                    updateLines(linesStartWithA, angle);
                }

                max = updateMax(max, linesStartWithA, samePoints);
            }
            return max;
        }
    }

    private int updateMax(int oldMax, HashMap<Double, Integer> linesStartWithA, int samePoints) {
        int longest;
        if (linesStartWithA.size() > 0) {
            longest = Collections.max(linesStartWithA.values()) + samePoints + 1;
        } else {
            longest = samePoints + 1;
        }

        if (longest > oldMax) {
            return longest;
        }
        return oldMax;
    }

    private void updateLines(HashMap<Double, Integer> linesStartWithA, Double angle) {
        if (linesStartWithA.containsKey(angle)) {
            linesStartWithA.put(angle, linesStartWithA.get(angle) + 1);
        } else {
            linesStartWithA.put(angle, 1);
        }
    }

    private Double calcAngle(double deltaX, int deltaY) {
        Double angle;
        if (deltaY == 0) {
            angle = Double.MIN_VALUE;
        } else {
            angle = deltaX / (double) deltaY;
            if (angle == -0d) {
                angle = 0d;
            }
        }
        return angle;
    }
}