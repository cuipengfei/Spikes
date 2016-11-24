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


    public Cell(char symbol, int row, int column) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
    }

    public boolean isPartOfShip() {
        return isPartOfShip;
    }

    public Cell getLeft() {
        return left;
    }

    public Cell getRight() {
        return right;
    }

    public Cell getUp() {
        return up;
    }

    public Cell getDown() {
        return down;
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

    public boolean isValidCell() {
        return true;
    }

    public boolean isAdjacentToShip() throws NoGapBetweenShipsException {
        List<Cell> neighbors = Stream.of(left, right, up, down)
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

    public void setIsPartOfShip(boolean isPartOfShip) {
        this.isPartOfShip = isPartOfShip;
    }

}
