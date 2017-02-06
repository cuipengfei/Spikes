package com.github.OddEvenLinkedList;

import java.util.List;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class Solution {

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        } else {
            int index = 3;

            ListNode lastOdd = head;
            ListNode lastEven = head.next;

            ListNode node = lastEven.next;

            while (node != null) {
                if (index % 2 == 1) {
                    lastOdd.next = node;
                    lastOdd = node;
                } else {
                    lastEven.next = node;
                    lastEven = node;
                }

                index++;
                node = node.next;
            }

            lastOdd.next = lastEven;

            return head;
        }
    }
}