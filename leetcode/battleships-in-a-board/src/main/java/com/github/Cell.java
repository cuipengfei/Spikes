package com.github;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class Cell {
    private final int x;
    private final int y;

    private Cell left;
    private Cell right;
    private Cell up;
    private Cell down;

    private boolean isIncludedInBattleShip;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isAdjacentToBattleShip() {
        return Stream.of(left, right, up, down)
                .filter(cell -> !(cell instanceof NotACell))
                .anyMatch(neighbor -> neighbor.isIncludedInBattleShip);
    }

    public void setIsIncludedInBattleShip(boolean isIncludedInBattleShip) {
        this.isIncludedInBattleShip = isIncludedInBattleShip;
    }
}
