package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventPuzzleDaySeven extends AbstractAdventPuzzleDay {

    public AdventPuzzleDaySeven(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        return calculateCalibrationResult(inputLines, 2); // Only supports + and *
    }

    @Override
    public String solvePart2() {
        return calculateCalibrationResult(inputLines, 3); // Includes concatenation (||)
    }

    private String calculateCalibrationResult(List<String> equations, int numOperators) {
        long totalCalibrationResult = 0;

        for (String equation : equations) {
            String[] parts = equation.split(":");
            long targetValue = Long.parseLong(parts[0].trim());
            String[] numberStrings = parts[1].trim().split(" ");
            long[] numbers = new long[numberStrings.length];
            for (int i = 0; i < numberStrings.length; i++) {
                numbers[i] = Long.parseLong(numberStrings[i]);
            }

            if (canMatchTarget(numbers, targetValue, numOperators)) {
                totalCalibrationResult += targetValue;
            }
        }

        return String.valueOf(totalCalibrationResult);
    }

    private boolean canMatchTarget(long[] numbers, long targetValue, int numOperators) {
        int n = numbers.length;
        int totalCombinations = (int) Math.pow(numOperators, n - 1);

        // Generate all possible combinations of operators
        for (int i = 0; i < totalCombinations; i++) {
            char[] operators = new char[n - 1];
            int combination = i;
            for (int j = 0; j < n - 1; j++) {
                int operatorIndex = combination % numOperators;
                combination /= numOperators;
                operators[j] = (operatorIndex == 0) ? '+' : (operatorIndex == 1) ? '*' : '|';
            }

            // Evaluate the expression with the current operator combination
            if (evaluateExpression(numbers, operators) == targetValue) {
                return true;
            }
        }

        return false;
    }

    private long evaluateExpression(long[] numbers, char[] operators) {
        long result = numbers[0];

        for (int i = 0; i < operators.length; i++) {
            if (operators[i] == '+') {
                result += numbers[i + 1];
            } else if (operators[i] == '*') {
                result *= numbers[i + 1];
            } else if (operators[i] == '|') {
                result = Long.parseLong(result + "" + numbers[i + 1]);
            }
        }

        return result;
    }
}
