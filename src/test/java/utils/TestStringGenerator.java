package utils;

import java.util.ArrayList;
import java.util.List;

public class TestStringGenerator {

    public List<String> generateValidStrings(int minLength, int maxLength, char fillerCharacter) {
        List<Integer> validLengths = new ArrayList<>();

        if (minLength < 1) {
           minLength = 1;
        }
        validLengths.add(minLength);
        validLengths.add(minLength + 1);
        validLengths.add((maxLength + minLength) / 2);
        validLengths.add(maxLength - 1);
        validLengths.add(maxLength);

        return getLengthRelatedStrings(validLengths, fillerCharacter);
    }

    public List<String> generateInvalidStrings(int minLength, int maxLength, char fillerCharacter) {
        List<Integer> invalidLengths = new ArrayList<>();

        if (minLength < 1) {
            minLength = 1;
        }

        invalidLengths.add(minLength - 1);
        invalidLengths.add(maxLength + 1);

        return getLengthRelatedStrings(invalidLengths, fillerCharacter);
    }

    /**
     * Vrátí Stringy o délce specifikované v předaném parametru
     *
     * @param stringLengths   Specifikace délek Stringů
     * @param fillerCharacter Znak, kterým se String vyplní v požadované délce
     * @return Stringy vygenerované podle specifikace
     */
    private List<String> getLengthRelatedStrings(List<Integer> stringLengths, char fillerCharacter) {
        List<String> relatedStrings = new ArrayList<>();

        for (int i = 0; i < stringLengths.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.repeat(fillerCharacter, stringLengths.get(i));
            String relatedString = stringBuilder.toString();
            relatedStrings.add(relatedString);
        }
        return relatedStrings;
    }
}
