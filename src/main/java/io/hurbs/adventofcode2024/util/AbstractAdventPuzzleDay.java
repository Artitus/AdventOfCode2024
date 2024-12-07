package io.hurbs.adventofcode2024.util;

import java.io.*;
import java.util.*;


public abstract class AbstractAdventPuzzleDay {

    protected List<String> inputLines = new ArrayList<>();

    public AbstractAdventPuzzleDay(String fileName) throws IOException {
        parseInput(fileName);
    }

    private void parseInput(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) { throw new FileNotFoundException("Resource file not found: " + fileName); }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.lines().forEach(line -> {
            inputLines.add(line);
        });
        reader.close();
    }

    public abstract String solvePart1();
    public abstract String solvePart2();
}
