package io.hurbs.adventofcode2024.puzzledays;

import io.hurbs.adventofcode2024.util.AbstractAdventPuzzleDay;

import java.io.IOException;
import java.util.*;

public class AdventPuzzleDayFive extends AbstractAdventPuzzleDay {

    public AdventPuzzleDayFive(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public String solvePart1() {
        List<String> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        parseInput(inputLines, rules, updates);

        Map<Integer, List<Integer>> ruleMap = buildRuleMap(rules);
        int sumOfMiddlePages = 0;

        for (List<Integer> update : updates) {
            if (isUpdateValid(update, ruleMap)) {
                sumOfMiddlePages += update.get(update.size() / 2);
            }
        }

        return String.valueOf(sumOfMiddlePages);
    }

    @Override
    public String solvePart2() {
        List<String> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        parseInput(inputLines, rules, updates);

        Map<Integer, List<Integer>> ruleMap = buildRuleMap(rules);
        int sumOfMiddlePages = 0;

        for (List<Integer> update : updates) {
            if (!isUpdateValid(update, ruleMap)) {
                // Reorder the update
                List<Integer> reorderedUpdate = reorderUpdate(update, ruleMap);
                sumOfMiddlePages += reorderedUpdate.get(reorderedUpdate.size() / 2);
            }
        }

        return String.valueOf(sumOfMiddlePages);
    }

    private void parseInput(List<String> lines, List<String> rules, List<List<Integer>> updates) {
        boolean isUpdateSection = false;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                isUpdateSection = true;
                continue;
            }

            if (!isUpdateSection) {
                rules.add(line.trim());
            } else {
                List<Integer> update = new ArrayList<>();
                for (String page : line.split(",")) {
                    update.add(Integer.parseInt(page.trim()));
                }
                updates.add(update);
            }
        }
    }

    private Map<Integer, List<Integer>> buildRuleMap(List<String> rules) {
        Map<Integer, List<Integer>> ruleMap = new HashMap<>();

        for (String rule : rules) {
            String[] parts = rule.split("\\|");
            int before = Integer.parseInt(parts[0].trim());
            int after = Integer.parseInt(parts[1].trim());

            ruleMap.computeIfAbsent(before, k -> new ArrayList<>()).add(after);
        }

        return ruleMap;
    }

    private boolean isUpdateValid(List<Integer> update, Map<Integer, List<Integer>> ruleMap) {
        Set<Integer> seenPages = new HashSet<>();

        for (int i = 0; i < update.size(); i++) {
            int currentPage = update.get(i);
            seenPages.add(currentPage);

            if (ruleMap.containsKey(currentPage)) {
                for (int mustFollow : ruleMap.get(currentPage)) {
                    if (seenPages.contains(mustFollow)) {
                        return false; // Rule violated
                    }
                }
            }
        }

        return true;
    }
    private List<Integer> reorderUpdate(List<Integer> update, Map<Integer, List<Integer>> ruleMap) {
        // Use a topological sort to reorder the pages in the update
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Build the graph and in-degree map for the current update
        for (int page : update) {
            graph.putIfAbsent(page, new ArrayList<>());
            inDegree.putIfAbsent(page, 0);
        }

        for (int page : update) {
            if (ruleMap.containsKey(page)) {
                for (int dependentPage : ruleMap.get(page)) {
                    if (update.contains(dependentPage)) {
                        graph.get(page).add(dependentPage);
                        inDegree.put(dependentPage, inDegree.getOrDefault(dependentPage, 0) + 1);
                    }
                }
            }
        }

        // Perform topological sort
        Queue<Integer> queue = new LinkedList<>();
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        List<Integer> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedOrder.add(current);

            for (int neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedOrder;
    }
}
