package com.github.InvertBinaryTree;

public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode treeNode = new TreeNode(root.val);
            treeNode.left = invertTree(root.right);
            treeNode.right = invertTree(root.left);
            return treeNode;
        }

        return null;
    }
}
