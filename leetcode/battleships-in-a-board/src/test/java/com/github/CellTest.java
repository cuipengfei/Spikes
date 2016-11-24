package com.github;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    @Test
    public void a_cell_should_know_if_itself_could_be_a_part_of_a_ship() throws Exception {
        Cell cell = new Cell('.', 1, 1);
        assertFalse(cell.couldBePartOfShip());

        cell = new Cell('X', 1, 1);
        assertTrue(cell.couldBePartOfShip());
    }

    @Test
    public void a_cell_should_know_if_it_is_adjacent_to_a_battleship() throws Exception, NoGapBetweenShipsException {
        //given: there is a cell that is in a ship
        Cell left = new Cell('X', 0, 0);
        left.setIsPartOfShip(true);

        //when: another cell is adjacent to that cell
        Cell cell = new Cell('X', 0, 1);
        cell.setLeft(left);

        cell.setRight(NotACell.instance());
        cell.setUp(NotACell.instance());
        cell.setDown(NotACell.instance());

        boolean isAdjacent = cell.isAdjacentToShip();

        //then: the other cell should know it is adjacent to a ship
        assertTrue(isAdjacent);
    }
}