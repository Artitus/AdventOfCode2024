package io.hurbs.adventofcode2024.day1;

import io.hurbs.adventofcode2024.util.Helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DayOne {

    private final InputStream inputStream;

    public DayOne() {
        this.inputStream = getClass().getClassLoader().getResourceAsStream("day1input.txt");
    }

    public int part1() {

        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        if (this.inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
            reader.lines().forEach(line -> {
                String[] parts = line.trim().split(" {3}");
                if (parts.length == 2) {
                    list1.add(Integer.parseInt(parts[0]));
                    list2.add(Integer.parseInt(parts[1]));
                }
            });
        } else {
            System.err.println("File not found!");
        }

        Collections.sort(list1);
        Collections.sort(list2);

        int result1 = 0;

        for (int i = 0; i < list1.size(); i++) {
            result1 += (Math.abs(list1.get(i) - list2.get(i)));
        }

        return result1;
    }



}
