package com.github.AssignCookies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int findContentChildren(int[] greeds, int[] sizes) {
        Queue<Integer> greedQueue = sortAndQueue(greeds);
        Queue<Integer> sizesQueue = sortAndQueue(sizes);

        int happy = 0;

        while (!greedQueue.isEmpty() && !sizesQueue.isEmpty()) {
            if (greedQueue.peek() <= sizesQueue.peek()) {
                greedQueue.poll();
                happy++;
            }
            sizesQueue.poll();
        }

        return happy;
    }

    private static Queue<Integer> sortAndQueue(int[] array) {
        Arrays.sort(array);
        Queue<Integer> q = new LinkedList<>();
        for (int i : array) {
            q.add(i);
        }
        return q;
    }
}
