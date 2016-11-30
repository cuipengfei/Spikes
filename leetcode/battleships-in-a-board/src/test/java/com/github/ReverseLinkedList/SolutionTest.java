package com.github.ReverseLinkedList;

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
        ListNode listNode = solution.reverseList(one);

        assertThat(listNode.val, is(3));
        assertThat(listNode.next.val, is(2));
        assertThat(listNode.next.next.val, is(1));
    }
}