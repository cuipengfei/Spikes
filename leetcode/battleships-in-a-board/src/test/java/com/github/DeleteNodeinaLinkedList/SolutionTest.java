package com.github.DeleteNodeinaLinkedList;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {

    private ListNode root;

    @Before
    public void setUp() throws Exception {
        root = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode tail = new ListNode(4);

        root.next = second;
        second.next = third;
        third.next = tail;
    }

    @Test
    public void shouldNotDeleteTail() throws Exception {
        ListNode tail = root.next.next.next;

        Solution solution = new Solution();
        solution.deleteNode(tail);

        assertThat(root.val, is(1));
        assertThat(root.next.val, is(2));
        assertThat(root.next.next.val, is(3));
        assertThat(root.next.next.next.val, is(4));
    }
}