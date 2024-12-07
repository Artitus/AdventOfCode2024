package io.hurbs.adventofcode2024;

import io.hurbs.adventofcode2024.day1.DayOne;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DayOne day1 = new DayOne();
        System.out.println("Day 1, part 1: " + day1.part1());
        System.out.println("Day 1, part 2: " + day1.part2());
    }
}
