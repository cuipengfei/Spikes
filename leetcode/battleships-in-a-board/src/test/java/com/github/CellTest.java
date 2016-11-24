package com.github;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    @Test
    public void a_cell_should_know_if_it_is_adjacent_to_a_battleship() throws Exception {
        //given: there is a cell that is already in a ship
        Cell left = new Cell(0, 1);
        left.setIsIncludedInBattleShip(true);

        //when: another cell is adjacent to that cell
        Cell cell = new Cell(1, 1);
        cell.setLeft(left);

        cell.setRight(NotACell.instance());
        cell.setUp(NotACell.instance());
        cell.setDown(NotACell.instance());

        boolean isAdjacent = cell.isAdjacentToBattleShip();

        //then: the other cell should know it is adjacent to a ship
        assertTrue(isAdjacent);
    }
}