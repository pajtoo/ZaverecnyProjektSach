package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MappingDataProvider {
    private Class clazz;
    // Získá slovník obsahující položky odpovídající atributům v třídě clazz
    private Map<String, MappingObject> itemsMap;

    public MappingDataProvider(Class<?> clazz) {
        this.clazz = clazz;
        this.itemsMap = FieldMap.getFieldMapping(clazz);
    }

    /**
     * Slouží uživatelskému rozhraní jako zdroj popisků jednotlivých položek
     * @return popisky jednotlivých položek třídy předané v konstruktoru
     */
    public List<PopiskyEnum> getFieldLabels() {
        return itemsMap.values().stream()
                .map(MappingObject::getPopisek)
                .toList();
    }

    /**
     * Dodá seznam validačních objektů k validaci atributů třídy, která byla předána konstruktoru
     * při založení instance
     *
     * @return Seznam validačních objektů
     */
    public List<ValidatorEnum> getValidatorCriteria() {
        return itemsMap.values().stream()
                .map(MappingObject::getValidator)
                .toList();
    }

    /**
     * Vrací seznam jmen atributů třídy, která byla předána v konstruktoru
     * @return Seznam jmen atributů
     */
    public List<String> getFieldNames() {
        return itemsMap.keySet().stream().toList();
    }

}



}