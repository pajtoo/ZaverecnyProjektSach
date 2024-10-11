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
    private Map<String,MappingObject> itemsMap;

    public MappingDataProvider(Class<?> clazz) {
        this.clazz = clazz;
        this.itemsMap = FieldMap.getFieldMapping(clazz);
    }

    public List<PopiskyEnum> getFieldLabels() {
        return itemsMap.values().stream()
                .map(MappingObject::getPopisek)
                .toList();
    }

    public List<ValidatorEnum> getValidatorCriteria() {

        Arrays.stream(clazz.getMethods())
                .filter(method -> {
                    method.getName().contains("set") &&
                    !Arrays.stream(method.getParameters()).filter(parameter -> {
                        parameter.getName().equalsIgnoreCase("id");
                    })
                })
                .forEach( method -> {
                    for (String key : itemsMap.keySet()) {
                        if (method.getName().toLowerCase().contains(key)) {
                            method.
                        }
                    }
                });
                });
        return itemsMap.keySet();
    }



}
