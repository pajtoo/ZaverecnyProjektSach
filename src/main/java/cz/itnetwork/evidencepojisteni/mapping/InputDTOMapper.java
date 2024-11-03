package cz.itnetwork.evidencepojisteni.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class InputDTOMapper<T> {

    /**
     * @param clazz Třída jejíž instance se bude mapováním tvořit
     * @param polozky První String je jméno atributu, druhý String je hodnota
     * @return DTO naplněné daty
     */
    public T createDTO(Class<T> clazz, Map<String, String> polozky) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T instanceDTO = constructor.newInstance();

            Arrays.stream(instanceDTO.getClass().getMethods())
                    .filter(metoda ->
                            // Získání setterů s výjimkou setId
                            metoda.getName().startsWith("set") &&
                            !metoda.getName().equalsIgnoreCase("setId")
                    ).forEach(metoda -> {
                        // Mapování
                        String setterFor = metoda.getName().substring(3);
                        Class<?> targetType = metoda.getParameterTypes()[0];
                        String respectiveFieldValue = "";
                        for (Map.Entry<String, String> polozka : polozky.entrySet()) {
                            if (polozka.getKey().equalsIgnoreCase(setterFor)) {
                                respectiveFieldValue = polozka.getValue();
                                break;
                            }
                        }
                        Object convertedValue = null;
                        if (respectiveFieldValue != null && !respectiveFieldValue.isBlank()) {
                            // Převod na příslušný datový typ
                            convertedValue = convertValueType(respectiveFieldValue, targetType);
                        }

                        // Podmínka pro odstranění setterů pro položky neobsažené v Map s vyplněnými položkami
                        if (polozky.containsKey(setterFor.toLowerCase())) {
                            try {
                                metoda.invoke(instanceDTO, convertedValue);
                            } catch (IllegalAccessException | InvocationTargetException ex) {
                                throw new RuntimeException("An exception occured during invoking DTO setters.", ex);
                            }
                        }
                    });
            return instanceDTO;
        } catch (ReflectiveOperationException | SecurityException ex) {
            // obaluji pomocí RuntimeException, protože se jedná o chyby v kódu, z nichž se vzpamatovat nedá a je třeba ukončit běh programu
            throw new RuntimeException("An exception occured during mapping values from user input to DTO fields.", ex);
        }

    }

    /**
     * Aktualizuje atributy DTO odpovídající položkám předaným v Map.
     * Prázdný String a absenci polozky interpretuje jako absenci změny daného atributu.
     * @param dtoToBeUpdated DTO, jehož hodnoty se budou aktualizovat
     * @param polozky Páry klíč-hodnota, kde klíč je jméno atributu a hodnota je příslušná hodnota
     * @return Aktualizované DTO
     */
    public T updateDTO(T dtoToBeUpdated, Map<String, String> polozky) {
        // Odstranění prázdných položek, tedy těch, které se nebudou měnit
        Iterator<Map.Entry<String, String>> polozkyIterator = polozky.entrySet().iterator();
        while (polozkyIterator.hasNext()) {
            Map.Entry<String, String> polozka = polozkyIterator.next();
            if (polozka.getValue() == null || polozka.getValue().isBlank()) {
                polozkyIterator.remove();
            }
        }

        for (Map.Entry<String, String> polozka : polozky.entrySet()) {
                    Arrays.stream(dtoToBeUpdated.getClass().getMethods())
                    .filter(metoda ->
                            // Získání setterů s výjimkou setId
                            metoda.getName().startsWith("set") &&
                            !metoda.getName().equalsIgnoreCase("setId")
                    ).filter(metoda ->
                            polozky.containsKey(metoda.getName().substring(3).toLowerCase())
                    ).forEach(metoda -> {
                        String setterFor = metoda.getName().substring(3).toLowerCase();
                        Class<?> targetType = metoda.getParameterTypes()[0];
                        String respectiveFieldValue = "";
                        for (Map.Entry<String, String> upravovanaPolozka : polozky.entrySet()) {
                            if (upravovanaPolozka.getKey().equalsIgnoreCase(setterFor)) {
                                respectiveFieldValue = upravovanaPolozka.getValue();

                                // Převod na příslušný datový typ
                                Object convertedValue = convertValueType(respectiveFieldValue, targetType);
                                try {
                                    metoda.invoke(dtoToBeUpdated, convertedValue);
                                } catch (IllegalAccessException | InvocationTargetException ex) {
                                    throw new RuntimeException("An exception occured during invoking DTO setters.", ex);
                                }
                            }
                        }
                    });
        }
        return dtoToBeUpdated;
    }

    /**
     * Pomocná metoda pro konverzi Stringu na parametrem vyžadovaný typ
     * @param value String, který se bude konvertovat.
     * @param targetType Požadovaný datový typ
     * @return Object wrapper s konvertovanou hodnotou
     */
    private Object convertValueType(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        }
        throw new IllegalArgumentException("Unsupported parameter type: " + targetType);
    }
}