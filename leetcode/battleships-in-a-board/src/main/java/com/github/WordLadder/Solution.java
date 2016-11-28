package com.github.WordLadder;

import java.util.*;
import java.util.stream.Collectors;

class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children = new ArrayList<>();

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
        if (parent != null) {
            this.parent.addChild(this);
        }
    }

    public void addChild(Node<T> node) {
        children.add(node);
    }

    public T getData() {
        return data;
    }

    public Node<T> getParent() {
        return parent;
    }
}

class NewLevelResult {
    boolean isFound;
    List<Node<String>> nodes;

    public NewLevelResult(boolean isFound, List<Node<String>> node) {
        this.isFound = isFound;
        this.nodes = node;
    }
}

public class Solution {

    boolean isFound = false;

    public List<List<String>> findLadders(String beginWord, String endWord, Set<String> wordList) {
        wordList.remove(endWord);

        if (isOneLetterDiff(beginWord, endWord)) {
            return Collections.singletonList(Arrays.asList(beginWord, endWord));
        } else {
            Node<String> root = new Node<>(beginWord, null);
            List<Node<String>> leaves = Arrays.asList(root);
            while (!isFound) {
                if (leaves.size() == 0) {
                    break;
                }
                leaves = goOneLevelDeeperForEach(leaves, endWord, wordList);
            }

            return leaves.stream()
                    .map(step -> allWords(step, endWord))
                    .collect(Collectors.toList());
        }
    }

    private List<Node<String>> goOneLevelDeeperForEach(List<Node<String>> nodes, String endWord, Set<String> wordList) {
        for (Node<String> node : nodes) {
            wordList.remove(node.getData());//avoid re-counting siblings
        }

        List<Node<String>> foundNodes = new ArrayList<>();
        List<Node<String>> noneFoundNodes = new ArrayList<>();

        for (Node<String> node : nodes) {
            NewLevelResult newLevelResult = goOneLevelDeeper(node, endWord, wordList);
            if (newLevelResult.isFound) {
                isFound = true;
                foundNodes.addAll(newLevelResult.nodes);
            }
            noneFoundNodes.addAll(newLevelResult.nodes);
        }

        if (isFound) {
            return foundNodes;
        } else {
            return noneFoundNodes;
        }
    }

    private NewLevelResult goOneLevelDeeper(Node<String> node, String endWord, Set<String> wordList) {
        List<Node<String>> nextSteps = new ArrayList<>();
        List<Node<String>> lastSteps = new ArrayList<>();

        for (String word : wordList) {
            if (isOneLetterDiff(node.getData(), word)) {
                Node<String> newNode = new Node<>(word, node);
                nextSteps.add(newNode);
                if (isOneLetterDiff(endWord, word)) {
                    lastSteps.add(newNode);
                }
            }
        }

        if (lastSteps.size() > 0) {
            return new NewLevelResult(true, lastSteps);
        } else {
            return new NewLevelResult(false, nextSteps);
        }
    }

    private List<String> allWords(Node<String> step, String endWord) {
        List<String> strs = new ArrayList<>();
        while (step.getParent() != null) {
            strs.add(0, step.getData());
            step = step.getParent();
        }
        strs.add(0, step.getData());//add root
        strs.add(strs.size(), endWord);
        return strs;
    }

    public static boolean isOneLetterDiff(String a, String b) {
        int diffs = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diffs++;
                if (diffs == 2) {
                    return false;
                }
            }
        }
        return diffs == 1;
    }
}
