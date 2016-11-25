package com.github.battleship;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParserTest {

    @Test
    public void shouldNotParseEmptyBoard() throws Exception {
        char[][] board = new char[][]{{}};
        assertThat(Parser.boardToRootCell(board), instanceOf(NotACell.class));
    }

    @Test
    public void should_Parse_One_Point_Board() throws Exception {
        char[][] board = new char[][]{
                {'X'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.getLeft(), instanceOf(NotACell.class));
        assertThat(rootCell.getRight(), instanceOf(NotACell.class));
        assertThat(rootCell.getUp(), instanceOf(NotACell.class));
        assertThat(rootCell.getDown(), instanceOf(NotACell.class));
    }

    @Test
    public void shouldParseOneRowBoard() throws Exception {
        char[][] board = new char[][]{
                {'X', '.'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.couldBePartOfShip(), is(true));
        assertThat(rootCell.getRight().couldBePartOfShip(), is(false));
    }

    @Test
    public void shouldParseOneColumnBoard() throws Exception {
        char[][] board = new char[][]{
                {'X'},
                {'.'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.couldBePartOfShip(), is(true));
        assertThat(rootCell.getDown().couldBePartOfShip(), is(false));
    }

    @Test
    public void shouldParseMultiRowsMultiColumnsBoard() throws Exception {
        char[][] board = new char[][]{
                {'X', '.'},
                {'.', 'X'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.couldBePartOfShip(), is(true));
        assertThat(rootCell.getRight().couldBePartOfShip(), is(false));
        assertThat(rootCell.getDown().couldBePartOfShip(), is(false));
        assertThat(rootCell.getDown().getRight().couldBePartOfShip(), is(true));
    }
}