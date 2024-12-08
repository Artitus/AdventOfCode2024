package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.*;

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

    private char[][] parseGrid(List<String> lines) {
        return lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private int simulateGuardPath(char[][] grid) {
        int[] guardPos = findGuard(grid);
        char direction = grid[guardPos[0]][guardPos[1]];
        grid[guardPos[0]][guardPos[1]] = '.'; // Replace starting position with open space

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int dirIndex = "^>v<".indexOf(direction);

        Set<String> visited = new HashSet<>();
        visited.add(guardPos[0] + "," + guardPos[1]);

        while (true) {
            int nextRow = guardPos[0] + directions[dirIndex][0];
            int nextCol = guardPos[1] + directions[dirIndex][1];

            if (nextRow < 0 || nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length) {
                break;
            }

            if (grid[nextRow][nextCol] == '#') {
                dirIndex = (dirIndex + 1) % 4; // Turn right
            } else {
                guardPos[0] = nextRow;
                guardPos[1] = nextCol;
                visited.add(guardPos[0] + "," + guardPos[1]);
            }
        }

        return visited.size();
    }

    private int findObstructionPositions(char[][] grid) {
        int[] guardPos = findGuard(grid);
        char direction = grid[guardPos[0]][guardPos[1]];
        grid[guardPos[0]][guardPos[1]] = '.'; // Replace starting position with open space

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        Set<String> validObstructions = new HashSet<>();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '.' && !(r == guardPos[0] && c == guardPos[1])) {
                    grid[r][c] = '#'; // Place obstruction
                    if (doesGuardLoop(grid, guardPos.clone(), direction, directions)) {
                        validObstructions.add(r + "," + c);
                    }
                    grid[r][c] = '.'; // Remove obstruction
                }
            }
        }

        return validObstructions.size();
    }

    private boolean doesGuardLoop(char[][] grid, int[] guardPos, char startDirection, int[][] directions) {
        int dirIndex = "^>v<".indexOf(startDirection);
        Set<String> visitedStates = new HashSet<>();

        while (true) {
            String state = guardPos[0] + "," + guardPos[1] + "," + dirIndex;
            if (visitedStates.contains(state)) {
                return true; // Loop detected
            }
            visitedStates.add(state);

            int nextRow = guardPos[0] + directions[dirIndex][0];
            int nextCol = guardPos[1] + directions[dirIndex][1];

            if (nextRow < 0 || nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length) {
                break;
            }

            if (grid[nextRow][nextCol] == '#') {
                dirIndex = (dirIndex + 1) % 4; // Turn right
            } else {
                guardPos[0] = nextRow;
                guardPos[1] = nextCol;
            }
        }

        return false;
    }

    private int[] findGuard(char[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if ("^>v<".indexOf(grid[r][c]) != -1) {
                    return new int[]{r, c};
                }
            }
        }
        throw new IllegalStateException("Guard not found in the grid");
    }
}