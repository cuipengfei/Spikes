package com.github.OddEvenLinkedList;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void shouldReverse() throws Exception {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);

        one.next = two;
        two.next = three;

        Solution solution = new Solution();
        ListNode listNode = solution.oddEvenList(one);

        assertThat(listNode.val, is(1));
        assertThat(listNode.next.val, is(3));
        assertThat(listNode.next.next.val, is(2));
    }

    @Test
    public void biggerInput() throws Exception {
        Solution solution = new Solution();

        ListNode node = solution.oddEvenList(makeLinkedList(1, 2, 3, 4, 5, 6, 7, 8));

        assertThat(node.val, is(1));
        assertThat(node.val, is(3));
        assertThat(node.val, is(5));
        assertThat(node.val, is(7));
        assertThat(node.val, is(2));
        assertThat(node.val, is(4));
        assertThat(node.val, is(6));
        assertThat(node.val, is(8));
    }

    private ListNode makeLinkedList(int... nums) {
        ListNode root = new ListNode(nums[0]);
        ListNode node = root;

        for (int i = 1; i < nums.length; i++) {
            ListNode newNode = new ListNode(nums[i]);
            node.next = newNode;
            node = newNode;
        }
        return root;
    }
}