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
            count += countCell(cell);
            count += countFrom(cell.getRight());
            count += countFrom(cell.getDown());
        }

        return count;
    }

    private int countCell(Cell cell) throws NoGapBetweenShipsException {
        int count = 0;

        if (cell.couldBePartOfShip()) {
            if (!cell.isAdjacentToShip() && !cell.isPartOfShip()) {
                count = 1;
            }
            cell.setIsPartOfShip(true);
        }
        return count;
    }

}