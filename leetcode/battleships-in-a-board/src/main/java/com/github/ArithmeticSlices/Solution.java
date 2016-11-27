package com.github.ArithmeticSlices;

public class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        if (A.length < 3) {
            return 0;
        } else {
            int prev = A[1]; //second elem
            int diff = A[1] - A[0]; //init diff
            int continuous = 2;
            int slices = 0;

            for (int i = 2; i < A.length; i++) {
                int current = A[i];
                int newDiff = current - prev;

                boolean isContinuous = newDiff == diff;
                if (isContinuous) {
                    continuous++;
                }

                if (!isContinuous || i == A.length - 1) {
                    slices += slicesOf(continuous);
                    continuous = 2;
                }

                diff = newDiff;
                prev = current;
            }

            return slices;
        }
    }

    private int slicesOf(int continuous) {
        if (continuous < 3) {
            return 0;
        } else {
            int slices = 1;
            int times = continuous - 3;

            for (int i = 0; i < times; i++) {
                slices = slices * 2 + 1;
            }

            return slices;
        }
    }
}
