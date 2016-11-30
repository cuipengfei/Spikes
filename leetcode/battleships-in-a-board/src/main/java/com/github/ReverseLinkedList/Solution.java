package com.github.ReverseLinkedList;

import java.util.ArrayList;
import java.util.List;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        List<ListNode> nodes = new ArrayList<>();
        nodes.add(head);
        while (head.next != null) {
            nodes.add(head.next);
            head = head.next;
        }

        for (int i = nodes.size() - 1; i >= 0; i--) {
            if (i == 0) {
                nodes.get(i).next = null;
            } else {
                nodes.get(i).next = nodes.get(i - 1);
            }
        }

        return nodes.get(nodes.size() - 1);
    }
}
