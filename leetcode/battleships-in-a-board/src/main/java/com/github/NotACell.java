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
}
