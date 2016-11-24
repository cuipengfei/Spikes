package com.github;

import java.util.List;
import java.util.stream.Collectors;
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
        List<Cell> neighbors = Stream.of(left, right, up, down)
                .filter(Cell::isValidShipPart).collect(Collectors.toList());

        if (neighbors.size() > 1) {
            throw new NoGapBetweenShipsException();
        }
        return neighbors.size() == 1;
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
