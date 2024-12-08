package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Thanks to ChatGPT for the awesome code refactoring in the end to make this pretty!
 */

public class AdventPuzzleDayTwo extends AbstractAdventPuzzleDay {

    public AdventPuzzleDayTwo(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countSafeReports(inputLines, false));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countSafeReports(inputLines, true));
    }

    /**
     * Counts the number of safe reports based on the rules.
     *
     * @param lines       List of input lines representing reports.
     * @param allowRemove Whether a single removal is allowed to make a report safe.
     * @return The count of safe reports.
     */
    private int countSafeReports(List<String> lines, boolean allowRemove) {
        int safeCount = 0;

        for (String line : lines) {
            if (isSafe(line)) {
                safeCount++;
            } else if (allowRemove && canBeMadeSafe(line)) {
                safeCount++;
            }
        }

        return safeCount;
    }

    /**
     * Checks if a report is safe by the rules.
     *
     * @param line The report represented as a space-separated string of numbers.
     * @return True if the report is safe; otherwise, false.
     */
    private boolean isSafe(String line) {
        List<Integer> levels = parseLevels(line);
        if (levels.size() < 2) {
            return false;
        }

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 0; i < levels.size() - 1; i++) {
            int diff = levels.get(i + 1) - levels.get(i);

            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            if (diff > 0) {
                decreasing = false;
            } else if (diff < 0) {
                increasing = false;
            }
        }

        return increasing || decreasing;
    }

    /**
     * Checks if a report can be made safe by removing one level.
     *
     * @param line The report represented as a space-separated string of numbers.
     * @return True if the report can be made safe; otherwise, false.
     */
    private boolean canBeMadeSafe(String line) {
        List<Integer> levels = parseLevels(line);

        for (int i = 0; i < levels.size(); i++) {
            List<Integer> modifiedLevels = new ArrayList<>(levels);
            modifiedLevels.remove(i);

            if (isSafe(convertLevelsToString(modifiedLevels))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Parses a line of space-separated numbers into a list of integers.
     *
     * @param line The input line.
     * @return A list of integers.
     */
    private List<Integer> parseLevels(String line) {
        List<Integer> levels = new ArrayList<>();
        for (String s : line.trim().split(" ")) {
            levels.add(Integer.parseInt(s));
        }
        return levels;
    }

    /**
     * Converts a list of integers into a space-separated string.
     *
     * @param levels The list of integers.
     * @return A space-separated string representation of the list.
     */
    private String convertLevelsToString(List<Integer> levels) {
        StringBuilder result = new StringBuilder();
        for (int level : levels) {
            result.append(level).append(" ");
        }
        return result.toString().trim();
    }
}
