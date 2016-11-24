package com.github;

public class Parser {
    private Parser() {
    }

    public static Cell boardToRootCell(char[][] board) {
        return createCellAndNeighbors(0, 0, board, createCellsCache(board));
    }

    private static Cell[][] createCellsCache(char[][] board) {
        int totalRows = board.length;
        int totalColumns = board[0].length;
        return new Cell[totalRows][totalColumns];
    }

    private static Cell createCellAndNeighbors(int row, int column, char[][] board, Cell[][] cells) {
        int totalRows = board.length;
        int totalColumns = board[0].length;

        if (isOutOfBounds(row, column, totalRows, totalColumns)) {
            return NotACell.instance();
        } else {
            if (cells[row][column] != null) {
                return cells[row][column];
            } else {
                return newCell(row, column, board, cells);
            }
        }
    }

    private static Cell newCell(int row, int column, char[][] board, Cell[][] cells) {
        Cell cell = new Cell(board[row][column]);
        cells[row][column] = cell;

        cell.setLeft(createCellAndNeighbors(row, column - 1, board, cells));
        cell.getLeft().setRight(cell);

        cell.setRight(createCellAndNeighbors(row, column + 1, board, cells));
        cell.getRight().setLeft(cell);

        cell.setUp(createCellAndNeighbors(row - 1, column, board, cells));
        cell.getUp().setDown(cell);

        cell.setDown(createCellAndNeighbors(row + 1, column, board, cells));
        cell.getDown().setUp(cell);

        return cell;
    }

    private static boolean isOutOfBounds(int row, int column, int totalRows, int totalColumns) {
        return row >= totalRows || column >= totalColumns || row < 0 || column < 0;
    }
}
