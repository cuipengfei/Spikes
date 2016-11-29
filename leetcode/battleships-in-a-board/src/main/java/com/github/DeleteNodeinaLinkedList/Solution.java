package com.github.DeleteNodeinaLinkedList;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class Solution {
    public void deleteNode(ListNode node) {
        if (node.next == null) {
            return;
        } else {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }
}