package com.github;

public class NotACell extends Cell {

    private static NotACell notACell = new NotACell();

    private NotACell() {
        super('.');
        setIsPartOfShip(false);
    }

    public static Cell instance() {
        return notACell;
    }

    @Override
    public boolean isValidCell() {
        return false;
    }

    @Override
    public boolean couldBePartOfShip() {
        return false;
    }

    @Override
    public boolean isAdjacentToBattleShip() {
        return false;
    }
}
