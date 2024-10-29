package utils;

import java.util.LinkedHashMap;

public class TestIntegerGenerator {

    public LinkedHashMap<String, Integer> generateValidValues(int min, int max) {
        LinkedHashMap<String, Integer> validValues = new LinkedHashMap<>();
        validValues.put("min", min);
        validValues.put("mid", (max - min) / 2);
        validValues.put("max", max);
        return validValues;
    }

    public LinkedHashMap<String, Integer> generateInvalidValues(int min, int max) {
        LinkedHashMap<String, Integer> invalidValues = new LinkedHashMap<>();
        invalidValues.put("min-1", min - 1);
        invalidValues.put("max+1", max + 1);
        return invalidValues;
    }
}