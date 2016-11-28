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
    List<Node<String>> node;

    public NewLevelResult(boolean isFound, List<Node<String>> node) {
        this.isFound = isFound;
        this.node = node;
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
        List<NewLevelResult> results = nodes.stream()
                .map(node -> goOneLevelDeeper(node, endWord, wordList))
                .collect(Collectors.toList());

        isFound = results.stream().anyMatch(res -> res.isFound);
        if (isFound) {
            return results.stream()
                    .filter(res -> res.isFound)
                    .flatMap(res -> res.node.stream())
                    .collect(Collectors.toList());
        } else {
            return results.stream()
                    .flatMap(res -> res.node.stream())
                    .collect(Collectors.toList());
        }
    }

    private NewLevelResult goOneLevelDeeper(Node<String> node, String endWord, Set<String> wordList) {
        wordList.remove(node.getData());
        List<Node<String>> nextSteps = wordList.stream()
                .filter(word -> isOneLetterDiff(node.getData(), word))
                .map((oneStepWord) -> new Node<>(oneStepWord, node))
                .collect(Collectors.toList());

        List<Node<String>> lastSteps = nextSteps.stream()
                .filter(stepWord -> isOneLetterDiff(stepWord.getData(), endWord))
                .collect(Collectors.toList());

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
