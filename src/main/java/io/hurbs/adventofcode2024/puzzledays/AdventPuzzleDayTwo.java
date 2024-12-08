package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventPuzzleDayTwo extends AbstractAdventPuzzleDay {

    public AdventPuzzleDayTwo(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        int safeCount = (int) inputLines.stream()
                .filter(this::isSafe)
                .count();
        return String.valueOf(safeCount);
    }

    @Override
    public String solvePart2() {
        int safeCount = 0;

        for (String line : inputLines) {
            if (isSafe(line)) {
                safeCount++;
            } else {
                List<Integer> levelReport = parseLevels(line);
                boolean madeSafe = false;

                for (int i = 0; i < levelReport.size(); i++) {
                    List<Integer> modifiedReport = new ArrayList<>(levelReport);
                    modifiedReport.remove(i);

                    if (isSafe(modifiedReport)) {
                        madeSafe = true;
                        break;
                    }
                }

                if (madeSafe) {
                    safeCount++;
                }
            }
        }

        return String.valueOf(safeCount);
    }

    private boolean isSafe(String line) {
        List<Integer> levelReport = parseLevels(line);
        return isSafe(levelReport);
    }

    private boolean isSafe(List<Integer> levelReport) {
        if (levelReport.size() < 2) {
            return false;
        }

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 0; i < levelReport.size() - 1; i++) {
            int diff = levelReport.get(i + 1) - levelReport.get(i);

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

    private List<Integer> parseLevels(String line) {
        return List.of(line.trim().split(" "))
                .stream()
                .map(Integer::parseInt)
                .toList();
    }
}
