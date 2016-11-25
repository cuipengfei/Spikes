package com.github.battleship.planloop;

public class Solution {
    public static final char X = 'X';
    int rows;
    int columns;

    public int countBattleships(char[][] board) {
        int count = 0;
        rows = board.length;
        columns = board[0].length;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char symbol = board[row][column];
                if (symbol == X &&
                        !(!(row < 0 || row >= rows || column - 1 < 0 || column - 1 >= columns)
                                && board[row][column - 1] == X ||
                                !(row - 1 < 0 || row - 1 >= rows || column < 0 || column >= columns)
                                        && board[row - 1][column] == X)) {
                    count++;
                }
            }
        }

        return count;
    }
}