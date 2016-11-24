package com.github;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void shouldNotParseEmptyBoard() throws Exception {
        char[][] board = new char[][]{{}};
        assertThat(Parser.boardToRootCell(board), is(NotACell.instance()));
    }

    @Test
    public void should_Parse_One_Point_Board() throws Exception {
        char[][] board = new char[][]{
                {'X'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.getRight(), is(NotACell.instance()));
        assertThat(rootCell.getDown(), is(NotACell.instance()));
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
}