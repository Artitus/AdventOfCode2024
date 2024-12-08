package io.hurbs.adventofcode2024.util;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static List<Integer> convertToDigits(String number) {
        List<Integer> digits = new ArrayList<>();
        for (char c : number.toCharArray()) {
            digits.add(Character.getNumericValue(c));
        }
        return digits;
    }
}
