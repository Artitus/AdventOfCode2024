package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdventPuzzleDayThree extends AbstractAdventPuzzleDay {

    public AdventPuzzleDayThree(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        int sum = 0;

        // Regex pattern to match valid mul(X,Y) instructions
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);

            // Process all matches
            while (matcher.find()) {
                int num1 = Integer.parseInt(matcher.group(1)); // Extract the first number
                int num2 = Integer.parseInt(matcher.group(2)); // Extract the second number
                sum += num1 * num2; // Multiply and add to the sum
            }
        }

        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        int sum = 0;
        boolean mulEnabled = true; // Default state: mul instructions are enabled

        // Regex patterns
        Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Pattern dontPattern = Pattern.compile("don't\\(\\)");

        for (String line : inputLines) {
            Matcher mulMatcher = mulPattern.matcher(line);
            Matcher doMatcher = doPattern.matcher(line);
            Matcher dontMatcher = dontPattern.matcher(line);

            // Track current index in the line
            int currentIndex = 0;

            while (currentIndex < line.length()) {
                // Handle `do()` instructions
                if (doMatcher.find(currentIndex) && doMatcher.start() == currentIndex) {
                    mulEnabled = true;
                    currentIndex = doMatcher.end();
                    continue;
                }

                // Handle `don't()` instructions
                if (dontMatcher.find(currentIndex) && dontMatcher.start() == currentIndex) {
                    mulEnabled = false;
                    currentIndex = dontMatcher.end();
                    continue;
                }

                // Handle `mul(X,Y)` instructions
                if (mulMatcher.find(currentIndex) && mulMatcher.start() == currentIndex) {
                    if (mulEnabled) {
                        int num1 = Integer.parseInt(mulMatcher.group(1));
                        int num2 = Integer.parseInt(mulMatcher.group(2));
                        sum += num1 * num2;
                    }
                    currentIndex = mulMatcher.end();
                    continue;
                }

                // Move to the next character
                currentIndex++;
            }
        }

        return String.valueOf(sum);
    }

}
