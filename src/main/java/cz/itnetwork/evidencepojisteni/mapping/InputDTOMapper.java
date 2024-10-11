package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class InputDTOMapper<T> {

    /**
     *
     * @param polozky První String je jméno atributu, druhý String je hodnota
     * @return
     */
    public <T> T createDTO(Class<T> clazz, Map<String, String> polozky) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getConstructor();
        Object instanceDTO = constructor.newInstance();

        Method[] setteryBezId = Arrays.stream(instanceDTO.getClass().getMethods())
               .filter(metoda -> {
                   metoda.getName().contains("set") &&
                           metoda.getName().equalsIgnoreCase("id");
               }).forEach(metod -> {
                   metoda.invoke()
               });
    }
}
