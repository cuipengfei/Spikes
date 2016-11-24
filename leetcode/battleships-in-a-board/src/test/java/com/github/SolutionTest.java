package com.github;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SolutionTest {
    @Test
    public void emptyBoardHasNoShip() throws Exception {
        char[][] board = new char[][]{{}};
        int count = new Solution().countBattleships(board);
        assertThat(count, is(0));
    }

    @Test
    public void oneDotIsNoShip() throws Exception {
        char[][] board = new char[][]{
                {'.'}
        };
        int count = new Solution().countBattleships(board);
        assertThat(count, is(0));
    }

    @Test
    public void oneXIsOneShip() throws Exception {
        char[][] board = new char[][]{
                {'X'}
        };
        int count = new Solution().countBattleships(board);
        assertThat(count, is(1));
    }

    @Test
    public void cornerShip() throws Exception {
        char[][] board = new char[][]{
                {'X', '.'},
                {'.', 'X'}
        };

        int count = new Solution().countBattleships(board);
        assertThat(count, is(2));
    }
}