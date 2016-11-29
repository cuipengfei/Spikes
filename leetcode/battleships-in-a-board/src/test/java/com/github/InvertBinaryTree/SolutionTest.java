package com.github.InvertBinaryTree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void exampleInput() throws Exception {
        TreeNode root = new TreeNode(4);

        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        Solution solution = new Solution();
        TreeNode treeNode = solution.invertTree(root);

        assertThat(treeNode.val, is(4));
        assertThat(treeNode.left.val, is(7));
        assertThat(treeNode.right.val, is(2));

        assertThat(treeNode.left.left.val, is(9));
        assertThat(treeNode.left.right.val, is(6));
        assertThat(treeNode.right.left.val, is(3));
        assertThat(treeNode.right.right.val, is(1));
    }
}