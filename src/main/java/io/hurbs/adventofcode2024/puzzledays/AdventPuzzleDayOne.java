package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AdventPuzzleDayOne extends AbstractAdventPuzzleDay {

    private final ArrayList<Integer> list1;
    private final ArrayList<Integer> list2;

    public AdventPuzzleDayOne(String fileName) throws IOException {
        super(fileName);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        initializeLists();
    }

    private void initializeLists() {
            inputLines.forEach(line -> {
                String[] parts = line.trim().split(" {3}");
                if (parts.length == 2) {
                    list1.add(Integer.parseInt(parts[0]));
                    list2.add(Integer.parseInt(parts[1]));
                }
            });

        Collections.sort(list1);
        Collections.sort(list2);
    }

    @Override
    public String solvePart1() {
        int result1 = 0;

        for (int i = 0; i < list1.size(); i++) {
            result1 += (Math.abs(list1.get(i) - list2.get(i)));
        }

        return String.valueOf(result1);
    }

    @Override
    public String solvePart2() {

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

        return String.valueOf(similarityscore);

    }



}
