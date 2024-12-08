package io.hurbs.adventofcode2024;

import io.hurbs.adventofcode2024.puzzledays.AdventPuzzleDayOne;
import io.hurbs.adventofcode2024.puzzledays.AdventPuzzleDayTwo;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class AdventMain
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        AdventPuzzleDayOne day1 = new AdventPuzzleDayOne("day1input.txt");
        System.out.println("Day 1, part 1: " + day1.solvePart1());
        System.out.println("Day 1, part 2: " + day1.solvePart2());

        AdventPuzzleDayTwo day2 = new AdventPuzzleDayTwo("day2input.txt");
        System.out.println("Day 2, part 1: " + day2.solvePart1());
        System.out.println("Day 2, part 2: " + day2.solvePart2());
    }
}
