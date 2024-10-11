package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.util.List;
import java.util.Map;

public class Mapper {
    // Získá slovník obsahující položky
    private Map<String,MappingObject> itemsMap;

    public Mapper(Class<?> clazz) {
        this.itemsMap = FieldMap.getFieldMapping(clazz);
    }

    public List<PopiskyEnum> getFieldLabels() {
        return itemsMap.values().stream()
                .map(MappingObject::getPopisek)
                .toList();
    }

    public List<ValidatorVstupu.ValidatorEnum> getValidatorCriteria() {
        return itemsMap.values().stream()
                .map(MappingObject::getValidator)
                .toList();
    }




}
