package utils;

import java.util.ArrayList;
import java.util.List;

public class TestIntegerGenerator {

    public List<Integer> generateValidValues(int min, int max) {
        List<Integer> validValues = new ArrayList<>();
        validValues.add(min);
        validValues.add((max - min) / 2);
        validValues.add(max);
        return validValues;
    }

    public List<Integer> generateInvalidValues(int min, int max) {
        List<Integer> invalidValues = new ArrayList<>();
        invalidValues.add(min - 1);
        invalidValues.add(max + 1);
        return invalidValues;
    }
}