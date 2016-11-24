package com.github;

public class NotACell extends Cell {
    private NotACell() {
        super('.');
        setIsPartOfShip(false);
    }

    public static Cell instance() {
        return new NotACell();
    }
}
