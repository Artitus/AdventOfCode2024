package io.hurbs.adventofcode2024.day1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DayOne {

    private final InputStream inputStream;
    private final ArrayList<Integer> list1;
    private final ArrayList<Integer> list2;

    public DayOne() {
        this.inputStream = getClass().getClassLoader().getResourceAsStream("day1input.txt");
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        initializeLists();
    }

    private void initializeLists() {
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
    }

    public int part1() {
        int result1 = 0;

        for (int i = 0; i < list1.size(); i++) {
            result1 += (Math.abs(list1.get(i) - list2.get(i)));
        }

        return result1;
    }

    public AtomicInteger part2() {

        HashMap<Integer, Integer> appearCount = new HashMap<>();

        for (int i = 0; i < list1.size(); i++) {
            int appearances = 0;
            for (int i1 = 0; i1 < list2.size(); i1++) {
                if (list1.get(i).equals(list2.get(i1))) { appearances++; }
            }
            if (appearances > 0) {
                appearCount.put(list1.get(i), appearances);
            }
        }

        AtomicInteger similarityscore = new AtomicInteger();
        appearCount.forEach((k,v) -> {
            similarityscore.addAndGet(k * v);
        });

        return similarityscore;

    }



}
