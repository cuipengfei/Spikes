package com.github;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void should_Parse_One_Point_Board() throws Exception {
        char[][] board = new char[][]{
                {'X'}
        };

        Cell rootCell = Parser.boardToRootCell(board);

        assertThat(rootCell.getLeft(), is(NotACell.instance()));
        assertThat(rootCell.getRight(), is(NotACell.instance()));
        assertThat(rootCell.getUp(), is(NotACell.instance()));
        assertThat(rootCell.getDown(), is(NotACell.instance()));
    }
}