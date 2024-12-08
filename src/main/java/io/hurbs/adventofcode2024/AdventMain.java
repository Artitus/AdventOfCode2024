package io.hurbs.adventofcode2024;

import io.hurbs.adventofcode2024.puzzledays.*;

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
        //AdventPuzzleDayOne day1 = new AdventPuzzleDayOne("day1input.txt");
        //System.out.println("Day 1, part 1: " + day1.solvePart1());
        //System.out.println("Day 1, part 2: " + day1.solvePart2());

       // AdventPuzzleDayTwo day2 = new AdventPuzzleDayTwo("day2input.txt");
       // System.out.println("Day 2, part 1: " + day2.solvePart1());
       // System.out.println("Day 2, part 2: " + day2.solvePart2());

        //AdventPuzzleDayThree day3 = new AdventPuzzleDayThree("day3input.txt");
        //System.out.println("Day 3, part 1 " + day3.solvePart1());
        //System.out.println("Day 3, part 2 " + day3.solvePart2());

        //AdventPuzzleDayFour day4 = new AdventPuzzleDayFour("day4input.txt");
        //System.out.println("Day 4, part 1 " + day4.solvePart1());
        //System.out.println("Day 4, part 2 " + day4.solvePart2());

        AdventPuzzleDayFive day5 = new AdventPuzzleDayFive("day5input.txt");
        System.out.println("Day 5, part 1 " + day5.solvePart1());
        System.out.println("Day 5, part 2 " + day5.solvePart2());
    }
}
