package cz.itnetwork.evidencepojisteni.mapping;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
                        // Převod na příslušný datový typ
                        Object convertedValue = convertValueType(respectiveFieldValue, targetType);

                        try {
                            metoda.invoke(instanceDTO, convertedValue);
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            throw new RuntimeException("An exception occured during invoking DTO setters.", ex);
                        }
                    });
            return instanceDTO;
        } catch (ReflectiveOperationException | SecurityException ex) {
            // obaluji pomocí RuntimeException, protože se jedná o chyby v kódu, z nichž se vzpamatovat nedá a je třeba ukončit běh programu
            throw new RuntimeException("An exception occured during mapping values from user input to DTO fields.", ex);
        }

    }

    // Pomocná metoda pro konverzi Stringu na parametrem vyžadovaný typ
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
