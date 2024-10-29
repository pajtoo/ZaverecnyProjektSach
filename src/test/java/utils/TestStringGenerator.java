package utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TestStringGenerator {

    public LinkedHashMap<String, String> generateValidValues(int minLength, int maxLength) {
        LinkedHashMap<String, Integer> validLengths = new LinkedHashMap<>();

        if (minLength < 1) {
           minLength = 1;
        }
        validLengths.put("min", minLength);
        validLengths.put("min+1", minLength + 1);
        validLengths.put("mid", (maxLength - minLength) / 2);
        validLengths.put("max-1", maxLength - 1);
        validLengths.put("max", maxLength);

        return getLengthRelatedStrings(validLengths);
    }

    public LinkedHashMap<String, String> generateInvalidValues(int minLength, int maxLength) {
        LinkedHashMap<String, Integer> invalidLengths = new LinkedHashMap<>();

        if (minLength < 1) {
            minLength = 1;
        }

        invalidLengths.put("min-1", minLength - 1);
        invalidLengths.put("max+1", maxLength + 1);

        return getLengthRelatedStrings(invalidLengths);
    }

    /**
     * Vrátí Stringy o délce specifikované v předaném parametru
     * @param stringLengths Specifikace délek Stringů
     * @return Stringy vygenerované podle specifikace
     */
    private LinkedHashMap<String, String> getLengthRelatedStrings(LinkedHashMap<String, Integer> stringLengths) {
        LinkedHashMap<String, String> relatedStrings = new LinkedHashMap<>();
        List<String> relatedStringKeys = new ArrayList<>(stringLengths.keySet());
        List<Integer> relatedStringLengths = new ArrayList<>(stringLengths.values());

        for (int i = 0; i < stringLengths.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.repeat("a", relatedStringLengths.get(i));
            String relatedString = stringBuilder.toString();
            relatedStrings.put(relatedStringKeys.get(i), relatedString);
        }
        return relatedStrings;
    }
}
