package com.github;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell {

    private char symbol;
    private final int row;
    private final int column;

    private Cell left;
    private Cell right;
    private Cell up;
    private Cell down;

    private boolean isPartOfShip;
    private boolean visited;

    public Cell(char symbol, int row, int column) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
    }

    public boolean isValidCell() {
        return true;
    }

    public boolean isPartOfShip() {
        return isPartOfShip;
    }

    public void setIsPartOfShip(boolean isPartOfShip) {
        this.isPartOfShip = isPartOfShip;
    }

    public Cell getLeft() {
        return orNotACell(left);
    }

    public Cell getRight() {
        return orNotACell(right);
    }

    public Cell getUp() {
        return orNotACell(up);
    }

    public Cell getDown() {
        return orNotACell(down);
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public void setUp(Cell up) {
        this.up = up;
    }

    public void setDown(Cell down) {
        this.down = down;
    }

    public boolean isAdjacentToShip() throws NoGapBetweenShipsException {
        List<Cell> neighbors = Stream.of(getLeft(), getRight(), getUp(), getDown())
                .filter(Cell::isPartOfShip)
                .collect(Collectors.toList());

        if (!isStraightLine(neighbors)) {
            throw new NoGapBetweenShipsException();
        }

        return neighbors.size() > 0;
    }

    private boolean isStraightLine(List<Cell> neighbors) {
        return neighbors.stream().allMatch(neighbor -> neighbor.row == row) ||
                neighbors.stream().allMatch(neighbor -> neighbor.column == column);
    }

    public boolean couldBePartOfShip() {
        return symbol == 'X';
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private Cell orNotACell(Cell cell) {
        if (cell == null) {
            return NotACell.instance();
        }
        return cell;
    }
}
