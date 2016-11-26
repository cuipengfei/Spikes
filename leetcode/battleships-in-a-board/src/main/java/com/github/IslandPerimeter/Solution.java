package com.github.IslandPerimeter;

public class Solution {
    public int islandPerimeter(int[][] grid) {
        int totalRows = grid.length;

        if (totalRows == 0) {
            return 0;
        } else {
            int perimeter = 0;
            int totalColumns = grid[0].length;

            for (int i = 0; i < totalRows; i++) {
                for (int j = 0; j < totalColumns; j++) {
                    if (isLand(i, j, grid, totalRows, totalColumns)) {
                        perimeter += (4 - neighborLands(i, j, grid, totalRows, totalColumns));
                    }
                }
            }

            return perimeter;
        }
    }

    private int neighborLands(int i, int j, int[][] grid, int totalRows, int totalColumns) {
        return boolToInt(isLand(i, j - 1, grid, totalRows, totalColumns)) +
                boolToInt(isLand(i, j + 1, grid, totalRows, totalColumns)) +
                boolToInt(isLand(i - 1, j, grid, totalRows, totalColumns)) +
                boolToInt(isLand(i + 1, j, grid, totalRows, totalColumns));
    }

    private int boolToInt(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isLand(int i, int j, int[][] grid, int totalRows, int totalColumns) {
        if (i < 0 || i >= totalRows || j < 0 || j >= totalColumns) {
            return false;
        } else {
            return grid[i][j] == 1;
        }
    }
}
