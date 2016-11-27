package com.github.ReverseString;

public class Solution {
    public String reverseString(String s) {
        if (s == null) {
            return null;
        } else {
            if (s.length() == 0) {
                return s;
            } else {
                char[] chars = s.toCharArray();
                char[] reversedChars = new char[chars.length];
                for (int i = chars.length - 1; i >= 0; i--) {
                    reversedChars[chars.length - i - 1] = chars[i];
                }
                return new String(reversedChars);
            }
        }
    }
}