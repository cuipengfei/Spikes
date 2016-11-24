package com.github;

public class Solution {
    public int countBattleships(char[][] board) {
        try {
            return countFrom(Parser.boardToRootCell(board));
        } catch (NoGapBetweenShipsException e) {
            return 0;
        }
    }

    private int countFrom(Cell cell) throws NoGapBetweenShipsException {
        int count = 0;

        if (cell.isValidCell() && !cell.isVisited()) {
            count += countCell(cell);
            count += countFrom(cell.getRight());
            count += countFrom(cell.getDown());
            cell.setVisited(true);
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