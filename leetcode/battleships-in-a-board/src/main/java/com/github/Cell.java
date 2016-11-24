package com.github;

import java.util.stream.Stream;

public class Cell {

    private Cell left;
    private Cell right;
    private Cell up;
    private Cell down;

    private boolean isPartOfShip;
    private char symbol;

    public Cell(char symbol) {
        this.symbol = symbol;
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

    public boolean isAdjacentToBattleShip() throws NoGapBetweenShipsException {
        Stream<Cell> neighbors = Stream.of(left, right, up, down)
                .filter(Cell::isValidShipPart);

        if (neighbors.count() > 1) {
            throw new NoGapBetweenShipsException();
        }
        return neighbors.count() == 1;
    }

    public boolean couldBePartOfShip() {
        return symbol == 'X';
    }

    public void setIsPartOfShip(boolean isPartOfShip) {
        this.isPartOfShip = isPartOfShip;
    }

    public boolean isValidShipPart() {
        return isValidCell() && couldBePartOfShip();
    }
}
