package com.github;

import static com.github.Parser.boardToRootCell;

public class Solution {
    public int countBattleships(char[][] board) {
        try {
            return countFrom(boardToRootCell(board));
        } catch (NoGapBetweenShipsException e) {
            return 0;
        }
    }

    private int countFrom(Cell cell) throws NoGapBetweenShipsException {
        int count = 0;

        if (cell.isValidCell()) {
            if (cell.couldBePartOfShip()) {
                if (!cell.isAdjacentToBattleShip() && !cell.isPartOfShip()) {
                    cell.setIsPartOfShip(true);
                    count++;
                }
            }

            count += countFrom(cell.getRight());
            count += countFrom(cell.getDown());
        }

        return count;
    }

}