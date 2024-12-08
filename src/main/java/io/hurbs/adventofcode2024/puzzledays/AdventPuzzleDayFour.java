package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;

public class AdventPuzzleDayFour extends AbstractAdventPuzzleDay {

    public AdventPuzzleDayFour(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        char[][] grid = parseGrid(inputLines);
        int totalOccurrences = countWordOccurrences(grid, "XMAS");
        return String.valueOf(totalOccurrences);
    }

    @Override
    public String solvePart2() {
        char[][] grid = parseGrid(inputLines);
        int totalOccurrences = countXMasOccurrences(grid);
        return String.valueOf(totalOccurrences);
    }

    private char[][] parseGrid(java.util.List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    private int countWordOccurrences(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        int count = 0;

        // Directions: {rowDelta, colDelta}
        int[][] directions = {
                {0, 1},   // Horizontal right
                {0, -1},  // Horizontal left
                {1, 0},   // Vertical down
                {-1, 0},  // Vertical up
                {1, 1},   // Diagonal down-right
                {1, -1},  // Diagonal down-left
                {-1, 1},  // Diagonal up-right
                {-1, -1}  // Diagonal up-left
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] direction : directions) {
                    if (isWordFound(grid, word, r, c, direction[0], direction[1], wordLength)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean isWordFound(char[][] grid, String word, int startRow, int startCol, int rowDelta, int colDelta, int wordLength) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < wordLength; i++) {
            int newRow = startRow + i * rowDelta;
            int newCol = startCol + i * colDelta;

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private int countXMasOccurrences(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        for (int r = 1; r < rows - 1; r++) { // Start from the second row to avoid out-of-bounds
            for (int c = 1; c < cols - 1; c++) { // Start from the second column to avoid out-of-bounds
                if (isXMas(grid, r, c)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isXMas(char[][] grid, int row, int col) {
        // Check all 4 variations of the X-MAS pattern
        return checkPattern(grid, row, col, true, true) ||  // Forward-forward
                checkPattern(grid, row, col, true, false) || // Forward-backward
                checkPattern(grid, row, col, false, true) || // Backward-forward
                checkPattern(grid, row, col, false, false);  // Backward-backward
    }

    private boolean checkPattern(char[][] grid, int row, int col, boolean topLeftForward, boolean bottomRightForward) {
        // Top-left diagonal MAS
        String topLeft = topLeftForward ? "MAS" : "SAM";
        // Bottom-right diagonal MAS
        String bottomRight = bottomRightForward ? "MAS" : "SAM";

        // Check top-left diagonal
        if (grid[row - 1][col - 1] != topLeft.charAt(0) ||
                grid[row][col] != topLeft.charAt(1) ||
                grid[row + 1][col + 1] != topLeft.charAt(2)) {
            return false;
        }

        // Check bottom-right diagonal
        if (grid[row - 1][col + 1] != bottomRight.charAt(0) ||
                grid[row][col] != bottomRight.charAt(1) ||
                grid[row + 1][col - 1] != bottomRight.charAt(2)) {
            return false;
        }

        return true;
    }
}