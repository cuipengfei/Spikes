package com.github.MaximumDepthofBinaryTree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void noTreeNoDepth() throws Exception {
        Solution solution = new Solution();
        int i = solution.maxDepth(null);
        assertThat(i, is(0));
    }

    @Test
    public void noChildrenOneDepth() throws Exception {
        Solution solution = new Solution();
        int i = solution.maxDepth(new TreeNode(1));
        assertThat(i, is(1));
    }

    @Test
    public void shouldFindOneChildLonger() throws Exception {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);

        root.right = new TreeNode(4);

        Solution solution = new Solution();
        int i = solution.maxDepth(root);

        assertThat(i, is(3));
    }
}