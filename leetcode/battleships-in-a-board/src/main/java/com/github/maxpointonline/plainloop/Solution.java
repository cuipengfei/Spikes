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

                Point a = points[i];
                //      Angle   Number
                HashMap<Double, Integer> linesStartWithA = new HashMap<>();
                int samePoints = 0;
                for (int j = i + 1; j < points.length; j++) {
                    Point b = points[j];

                    int deltaX = a.x - b.x;
                    int deltaY = a.y - b.y;

                    Double angle;
                    if (deltaX == 0 && deltaY == 0) {
                        samePoints++;
                        continue;
                    } else if (deltaY == 0) {
                        angle = Double.MIN_VALUE;
                    } else {
                        angle = (double) deltaX / (double) deltaY;
                        if (angle == -0d) {
                            angle = 0d;
                        }
                    }

                    if (linesStartWithA.containsKey(angle)) {
                        linesStartWithA.put(angle, linesStartWithA.get(angle) + 1);
                    } else {
                        linesStartWithA.put(angle, 1);
                    }
                }

                int longest;
                if (linesStartWithA.size() > 0) {
                    longest = Collections.max(linesStartWithA.values()) + samePoints + 1;
                } else {
                    longest = samePoints + 1;
                }
                if (longest > max) {
                    max = longest;
                }
            }
            return max;
        }
    }
}