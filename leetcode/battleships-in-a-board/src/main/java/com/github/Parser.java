package com.github;

import java.util.List;

public class Parser {
    private Parser() {
    }

    public static Cell boardToRootCell(char[][] board) {
        return createCellAndNeighbors(0, 0, board);
    }

    private static Cell createCellAndNeighbors(int row, int column, char[][] board) {
        int totalRows = board.length;
        int totalColumns = board[0].length;

        if (isOutOfBounds(row, column, totalRows, totalColumns)) {
            return NotACell.instance();
        } else {
            Cell cell = new Cell(board[row][column]);

            cell.setLeft(createCellAndNeighbors(row, column - 1, board));
            cell.getLeft().setRight(cell);

            cell.setRight(createCellAndNeighbors(row, column + 1, board));
            cell.getRight().setLeft(cell);

            cell.setUp(createCellAndNeighbors(row - 1, column, board));
            cell.getUp().setDown(cell);

            cell.setDown(createCellAndNeighbors(row + 1, column, board));
            cell.getDown().setUp(cell);

            return cell;
        }
    }

    private static boolean isOutOfBounds(int row, int column, int totalRows, int totalColumns) {
        return row >= totalRows || column >= totalColumns || row < 0 || column < 0;
    }
}
