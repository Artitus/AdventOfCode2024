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
        return String.valueOf(updates.stream()
                .filter(update -> isUpdateValid(update, ruleMap))
                .mapToInt(update -> update.get(update.size() / 2))
                .sum());
    }

    @Override
    public String solvePart2() {
        List<String> rules = new ArrayList<>();
        List<List<Integer>> updates = new ArrayList<>();

        parseInput(inputLines, rules, updates);

        Map<Integer, List<Integer>> ruleMap = buildRuleMap(rules);
        return String.valueOf(updates.stream()
                .filter(update -> !isUpdateValid(update, ruleMap))
                .map(update -> reorderUpdate(update, ruleMap))
                .mapToInt(reorderedUpdate -> reorderedUpdate.get(reorderedUpdate.size() / 2))
                .sum());
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
                updates.add(Arrays.stream(line.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList());
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

        for (int currentPage : update) {
            seenPages.add(currentPage);
            if (ruleMap.getOrDefault(currentPage, Collections.emptyList())
                    .stream()
                    .anyMatch(seenPages::contains)) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> reorderUpdate(List<Integer> update, Map<Integer, List<Integer>> ruleMap) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int page : update) {
            graph.putIfAbsent(page, new ArrayList<>());
            inDegree.putIfAbsent(page, 0);
        }

        for (int page : update) {
            for (int dependentPage : ruleMap.getOrDefault(page, Collections.emptyList())) {
                if (update.contains(dependentPage)) {
                    graph.get(page).add(dependentPage);
                    inDegree.put(dependentPage, inDegree.get(dependentPage) + 1);
                }
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedOrder.add(current);
            for (int neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedOrder;
    }
}
