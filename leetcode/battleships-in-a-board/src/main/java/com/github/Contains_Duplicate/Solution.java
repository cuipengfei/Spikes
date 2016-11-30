package com.github.Contains_Duplicate;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        } else {
            Set<Integer> ints = new HashSet<>();
            for (int num : nums) {
                if (ints.contains(num)) {
                    return true;
                }
                ints.add(num);
            }
        }
        return false;
    }
}