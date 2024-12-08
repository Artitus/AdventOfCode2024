package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AdventPuzzleDaySix extends AbstractAdventPuzzleDay {

    public AdventPuzzleDaySix(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        char[][] grid = parseGrid(inputLines);
        return String.valueOf(simulateGuardPath(grid));
    }

    @Override
    public String solvePart2() {
        char[][] grid = parseGrid(inputLines);
        return String.valueOf(findObstructionPositions(grid));
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

    private int simulateGuardPath(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Find the guard's initial position and direction
        int[] guardPos = new int[2];
        char direction = ' ';
        boolean found = false;

        for (int r = 0; r < rows && !found; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = grid[r][c];
                if ("^v<>".indexOf(ch) != -1) {
                    guardPos[0] = r;
                    guardPos[1] = c;
                    direction = ch;
                    grid[r][c] = '.'; // Replace guard's starting position with open space
                    found = true;
                    break;
                }
            }
        }

        // Directions: {rowDelta, colDelta}
        int[][] directions = {
                {-1, 0}, // Up
                {0, 1},  // Right
                {1, 0},  // Down
                {0, -1}  // Left
        };
        int dirIndex = "^>v<".indexOf(direction);

        // Track visited positions
        Set<String> visited = new HashSet<>();
        visited.add(guardPos[0] + "," + guardPos[1]);

        while (true) {
            // Calculate the next position
            int nextRow = guardPos[0] + directions[dirIndex][0];
            int nextCol = guardPos[1] + directions[dirIndex][1];

            // Check if the guard is leaving the map
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            // Check if there is an obstacle in front
            if (grid[nextRow][nextCol] == '#') {
                dirIndex = (dirIndex + 1) % 4; // Turn right
            } else {
                // Move forward
                guardPos[0] = nextRow;
                guardPos[1] = nextCol;
                visited.add(guardPos[0] + "," + guardPos[1]);
            }
        }

        return visited.size();
    }

    private int findObstructionPositions(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Find the guard's initial position
        int[] guardPos = new int[2];
        char direction = ' ';
        boolean found = false;

        for (int r = 0; r < rows && !found; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = grid[r][c];
                if ("^v<>".indexOf(ch) != -1) {
                    guardPos[0] = r;
                    guardPos[1] = c;
                    direction = ch;
                    grid[r][c] = '.'; // Replace guard's starting position with open space
                    found = true;
                    break;
                }
            }
        }

        // Try placing an obstruction at every open space
        Set<String> validObstructions = new HashSet<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '.' && !(r == guardPos[0] && c == guardPos[1])) {
                    // Simulate with a new obstruction
                    grid[r][c] = '#';
                    if (doesGuardLoop(grid, guardPos.clone(), direction)) {
                        validObstructions.add(r + "," + c);
                    }
                    grid[r][c] = '.'; // Remove the obstruction
                }
            }
        }

        return validObstructions.size();
    }

    private boolean doesGuardLoop(char[][] grid, int[] guardPos, char startDirection) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Directions: {rowDelta, colDelta}
        int[][] directions = {
                {-1, 0}, // Up
                {0, 1},  // Right
                {1, 0},  // Down
                {0, -1}  // Left
        };
        int dirIndex = "^>v<".indexOf(startDirection);

        // Track visited states (position + direction)
        Set<String> visitedStates = new HashSet<>();

        while (true) {
            // Create a unique state string
            String state = guardPos[0] + "," + guardPos[1] + "," + dirIndex;
            if (visitedStates.contains(state)) {
                return true; // Loop detected
            }
            visitedStates.add(state);

            // Calculate the next position
            int nextRow = guardPos[0] + directions[dirIndex][0];
            int nextCol = guardPos[1] + directions[dirIndex][1];

            // Check if the guard is leaving the map
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            // Check if there is an obstacle in front
            if (grid[nextRow][nextCol] == '#') {
                dirIndex = (dirIndex + 1) % 4; // Turn right
            } else {
                // Move forward
                guardPos[0] = nextRow;
                guardPos[1] = nextCol;
            }
        }

        return false;
    }
}
