package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdventPuzzleDaySeven extends AbstractAdventPuzzleDay {

    public AdventPuzzleDaySeven(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(calculateCalibrationResult(2)); // Only supports + and *
    }

    @Override
    public String solvePart2() {
        return String.valueOf(calculateCalibrationResult(3)); // Includes concatenation (||)
    }

    private long calculateCalibrationResult(int operatorCount) {
        long totalCalibrationResult = 0;

        for (String line : inputLines) {
            String[] parts = line.split(":");
            long targetValue = Long.parseLong(parts[0].trim());
            long[] numbers = parseNumbers(parts[1].trim());

            if (canMatchTarget(numbers, targetValue, operatorCount)) {
                totalCalibrationResult += targetValue;
            }
        }

        return totalCalibrationResult;
    }

    private long[] parseNumbers(String numberString) {
        String[] numberTokens = numberString.split(" ");
        long[] numbers = new long[numberTokens.length];

        for (int i = 0; i < numberTokens.length; i++) {
            numbers[i] = Long.parseLong(numberTokens[i]);
        }

        return numbers;
    }

    private boolean canMatchTarget(long[] numbers, long targetValue, int operatorCount) {
        Map<String, Boolean> memo = new HashMap<>();
        return evaluate(numbers, 0, operatorCount, targetValue, numbers[0], memo);
    }

    private boolean evaluate(long[] numbers, int index, int operatorCount, long targetValue, long currentResult, Map<String, Boolean> memo) {
        if (index == numbers.length - 1) {
            return currentResult == targetValue;
        }

        String memoKey = index + ":" + currentResult;
        if (memo.containsKey(memoKey)) {
            return memo.get(memoKey);
        }

        long nextNumber = numbers[index + 1];
        boolean result = false;

        // Addition
        if (operatorCount > 0) {
            result |= evaluate(numbers, index + 1, operatorCount, targetValue, currentResult + nextNumber, memo);
        }

        // Multiplication
        if (operatorCount > 1) {
            result |= evaluate(numbers, index + 1, operatorCount, targetValue, currentResult * nextNumber, memo);
        }

        // Concatenation
        if (operatorCount > 2) {
            long concatenated = Long.parseLong(currentResult + "" + nextNumber);
            result |= evaluate(numbers, index + 1, operatorCount, targetValue, concatenated, memo);
        }

        memo.put(memoKey, result);
        return result;
    }
}
