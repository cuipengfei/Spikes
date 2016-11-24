package com.github;

public class Solution {
    public int countBattleships(char[][] board) {
        Cell rootCell = Parser.boardToRootCell(board);
        if (!rootCell.isValidCell()) {
            return 0;
        } else {
            return 1;
        }
    }
}