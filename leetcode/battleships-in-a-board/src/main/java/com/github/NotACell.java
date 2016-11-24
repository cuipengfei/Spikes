package com.github;

public class NotACell extends Cell {
    private NotACell() {
        super(Integer.MIN_VALUE, Integer.MIN_VALUE);
        setIsIncludedInBattleShip(false);
    }

    public static Cell instance() {
        return new NotACell();
    }
}
