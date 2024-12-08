package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.*;

public class AdventPuzzleDayOne extends AbstractAdventPuzzleDay {

    private final List<Integer> list1;
    private final List<Integer> list2;

    public AdventPuzzleDayOne(String fileName) throws IOException {
        super(fileName);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        initializeLists();
    }

    private void initializeLists() {
        inputLines.forEach(line -> {
            String[] parts = line.trim().split("\\s+"); // Split on any whitespace
            if (parts.length == 2) { // Ensure two parts exist
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
            result1 += Math.abs(list1.get(i) - list2.get(i));
        }

        return String.valueOf(result1);
    }

    @Override
    public String solvePart2() {
        Map<Integer, Integer> appearCount = new HashMap<>();

        for (int i : list1) {
            int appearances = (int) list2.stream().filter(j -> j.equals(i)).count();
            if (appearances > 0) {
                appearCount.put(i, appearances);
            }
        }

        return String.valueOf(appearCount.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum());
    }
}
